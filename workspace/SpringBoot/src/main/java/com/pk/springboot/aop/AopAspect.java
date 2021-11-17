package com.pk.springboot.aop;

import com.pk.springboot.annotation.ApiStat;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AopAspect {




    @Before("execution(* com.pk.springboot.aop.IBuy.buy(..))")
    public void statBeforeApi() {
        System.out.println("hello");
    }
}
