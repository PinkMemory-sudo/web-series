package com.pk.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description: 线索反馈原因表
 * @author: v_lilong06 <v_lilong06@baidu.com>
 * @since: 2021-08-31
 */
@Data
@Entity
@Table(name = "feeback_reason")
public class FeedbackReasonEntity implements Serializable {
    // 内部的自增ID,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 原因，1代表『根据内容判断无嫌疑』，2代表『三码无法落地人员身份』，3代表『常驻地不准确』，4代表『其他』,可以多个
    @Column(name = "reason")
    private String reason;

    // 该user所属的task的ID,
    @Column(name = "task_id")
    private String taskId;

    // 人员ID，导入到系统后为百度侧userid，没有异或,
    @Column(name = "user_id")
    private String userId;

    // 线索反馈时间,
    @Column(name = "action_time")
    private String actionTime;

    // 线索有效性反馈，1代表无效，2代表有效，3代表立案,
    @Column(name = "clue_value")
    private int clueValue;

    // 其他原因，如果原因中包含了其他，用户手工填写的其他原因,
    @Column(name = "other_reason")
    private String otherReason;

    // 备注,
    @Column(name = "note")
    private String note;

    // 关联的发布文件ID,
    @Column(name = "release_id")
    private int releaseId;

}
