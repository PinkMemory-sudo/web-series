package com.pk.springboot.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:person.yml")
@ConfigurationProperties(prefix = "person")
@Data
public class PersonConfig {
    private String name;
    private int age;
}
