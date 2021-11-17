package com.pk.springboot.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AopTest {

    @Autowired
    private Boy boy;

    @Autowired
    private Girl girl;

    @Test
    public void test() {
        System.out.println(boy.buy());
        System.out.println(girl.buy());
    }
}
