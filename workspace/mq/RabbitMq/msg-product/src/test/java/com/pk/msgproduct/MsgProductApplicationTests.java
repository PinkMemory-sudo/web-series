package com.pk.msgproduct;

import com.pk.msgproduct.utils.MqAdminUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsgProductApplicationTests {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    void contextLoads() {
        DirectExchange exchange = new DirectExchange("testexchange");
        MqAdminUtil.createExchange(amqpAdmin,exchange,"testquery", Binding.DestinationType.QUEUE,"test");
    }

}
