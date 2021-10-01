package com.mq.rabbittest.fivemodel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * simple工作模式
 * 使用默认的Exchange和bind
 */
@RestController
@Slf4j
public class Simple {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 创建队列
    @Bean
    public Queue simpleQueue() {
        return new Queue("simple_queue");
    }

    @GetMapping("/sent_simple_msg")
    public void sentMsg() {
        rabbitTemplate.convertAndSend("simple_queue", "simple");
    }

    // 消费者1
    @RabbitListener(queues = "simple_queue")
    public void consumer1(Message message) {
        log.info("consumer get msg: {}", message);
    }

}
