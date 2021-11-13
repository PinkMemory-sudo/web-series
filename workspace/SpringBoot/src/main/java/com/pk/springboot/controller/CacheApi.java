package com.pk.springboot.controller;

import com.pk.springboot.dao.EsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cache_test")
@RestController
public class CacheApi {

    @Autowired
    private EsDao esDao;

    @GetMapping("/test")
    public String test() {
        return esDao.test();
    }

}
