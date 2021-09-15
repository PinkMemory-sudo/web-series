package com.pk.jpa.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description: query反馈表
 * @author: v_lilong06 <v_lilong06@baidu.com>
 * @since: 2021-08-31
 */

public class QueryFeedbackDto implements Serializable, Comparable<QueryFeedbackDto> {
    // 自定义query主键ID, 根据taskId、userId、content生成

    private String id;

    // queryid

    private String queryId;

    // 该user所属的task的ID

    private String taskId;

    // 人员ID，导入到系统后为百度侧user id，没有异或

    private String userId;


    private String content;


    private String retrievalTime;

    private int value;


    private String actionTime;

    public int compareTo(QueryFeedbackDto o) {
        return o.actionTime.compareTo(this.actionTime);
    }
}
