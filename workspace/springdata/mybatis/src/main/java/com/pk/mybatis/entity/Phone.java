package com.pk.mybatis.entity;

import lombok.Data;


@Data
public class Phone {

    private int id;
    private String phoneNumber;
    private int personId;
}
