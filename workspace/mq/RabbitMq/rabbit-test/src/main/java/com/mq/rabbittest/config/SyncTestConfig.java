package com.mq.rabbittest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SyncTestConfig {

    // 白酒的消息队列
    @Bean
    public Queue whiteWine() {
        return new Queue("whiteWine");
    }

    // 医药的消息队列
    @Bean
    public Queue medicine() {
        return new Queue("medicine");
    }

    // Exchange
    @Bean
    public TopicExchange funtExchange() {
        return new TopicExchange("fund");
    }

    // 白酒bind工资信息
    @Bean
    public Binding wwBind() {
        return BindingBuilder.bind(whiteWine()).to(funtExchange()).with("salary");
    }

    // 医药bind工资消息
    @Bean
    public Binding mdBind() {
        return BindingBuilder.bind(medicine()).to(funtExchange()).with("salary");
    }

    // 告诉券商们，我发工资了
    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> template.convertAndSend("fund","salary", "998");
    }

    // 监听队列
    @RabbitListener(queues = "whiteWine")
    public void wlisten(String in) {
        log.info("ALLIN: {}",in);
    }

    // 监听队列
    @RabbitListener(queues = "medicine")
    public void mlisten(String in) {
        log.info("ALLIN: {}",in);
    }

}
