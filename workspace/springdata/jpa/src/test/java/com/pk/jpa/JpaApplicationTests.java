package com.pk.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.jpa.dao.VariantDao;
import com.pk.jpa.entity.IntentionTypeEntity;
import com.pk.jpa.entity.Parent;
import com.pk.jpa.entity.QueryFeedbackEntity;
import com.pk.jpa.entity.Son;
import com.pk.jpa.entity.VariantFeedbackEntity;
import com.pk.jpa.model.PageResultData;
import com.pk.jpa.model.dto.ParentDto;
import com.pk.jpa.model.param.FeedbackQueryParam;
import com.pk.jpa.model.param.QueryFilterParam;
import com.pk.jpa.model.param.VariantFilterParam;
import com.pk.jpa.service.impl.VariantServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class JpaApplicationTests {

    @Autowired
    private VariantDao variantDao;

    @Autowired
    private VariantServiceImpl variantService;

    @Test
    void contextLoads() throws JsonProcessingException {
        // 人员
        VariantFilterParam variantParam = new VariantFilterParam();
        variantParam.setAccountName("乐清公安");
//        variantParam.setAccountId("y");
//        variantParam.setPushDateStart("2010-01-01 01:00:00");
//        variantParam.setPushDateStart("2021-01-01 01:00:00");
        variantParam.setActionTimeStart("2022-01-01");
        variantParam.setActionTimeEnd("2022-01-02");
//        variantParam.setValue(1);
        List<Integer> reasonList = new ArrayList<>();
        reasonList.add(2);
        reasonList.add(3);
        List<String> variantList = new ArrayList<>();
        variantList.add("yq1-2");
        variantList.add("yq1-1");
//        variantParam.setVariantList(variantList);
//        variantParam.setReasonList(reasonList);

        // query
        QueryFilterParam queryParam = new QueryFilterParam();
        List<Integer> valueList = new ArrayList<>();
//        valueList.add(1);
        queryParam.setValueList(valueList);
        queryParam.setActionTimeStart("2021-01-01");
        queryParam.setActionTimeEnd("2022-01-01");

        // intention

        ArrayList<String> intentionList = new ArrayList<>();
        intentionList.add("吸毒");
        intentionList.add("制毒");
        FeedbackQueryParam param = new FeedbackQueryParam();
        param.setVariantFilterParam(variantParam);
//        param.setQueryFilterParam(queryParam);
//        param.setIntentionList(intentionList);
        ObjectMapper mapper = new ObjectMapper();
        PageResultData<VariantFeedbackEntity> page = variantService.findPage(param, 0, 9);
        System.out.println(mapper.writeValueAsString(page));
    }

    @Test
    public void insertTest() {
        VariantFeedbackEntity entity = new VariantFeedbackEntity();
        entity.setAccountId("guangzhouJD");
        entity.setAccountName("广州禁毒");
        entity.setTaskId("gzjd1");
        entity.setUserId("gz1");
        entity.setPhone("phone");
        entity.setPushDate("2021-01-12");
        entity.setPushCount(2);
        entity.setClueValue(1);
        entity.setReason("1,2");
        entity.setActionTime("2021-01-01 10:00:00");
        ArrayList<QueryFeedbackEntity> queryFeedbackList = new ArrayList<>();
        QueryFeedbackEntity query = new QueryFeedbackEntity();
        query.setId("asg");
        query.setQueryId("q6");
        query.setContent("吸毒后可以做保安吗");
//        query.setRetrievalTime();
        query.setValue(1);
//        query.setActionTime(new Date());
        queryFeedbackList.add(query);
        entity.setQueryList(queryFeedbackList);
        ArrayList<IntentionTypeEntity> intentionList = new ArrayList<>();
        IntentionTypeEntity typeEntity = new IntentionTypeEntity();
        typeEntity.setName("吸毒");
        typeEntity.setPosition(1);
        typeEntity.setScore(90.00);
//        typeEntity.set
    }

    @Test
    public void dtoTest() {
        Son son = new Son("son");
        List<Son> sonList = new ArrayList<>();
        sonList.add(son);
        Parent parent = new Parent("parent", sonList);
        ParentDto parentDto = new ParentDto();
        BeanUtils.copyProperties(parent, parentDto);
        System.out.println(parent);
        System.out.println(parentDto);
        sonList.stream().forEach(it->{it.setName("jack");});
        System.out.println(sonList);
    }

}
