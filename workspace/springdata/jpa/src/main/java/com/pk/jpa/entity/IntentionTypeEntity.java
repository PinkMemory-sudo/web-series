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
 * @description: 意图类型表
 * @author: v_lilong06 <v_lilong06@baidu.com>
 * @since: 2021-08-31
 */
@Data
@Entity
@Table(name = "intention_type")
public class IntentionTypeEntity implements Serializable {
    // 内部的自增ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // intentiontype在excel中的位置，第几列，默认为第1列
    @Column(name = "position")
    private int position;

    // 二级意图的名称
    @Column(name = "name")
    private String name;

    // 二级意图的得分
    @Column(name = "score")
    private double score;

    // 该user所属的task的ID
    @Column(name = "task_id")
    private String taskId;

    // 人员ID，导入到系统后为百度侧userid，没有异或
    @Column(name = "user_id")
    private String userId;

}
