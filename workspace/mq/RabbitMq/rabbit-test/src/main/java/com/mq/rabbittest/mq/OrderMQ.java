package com.mq.rabbittest.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private void sendMsg() {

    }

    private <T> T getMsg(Class<T> t) throws IllegalAccessException, InstantiationException {
        return t.newInstance();

    }
}
