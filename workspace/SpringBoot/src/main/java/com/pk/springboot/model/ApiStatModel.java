package com.pk.springboot.model;

import lombok.Data;

@Data
public class ApiStatModel {
    private String accountName;
    private String url;
    private String timeStamp;
    private String machineCode;
    private String requestMethod;
    private String methodDescription;
}
