package com.pk.jpa.model.param;

import lombok.Data;

import java.util.List;

/**
 * query相关的反馈过滤条件
 */
@Data
public class QueryFilterParam {
    private String actionTimeStart;
    private String actionTimeEnd;
    private List<Integer> valueList;
}
