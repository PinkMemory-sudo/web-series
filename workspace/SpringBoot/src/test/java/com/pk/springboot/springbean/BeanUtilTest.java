package com.pk.springboot.springbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Scanner;

@SpringBootTest
public class BeanUtilTest {

    @Test
    public void test(){
        School school = new School("SJ", "201");
        A tom = new A("TOM", "101", "701",school);
        B jack = new B();
        // 把能与源对象匹配的属性复制到另一个对象
        BeanUtils.copyProperties(tom,jack);
        System.out.println(jack);

    }

    @Data
    @AllArgsConstructor
    class A{
        private String name;
        private String addr;
        private String company;
        private School school;
    }

    @Data
    class B{
        private String name;
        private String addr;
        private String home;
        private School school;
    }

    @Data
    @AllArgsConstructor
    class School{
        private String name;
        private String addr;
    }
}
