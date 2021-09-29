package com.pk.jpa.model.param;

import lombok.Data;

import java.util.List;

/**
 * 人员信息相关的反馈过滤条件
 */
@Data
public class VariantFilterParam {
    // 组织名称
    private String accountId;
    // 账户名称
    private String accountName;
    // 推送时间范围
    private String pushDateStart;
    private String pushDateEnd;
    // 反馈时间范围
    private String actionTimeStart;
    private String actionTimeEnd;
    // 线索有效性
    private int value;
    // 无效原因
    private List<Integer> reasonList;
    private List<String> variantList;
}
