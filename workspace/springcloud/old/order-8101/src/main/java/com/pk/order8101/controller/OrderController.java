package com.pk.order8101.controller;

import com.pk.order8101.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/goods/{orderId}")
    private String getOrderGoods(@PathVariable("orderId") String orderId) {
        return orderService.getOrderGoods("");
    }
}
