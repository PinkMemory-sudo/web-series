package com.pk.springboot.event;

import com.pk.springboot.model.ApiStatModel;
import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

    public MyEvent(ApiStatModel source) {
        super(source);
    }
}
