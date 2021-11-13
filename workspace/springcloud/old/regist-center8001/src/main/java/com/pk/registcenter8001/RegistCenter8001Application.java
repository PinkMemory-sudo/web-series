package com.pk.registcenter8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistCenter8001Application {

    public static void main(String[] args) {
        SpringApplication.run(RegistCenter8001Application.class, args);
    }

}
