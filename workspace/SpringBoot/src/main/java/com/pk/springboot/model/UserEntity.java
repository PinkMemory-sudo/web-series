package com.pk.springboot.model;


import lombok.Data;

import java.util.List;

@Data
public class UserEntity {

    private String name;
    private int age;
    private List<Area> areaList;
}
