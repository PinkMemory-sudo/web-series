package com.pk.msgproduct.utils;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

public class MqAdminUtil {

    public static void createExchange(AmqpAdmin amqpAdmin, Exchange exchange, String queueName, Binding.DestinationType type, String routingKey) {
        Queue queue = new Queue(queueName);
        Binding binding = new Binding(queueName, type, exchange.getName(), routingKey, null);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
    }
}
