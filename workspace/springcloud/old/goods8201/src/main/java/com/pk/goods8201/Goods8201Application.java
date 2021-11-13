package com.pk.goods8201;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Goods8201Application {

    public static void main(String[] args) {
        SpringApplication.run(Goods8201Application.class, args);
    }

}
