package com.mq.rabbittest;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@SpringBootTest
public class ProductConsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void adminTest() {
        // 声明Exchange，binding，queue
        DirectExchange exchange = new DirectExchange("TestExchange");
        Queue queue = new Queue("TestQueue");
        Binding binding = new Binding("TestQueue",
                Binding.DestinationType.QUEUE, "TestExchange", "test", null);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }

    @Test
    public void sendMessage() {
        // Message(byte[] body, MessageProperties messageProperties)
        // 同构消息头个消息体构造Message
        HashMap<String, String> map = new HashMap<>();
        map.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // 如果不需要消息头，可使用convertAndSend
        rabbitTemplate.convertAndSend("TestExchange", "test", map);
    }

    @Test
    public void receiveTest(){
        System.out.println(rabbitTemplate.receiveAndConvert("TestQueue"));
    }
}
