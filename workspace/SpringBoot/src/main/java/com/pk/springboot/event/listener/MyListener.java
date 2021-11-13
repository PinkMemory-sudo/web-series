package com.pk.springboot.event.listener;

import com.pk.springboot.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyListener {

    @EventListener // 注意此处
    public void handleDemoEvent(MyEvent event) {
        log.info("发布的data为:{}", event.getSource());
    }
}