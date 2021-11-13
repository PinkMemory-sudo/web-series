**72-73**

网关用来，挡在前面进行日志，限流，权限安全等工作。



动态路由





流程



# 三大概念



## 路由

Path映射到url



## 断言

用来干什么的



常用11中断言



## 过滤器



# 路由配置的两种方法



## 1. 配置文件



## 2. 注入RouteLocator

```java
@Bean
    public RouteLocator remoteRoutes(RouteLocatorBuilder builder) throws Exception {
        return builder.routes().route("id",p -> p
                // 匹配哪个路径
                .path("/**")
                // 转发到哪
                .uri("https://www.cnblogs.com")
                ).build();
    }
```

route两种重载来添加路由：

`route(Function<PredicateSpec, AsyncBuilder> fn)`

`route(String id, Function<PredicateSpec, AsyncBuilder> fn)`



**通过微服务名实现动态路由**

1. 配置文件中开启



2. 使用服务名