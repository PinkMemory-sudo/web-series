package com.pk.jpa.model.es;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 意图query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Query implements Serializable {
    // 查询内容
    private String q;
    // 查询时间
    private String date;
    // 意图
    private List<String> role;
    // 实例id
    private Long instanceId;
    // userID
    private Long userId;
    // ip
    private String ip;
    // 权重
    private String valueWeight;
    // 时间戳
    private String dataTime;
}
