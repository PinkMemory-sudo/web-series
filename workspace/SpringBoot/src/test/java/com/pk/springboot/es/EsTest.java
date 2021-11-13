package com.pk.springboot.es;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EsTest {
    @Autowired
    private TransportClient client;

    @Test
    public void test(){
        GetResponse response = client.prepareGet("intention", "record", "47_1298301818").get();
        System.out.println(response.getSource());
    }
}
