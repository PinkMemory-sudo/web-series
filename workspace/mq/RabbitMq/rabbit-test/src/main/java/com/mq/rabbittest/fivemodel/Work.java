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

import java.time.LocalDate;

/**
 * simple工作模式
 * 使用默认的Exchange和bind
 */
@RestController
@Slf4j
public class Work {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 创建队列
    @Bean
    public Queue workQueue() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        return new Queue("work_queue");
    }

    @GetMapping("/sent_work_msg")
    public void sentMsg() {
        rabbitTemplate.convertAndSend("work_queue", "work");
    }

    // 消费者1
    @RabbitListener(queues = "work_queue")
    public void consumer1(Message message) {
        log.info("consumer1 get msg: {}", message);
    }

    // 消费者2
    @RabbitListener(queues = "work_queue")
    public void consumer2(Message message) {
        log.info("consumer2 get msg: {}", message);
    }

}
