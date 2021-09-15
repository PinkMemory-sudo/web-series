package com.pk.jpa.model;

import com.pk.jpa.entity.IntentionTypeEntity;
import com.pk.jpa.entity.QueryFeedbackEntity;
import com.pk.jpa.entity.VariantFeedbackPK;
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
public class VariantFeedbackDto implements Serializable,Comparable<VariantFeedbackDto> {

    // 账户名称
    private String accountName;

    // account id
    private String accountId;

    // 该user所属的task的ID
    private String taskId;

    // 实例id
    private int instanceId;

    // 人员ID，导入到系统后为百度侧user id，没有异或
    private String userId;

    // 意图人员手机号，多个用 , 分割
    private String phone;

    // 意图人员mac，多个用 , 分割
    private String mac;

    // 意图人员imei，多个用 , 分割
    private String imei;

    // 线索推送日期，格式为YYYY-MM-DD
    private String pushDate;

    // 线索推送次数
    private int pushCount;

    // 线索有效性反馈，1代表无效，2代表有效，3代表立案,
    private int clueValue;

    // 逗号分割的数字组合，1代表『根据内容判断无嫌疑』，2代表『三码无法落地人员身份』，3代表『常驻地不准确』，4代表『其他』,
    private String reason;

    // 其他原因，如果原因中包含了其他，用户手工填写的其他原因,
    private String otherReason;

    // 备注,
    private String note;

    // 线索反馈时间,
    private String actionTime;


    private List<QueryFeedbackDto> queryList;


    private List<IntentionTypeEntity> intentionList;

    @Override
    public int compareTo(VariantFeedbackDto o) {
        return o.actionTime.compareTo(this.actionTime);
    }
}
