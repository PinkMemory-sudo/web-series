package com.pk.springboot.controller;

import com.pk.springboot.annotation.ApiStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
@Slf4j
public class RestApi {

    @ApiStat("AOP测试")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        return "hello";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String StringupdateWarnNumUsed(String accountName, int operand) {
        log.info("{}<{}", accountName,  operand);

        return "se";
    }
}
