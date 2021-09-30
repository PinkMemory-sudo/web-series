package com.mq.rabbittest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Slf4j
@SpringBootTest
public class PubSubTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        TopicExchange fodderTopic = new TopicExchange("fodderTopic");
        Queue rabbit = new Queue("rabbit");
        Queue hen = new Queue("hen");
        Binding tomHenBinding = BindingBuilder.bind(hen).to(fodderTopic).with("tom.*");
        Binding tomRabbitBinding = BindingBuilder.bind(rabbit).to(fodderTopic).with("tom.*");
        Binding henBinding = BindingBuilder.bind(hen).to(fodderTopic).with("hen.*");
        Binding rabbitBinding = BindingBuilder.bind(hen).to(fodderTopic).with("rabbit.*");
        amqpAdmin.declareQueue(rabbit);
        amqpAdmin.declareQueue(hen);
        amqpAdmin.declareExchange(fodderTopic);
        amqpAdmin.declareBinding(tomRabbitBinding);
        amqpAdmin.declareBinding(tomHenBinding);
        amqpAdmin.declareBinding(henBinding);
        amqpAdmin.declareBinding(rabbitBinding);
    }

    @Test
    public void sendMessage() {
        // Message(byte[] body, MessageProperties messageProperties)
        // 同构消息头个消息体构造Message
        HashMap<String, String> map = new HashMap<>();
        map.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // 如果不需要消息头，可使用convertAndSend
        rabbitTemplate.convertAndSend("fodderTopic", "hen.", map);
        rabbitTemplate.convertAndSend("fodderTopic", "tom.", map);
    }

    @Test
    public void receiveTest() {
        log.info("rabbit:{}", rabbitTemplate.receiveAndConvert("rabbit"));
        log.info("hen: {}", rabbitTemplate.receiveAndConvert("hen"));
    }
}
