package com.pk.jpa.service.impl;

import com.pk.jpa.dao.VariantDao;
import com.pk.jpa.dao.es.QueryDao;
import com.pk.jpa.entity.QueryFeedbackEntity;
import com.pk.jpa.entity.VariantFeedbackEntity;
import com.pk.jpa.model.Constants;
import com.pk.jpa.model.PageResultData;
import com.pk.jpa.model.VariantFeedbackDto;
import com.pk.jpa.model.es.Query;
import com.pk.jpa.model.param.FeedbackQueryParam;
import com.pk.jpa.model.param.QueryFilterParam;
import com.pk.jpa.model.param.VariantFilterParam;
import com.pk.jpa.service.VariantService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
//@ConditionalOnProperty(name = "intention.isBaidu", havingValue = "true")
public class VariantServiceImpl implements VariantService {

    @Autowired
    private VariantDao variantDao;

    @Autowired
    private QueryDao queryDao;

    /**
     * 反馈列表分页查询
     */
    @Override
    public PageResultData<VariantFeedbackEntity> findPage(FeedbackQueryParam param, int pageNumber, int pageSize) {
        Page<VariantFeedbackEntity> variantPage = findVariantAll(param, pageNumber, pageSize);
        List<VariantFeedbackEntity> variantList = variantPage.getContent();
        ArrayList<VariantFeedbackDto> dtos = new ArrayList<>();

        // 没有query反馈的
        List<VariantFeedbackEntity> variantNoQueryList = variantList.stream()
                .filter(it -> CollectionUtils.isEmpty(it.getQueryList())).collect(Collectors.toList());
        List<String> intentionList = param.getIntentionList();
        QueryFilterParam queryFilter = param.getQueryFilterParam();
        // 是否有意图和query的过滤条件
        if (null != queryFilter) {
            variantList = variantList.parallelStream().peek(it -> {
                String actionTimeStart = queryFilter.getActionTimeStart();
                String actionTimeEnd = queryFilter.getActionTimeEnd();
                List<Integer> valueList = queryFilter.getValueList();
                List<QueryFeedbackEntity> queryList = it.getQueryList().stream().filter(query -> {
                    if (StringUtils.isNoneEmpty(actionTimeStart, actionTimeEnd)) {
                        String actionTime = query.getActionTime();
                        if (actionTime.compareTo(actionTimeStart) < 0 || actionTime.compareTo(actionTimeEnd) > 0) {
                            return false;
                        }
                    }
                    if (!CollectionUtils.isEmpty(valueList)) {
                        return valueList.contains(query.getValue());
                    }
                    return true;
                }).sorted().collect(Collectors.toList());
                it.setQueryList(queryList);
            }).collect(Collectors.toList());
            Collections.sort(variantList);
        }

        // 没有query反馈的，取最新一条query
        if (!CollectionUtils.isEmpty(variantNoQueryList)) {
            List<Query> queryList = new ArrayList<>();
            for (VariantFeedbackEntity entity : variantNoQueryList) {
                Query query = new Query();
                query.setInstanceId((long) (entity.getInstanceId()));
                query.setUserId(Long.parseLong(entity.getUserId()));
                queryList.add(query);
            }
//            Map<String, Query> queryMap = queryDao.bulkGetLatestQuery(queryList);
            variantList = variantList.stream().peek(it -> {
                if (CollectionUtils.isEmpty(it.getQueryList())) {
                    StringJoiner stringJoiner = new StringJoiner(Constants.DASH);
                    stringJoiner.add(String.valueOf(it.getInstanceId())).add(it.getUserId());
//                    Query query = queryMap.get(stringJoiner.toString());
                    Query query = new Query();
                    query.setQ("queryContent");
                    query.setDate("2021-07-10 22:07:19");
                    List<QueryFeedbackEntity> queryEntityList = new ArrayList<>();
                    QueryFeedbackEntity queryEntity = new QueryFeedbackEntity();
                    queryEntity.setContent(query.getQ());
                    queryEntity.setRetrievalTime(query.getDate());
                    queryEntity.setActionTime(Constants.DASH);
                    queryEntity.setValue(Constants.ZERO);
                    queryEntityList.add(queryEntity);
                    it.setQueryList(queryEntityList);
                }
            }).collect(Collectors.toList());
        }
        PageResultData<VariantFeedbackEntity> pageResultData = new PageResultData<>();
        pageResultData.setCount(variantPage.getTotalElements());
        pageResultData.setRows(variantList);
        return pageResultData;
    }

    /**
     * 获得符合查询条件的人员和对应所有query和意图
     */
    private Page<VariantFeedbackEntity> findVariantAll(FeedbackQueryParam param, int pageNumber, int pageSize) {
        Specification<VariantFeedbackEntity> specification =
                (Specification<VariantFeedbackEntity>) (root, query, cb) -> {
                    List<Predicate> predicateList = new ArrayList<>();
                    // 是否有人员相关查询条件
                    if (null != param.getVariantFilterParam()) {
                        VariantFilterParam variantParam = param.getVariantFilterParam();
                        List<String> variantList = variantParam.getVariantList();
                        int value = variantParam.getValue();
                        List<Integer> reasonList = variantParam.getReasonList();
                        String pushDateStart = variantParam.getPushDateStart();
                        String pushDateEnd = variantParam.getPushDateEnd();
                        String actionTimeStart = variantParam.getActionTimeStart();
                        String actionTimeEnd = variantParam.getActionTimeEnd();
                        String accountId = variantParam.getAccountId();
                        String accountName = variantParam.getAccountName();
                        if (!CollectionUtils.isEmpty(variantList)) {
                            Predicate[] predicateArr = new Predicate[variantList.size()];
                            for (int i = 0; i < variantList.size(); i++) {
                                String taskUserId = variantList.get(i);
                                String[] split = taskUserId.split(Constants.DASH);
                                predicateArr[i] = cb.and(cb.equal(root.get("taskId"), split[0]),
                                        cb.equal(root.get("userId"), split[1]));
                            }
                            predicateList.add(cb.or(predicateArr));
                        }
                        // 线索有效性过滤
                        if (value != 0) {
                            predicateList.add(cb.equal(root.get("clueValue"), variantParam.getValue()));
                        }
                        if (StringUtils.isNotEmpty(pushDateStart) && StringUtils.isNotEmpty(pushDateEnd)) {
                            predicateList.add(cb.between(root.get("pushDate"), pushDateStart, pushDateEnd));
                        }
                        if (StringUtils.isNotEmpty(actionTimeStart) && StringUtils.isNotEmpty(actionTimeEnd)) {
                            predicateList.add(cb.between(root.get("actionTime"), actionTimeStart, actionTimeEnd));
                        }
                        // 组织名过滤
                        if (StringUtils.isNotEmpty(accountId)) {
                            predicateList.add(cb.like(root.get("accountId"),
                                    "%" + variantParam.getAccountId() + "%"));

                        }
                        // 账户名过滤
                        if (StringUtils.isNotEmpty(accountName)) {
                            predicateList.add(cb.like(root.get("accountName"),
                                    "%" + variantParam.getAccountName() + "%"));
                        }
                        // 无效原因过滤
                        if (!CollectionUtils.isEmpty(reasonList)) {
                            Predicate[] predicateArr = new Predicate[reasonList.size()];
                            for (int i = 0; i < reasonList.size(); i++) {
                                predicateArr[i] = cb.like(root.get("reason"), "%" + reasonList.get(i) + "%");
                            }
                            predicateList.add(cb.or(predicateArr));
                        }
                    }
                    // 是否有query相关查询条件
                    if (null != param.getQueryFilterParam()) {
                        Join<Object, Object> join = root.join("queryList", JoinType.LEFT);
                        QueryFilterParam queryParam = param.getQueryFilterParam();
                        String actionTimeStart = queryParam.getActionTimeStart();
                        String actionTimeEnd = queryParam.getActionTimeEnd();
                        List<Integer> valueList = queryParam.getValueList();
                        // query反馈时间过滤
                        if (StringUtils.isNotEmpty(actionTimeStart) && StringUtils.isNotEmpty(actionTimeEnd)) {
                            predicateList.add(cb.between(join.get("actionTime"), actionTimeStart, actionTimeEnd));
                        }
                        // query有效性过滤
                        if (!CollectionUtils.isEmpty(valueList) && valueList.size() == 1) {
                            predicateList.add(cb.equal(join.get("value"), 1));
                        }

                    }
                    // 是否有意图相关查询条件
                    if (!CollectionUtils.isEmpty(param.getIntentionList())) {
                        List<String> intentionList = param.getIntentionList();
                        Join<Object, Object> intention = root.join("intentionList", JoinType.LEFT);
                        Predicate[] predicateArr = new Predicate[intentionList.size()];
                        for (int i = 0; i < intentionList.size(); i++) {
                            predicateArr[i] = cb.equal(intention.get("name"), intentionList.get(i));
                        }
                        predicateList.add(cb.or(predicateArr));
                    }
                    // 按照userId+taskId分组
                    query.groupBy(root.get("userId"), root.get("taskId"));
                    // 按照反馈时间降序排序
                    query.orderBy(new OrderImpl(root.get("actionTime"), false));
                    // 是否有意图相关过滤
                    return cb.and(predicateList.toArray(new Predicate[0]));
                };

        return variantDao.findAll(specification, PageRequest.of(pageNumber, pageSize));
    }
}
