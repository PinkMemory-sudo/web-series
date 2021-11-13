package com.pk.springboot.service;

import com.pk.springboot.annotation.ApiStat;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.StringJoiner;

@Aspect
@Component
@Slf4j
public class ApiStatAspect {

    //切入点描述 这个是controller包的切入点
//    @Pointcut("@annotation(apiStat)")
//    public void apiStat(ApiStat apiStat) {
//
//    }


    @Before("@annotation(apiStat)")
    public void statBeforeApi(JoinPoint joinPoint, ApiStat apiStat) {
//        StringJoiner joiner = new StringJoiner("\001");
//        joiner.add("ApiStat");
//        // 获得request请求
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        String requestMethod = request.getMethod();
//        String url = request.getRequestURL().toString();
//        long st = Instant.now().getEpochSecond();
//        String methodName = joinPoint.getSignature().getName();
//        Object[] args = joinPoint.getArgs();
//        Class<?>[] argTypeArray = new Class[args.length];
//        for (int i = 0; i < args.length; i++) {
//            argTypeArray[i] = args[i].getClass();
//        }
//        try {
//            Method method = joinPoint.getTarget().getClass().getMethod(methodName, argTypeArray);
//            ApiStat annotation = method.getAnnotation(ApiStat.class);
//            String descript = annotation.value();
//            joiner.add("admin").add(url).add(requestMethod).add(st + "").add(methodName).add(descript);
//            String logStr = joiner.toString();
//            log.info(logStr);
//            String[] split = logStr.split("\001");
//            System.out.println(Arrays.toString(split));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        System.out.println(apiStat.value());
    }
}
