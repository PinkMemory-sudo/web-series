package com.mq.rabbittest;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class RabbitTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitTestApplication.class, args);
    }

}
