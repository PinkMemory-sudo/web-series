package com.pk.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;

/**
 * @description: query反馈表
 * @author: v_lilong06 <v_lilong06@baidu.com>
 * @since: 2021-08-31
 */
@Data
@Entity
@Table(name = "intel_query_feedback")
public class QueryFeedbackEntity implements Serializable, Comparable<QueryFeedbackEntity> {
    // 自定义query主键ID, 根据taskId、userId、content生成
    @Id
    @Column(name = "id")
    private String id;

    // queryid
    @Column(name = "query_id", length = 127)
    private String queryId;

    // 该user所属的task的ID
    @Column(name = "task_id", length = 32)
    private String taskId;

    // 人员ID，导入到系统后为百度侧user id，没有异或
    @Column(name = "user_id", length = 64)
    private String userId;

    // query内容
    @Column(name = "content", length = 765)
    private String content;

    // query检索时间
    @Column(name = "retrieval_time")
    private String retrievalTime;

    // query有效性反馈，0代表取消反馈，1代表无价值query，2代表高价值query
    @Column(name = "value")
    private int value;

    // query反馈时间
    @Column(name = "action_time")
    private String actionTime;

    // 根据反馈时间降序排序
    @Override
    public int compareTo(QueryFeedbackEntity o) {
        return o.actionTime.compareTo(this.actionTime);
    }
}
