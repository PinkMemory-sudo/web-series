package com.pk.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 人员反馈表
 * @author: v_lilong06 <v_lilong06@baidu.com>
 * @since: 2021-08-31
 */
@Data
@Entity
@Table(name = "intel_variant_feedback")
@IdClass(VariantFeedbackPK.class)
public class VariantFeedbackEntity implements Serializable,Comparable<VariantFeedbackEntity> {

    // 账户名称
    @Column(name = "account_name",length = 32)
    private String accountName;

    // account id
    @Column(name = "account_id",length = 32)
    private String accountId;

    // 该user所属的task的ID
    @Id
    @Column(name = "task_id",length = 32)
    private String taskId;

    // 实例id
    @Column(name = "instance_id")
    private int instanceId;

    // 人员ID，导入到系统后为百度侧user id，没有异或
    @Id
    @Column(name = "user_id",length = 64)
    private String userId;

    // 意图人员手机号，多个用 , 分割
    @Column(name = "phone",length = 765)
    private String phone;

    // 意图人员mac，多个用 , 分割
    @Column(name = "mac",length = 765)
    private String mac;

    // 意图人员imei，多个用 , 分割
    @Column(name = "imei",length = 765)
    private String imei;

    // 线索推送日期，格式为YYYY-MM-DD
    @Column(name = "push_date")
    private String pushDate;

    // 线索推送次数
    @Column(name = "push_count")
    private int pushCount;

    // 线索有效性反馈，1代表无效，2代表有效，3代表立案,
    @Column(name = "clue_value")
    private Integer clueValue;

    // 逗号分割的数字组合，1代表『根据内容判断无嫌疑』，2代表『三码无法落地人员身份』，3代表『常驻地不准确』，4代表『其他』,
    @Column(name = "reason")
    private String reason;

    // 其他原因，如果原因中包含了其他，用户手工填写的其他原因,
    @Column(name = "other_reason")
    private String otherReason;

    // 备注,
    @Column(name = "note")
    private String note;

    // 线索反馈时间,
    @Column(name = "action_time")
    private String actionTime;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumns({
            @JoinColumn(name = "task_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    })
    private List<QueryFeedbackEntity> queryList;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumns({
            @JoinColumn(name = "task_id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    })
    private List<IntentionTypeEntity> intentionList;

    @Override
    public int compareTo(VariantFeedbackEntity o) {
        return o.actionTime.compareTo(this.actionTime);
    }
}
