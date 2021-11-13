package com.pk.goods8201.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;

@RestController
@RequestMapping("/goods")
public class GoodsApi {

    @GetMapping("/list")
    public String getOrderGoods(String orderId) {
        return "hello,hear is 8201";
    }
}
