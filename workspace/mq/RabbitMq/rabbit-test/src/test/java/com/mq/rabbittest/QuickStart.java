package com.mq.rabbittest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class QuickStart {

    @Test
    public void test() {
        ConnectionFactory connectionFactory = new CachingConnectionFactory();
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        // 创建队列
        admin.declareQueue(new Queue("myqueue"));
        // 没有指定Exchange，使用默认的Exchange和binding
        AmqpTemplate template = new RabbitTemplate(connectionFactory);
        // 添加消息
        template.convertAndSend("myqueue", "foo");
        // 获得消息
        String foo = (String) template.receiveAndConvert("myqueue");
        log.info("get msg: {}", foo);
    }
}
