package com.pk.order8101.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("goods")
public interface OrderService {
    @GetMapping("/hello") //请求结果作为接口的返回值
    String getOrderGoods(String orderId);
}
