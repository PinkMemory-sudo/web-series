package com.pk.jpa.dao.es;

import com.pk.jpa.model.Constants;
import com.pk.jpa.model.es.Query;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * 最新情报query相关的查询
 */
@Component
public class QueryDao {

    @Autowired
    private TransportClient client;

    @Value("${intention.inside.pushed.query.index}")
    private String bakQueryIndex;

    @Value("${intention.inside.pushed.query.map}")
    private String bakQueryType;

    /**
     * 根据instanceId，userId和一级意图，获得最新的一条query
     */
    public Map<String, Query> bulkGetLatestQuery(List<Query> queryList) {
        Map<String, Query> queryMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(queryList)) {
            MultiSearchRequestBuilder multiSearch = client.prepareMultiSearch();
            for (Query query : queryList) {
                List<Integer> roleList = query.getRole().stream().map(Integer::parseInt).collect(Collectors.toList());
                BoolQueryBuilder queryBuilder = boolQuery().filter(termQuery("instanceId", query.getInstanceId()))
                        .filter(termQuery("useridEs", query.getUserId()));
                SearchRequestBuilder searchRequest = client.prepareSearch(bakQueryIndex).setTypes(bakQueryType)
                        .setQuery(queryBuilder).addSort("queryDateTimeEs", SortOrder.DESC).setSize(Constants.ONE);
                multiSearch.add(searchRequest);
            }
            MultiSearchResponse items = multiSearch.get();
            for (MultiSearchResponse.Item respons : items.getResponses()) {
                SearchHits hits = respons.getResponse().getHits();
                if (hits.getTotalHits() > 0) {
                    Map<String, Object> source = hits.getHits()[0].getSource();
                    Query query = new Query(
                            String.valueOf(source.get("queryQEs")),
                            String.valueOf(source.get("queryDateTimeEs")),
                            (List<String>) source.get("queryRoleEs"),
                            Long.parseLong(source.get("instanceId").toString()),
                            Long.parseLong(source.get("useridEs").toString()),
                            String.valueOf(source.get("ip")),
                            String.valueOf(source.get("valueWeight")),
                            String.valueOf(source.get("queryDateEs"))
                    );
                    StringJoiner stringJoiner = new StringJoiner(Constants.DASH);
                    String key = stringJoiner.add(String.valueOf(query.getInstanceId()))
                            .add(String.valueOf(query.getUserId())).add(query.getDate()).toString();
                    queryMap.put(key, query);
                }
            }
        }
        return queryMap;
    }

}
