package com.pk.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 人员反馈复合主键
 * @author: v_lilong06 <v_lilong06@baidu.com>
 * @since: 2021-08-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantFeedbackPK implements Serializable {

    // 该user所属的task的ID
    private String taskId;

    // 人员ID，导入到系统后为百度侧user id，没有异或
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VariantFeedbackPK that = (VariantFeedbackPK) o;
        return taskId.equals(that.taskId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, userId);
    }
}
