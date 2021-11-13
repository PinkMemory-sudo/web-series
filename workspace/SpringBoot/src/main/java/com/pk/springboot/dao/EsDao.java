package com.pk.springboot.dao;

import com.pk.springboot.model.Area;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Component
public class EsDao {

    @Autowired
    private TransportClient client;

    @Cacheable("cacheTest")
    public String test() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }

    /**
     * 根据意图得分，区域，获得可以推送的人数
     */

    /**
     * 根据意图得分，区域，获得账户已经推送过的个数
     */

    /**
     * 根据意图得分，区域，获得还可以推送的人数
     */
//    public int getQuantity() {
//        boolQuery().filter(termQuery("instanceId", 2023))
//                .filter()
//    }

    /**
     * 获得查询区域
     */
    private QueryBuilder getAreaQuery(List<Area> areaList) {
        BoolQueryBuilder query = boolQuery();

        for (Area area : areaList) {
            BoolQueryBuilder areaQuery = boolQuery();
            if (StringUtils.isNotEmpty(area.getProvince())) {
                areaQuery.filter(termQuery("provinceEs", area.getProvince()));
            }
            if (StringUtils.isNotEmpty(area.getCity())) {
                areaQuery.filter(termQuery("cityEs", area.getCity()));
            }
            if (StringUtils.isNotEmpty(area.getDistrict())) {
                areaQuery.filter(termQuery("districtEs", area.getDistrict()));
            }
            query.should(areaQuery);
        }
        return query;
    }

    /**
     * 获得意图得分查询
     */
    private QueryBuilder getRoleQuery(List<Area> areaList) {
        BoolQueryBuilder query = boolQuery();

        for (Area area : areaList) {
            BoolQueryBuilder areaQuery = boolQuery();
            if (StringUtils.isNotEmpty(area.getProvince())) {
                areaQuery.filter(termQuery("provinceEs", area.getProvince()));
            }
            if (StringUtils.isNotEmpty(area.getCity())) {
                areaQuery.filter(termQuery("cityEs", area.getCity()));
            }
            if (StringUtils.isNotEmpty(area.getDistrict())) {
                areaQuery.filter(termQuery("districtEs", area.getDistrict()));
            }
            query.should(areaQuery);
        }
        return query;
    }


}
