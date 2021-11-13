* 基于SpringFramework5和SpringBoot2.0
* 访问量限制
* Path Rewriting
* 集成了Circuit Breaker和Spring Cloud DiscoveryClient



简单高效的路由API





引入SpringCloud Gateway



引入后不想使用可以用`spring.cloud.gateway.enabled=false` 关闭





**注意**

Spring Cloud Gateway is built on Spring Boot 2.x, Spring WebFlux, and Project Reactor. As a consequence, many of the familiar synchronous libraries (Spring Data and Spring Security, for example) and patterns you know may not apply when you use Spring Cloud Gateway

it does not work in a traditional Servlet Container or when built as a WAR.





术语



# GateWay的三大核心概念



## **Route**

定义：由ID，目标uri，一些断言和过滤器组成。断言成立是取路由

用来将请求发给不同服务器，路由就是**转发规则**

路由可以理解成是转发



**Predicate**

是Java8的Predicate，输入是Spring Framework ServerWebExchange，用来匹配HTTP的request，如请求头和参数等

**Filter**

在请求被路由前后对请求进行修改

Spring Framework GatewayFilter的实例

can modify requests and responses before or after



熔断限流容错，反向代理，鉴权，日志监控s

集成了netty，servlet是请求来时，从线程池充获取空闲线程调用service函数，当并发量高时，线程的代价是昂贵的



网关前十nginx等负载均衡，将请求发给不同网关



基于异步非阻塞模型进行开发，zuul是同步阻塞式IO模型

动态路由：能够匹配任何路由属性

集成列Histrix熔断器





# Gateway的工作流程



* 客户端向SpringCloud Gateway发送请求

* Gateway Handler Mapping将请求映射到与请求匹配的Gateway Web Handle
* Handle通过指定的过滤器链将请求发送到微服务然后返回
* 在过滤器链中可以对请求进行修改
* pre过滤前可以进行参数校验，鉴权，流量监控，日志输出，协议转换等
* post,过滤后可以修改响应，响应头，等



# 配置网关



# 配置动态路由

用服务明代替服务地址



路由



通过filter



























