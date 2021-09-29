package com.pk.jpa.model.param;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackQueryParam {
    // 人员相关过滤条件
    private VariantFilterParam variantFilterParam;
    // query相关过滤条件
    private QueryFilterParam queryFilterParam;
    // 意图类型相关过滤条件
    private List<String> intentionList;

}
