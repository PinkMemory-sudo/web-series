package com.pk.springboot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/request")
@RestController
public class RequestBodyTest {

    @PostMapping("/list")
    public String test(List<String> nameList){
        return "success";
    }

}
