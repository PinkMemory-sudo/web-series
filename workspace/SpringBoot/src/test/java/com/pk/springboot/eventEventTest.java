package com.pk.springboot;

import com.pk.springboot.event.MyEvent;
import com.pk.springboot.model.ApiStatModel;
import com.pk.springboot.service.ApiStatAspect;
import org.apache.catalina.core.StandardContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootTest
public class eventEventTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void test() {
        ApiStatModel apiStatModel = new ApiStatModel();
        apiStatModel.setAccountName("test");
        applicationContext.publishEvent(new MyEvent(apiStatModel));
    }
}
