# 概述

已经停止更新，但大部分厂商还在使用

为什么用服务注册中心？

解耦，简化RPC结构。

什么是服务治理？

用来管理服务和服务之间的依赖，调用，负载均衡，容错，实现服务的注册与发现



## 架构

**eurake采用CS架构**

所以有两个组件：server，client

eurake server作为服务注册中心，eurake client连接到注册中心，将自己的信息(服务名，IP等)提交到注册中心并维持心跳，这要就可以通过服务中心来监控服务。其他服务消费者也注册到eurake，同时拉取其他服务的信息，通过服务名调用服务。

**服务都连接到eurake，维持心跳来注册自己，拉取其他服务的信息**

CS架构，所以可以类比MySQL，服务需要在配置文件中配置连接

心跳默认30s，如果eurake多个周期内都没有收到某个服务的心跳，则会移除该服务



**同时集成了ribbon(以前，现在替换成LoadBalence了)**



## CAP理论



## 名词

**实例**：多个又相同服务明的服务组成一个实例



## Eureka的自我保护机制

高可用的设计理念



现象：页面出现红色的字(短时间多个微服务挂了)

Eureka Server将会尝试保护服务注册表信息，不移除服务。术语CPA里面的AP。

禁止自我保护机制：不保存服务信息，直接移除

`eureka.server.enable-self-preservation=false`



## 停止更新

不用深入研究了，够用就行。

替代方案：zookeeper，consul，nacos



# 使用



## Eureka server的配置

1. 添加依赖

   ```xml
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   </dependency>
   ```

   

2. 启动类上加注解

   @EnableEurekaServer

3. 配置连接

```properties
server.port=8001
spring.application.name=register01
# Eureka中的实例名
eureka.instance.hostname= localhost
# 不想注册中心注册自己
eureka.client.register-with-eureka=false
# 自己就是注册中心，不需要拉取服务
eureka.client.fetch-registry=false
# 其他服务与Eureka交互的地址，服务查询和注册都依赖这个地址，浏览器查看的方法是host:port
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
# 禁用自我保护机制
eureka.server.enable-self-preservation=false
```



## Eureka Client



1. 添加依赖

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

2. 主启动类上加注解

```java
@EnableEurekaClient  // 或者@EnableDiscoveryClient
```

@EnableDiscoveryClient 可以是其他注册中心,是官方推荐的

1. 配置连接

```properties
server.port=8011
# 注册到eureka时的服务名就用这个
spring.application.name=provider01
# 配置注册中心的连接，默认会注册和拉取服务
eureka.client.service-url.defaultZone=http://localhost:8001/eureka/
```



## Eureka server集群

注册中心往往不能是单个的，要用集群来实现高可用

互相注册，相互守望，对外暴露一个整体



**将自己注册到另外几个注册中心**

```properties
server.port=8001
spring.application.name=register01
# Eureka中的实例名
eureka.instance.hostname= localhost01
# 不想注册中心注册自己
eureka.client.register-with-eureka=false
# 自己就是注册中心，不需要拉取服务
eureka.client.fetch-registry=false
# 与另一个注册中心相互注册
eureka.client.service-url.defaultZone=http://localhost:8002/eureka/
```

然后访问eureka的页面，返现DS Replicas中有彼此



其他服务要将自己注册到全部的注册中心

```properties
server.port=8011
spring.application.name=provider01
# 配置注册中心的连接，默认会注册和拉取服务
eureka.client.service-url.defaultZone=http://localhost:8001/eureka/,http://localhost:8002/eureka/
```



**Eureka client**

只需要在url.defaultZone中指定所有注册中心



## *Eureka client集群*

把某个服务启动多个，都注册到注册中心，保持application-name不变



```properties
# 是否允许开启自我保护模式，缺省：true
# 当Eureka服务器在短时间内丢失过多客户端时，自我保护模式可使服务端不再删除失去连接的客户端
eureka.server.enable-self-preservation = false

# Peer节点更新间隔，单位：毫秒
eureka.server.peer-eureka-nodes-update-interval-ms = 

# Eureka服务器清理无效节点的时间间隔，单位：毫秒，缺省：60000，即60秒
eureka.server.eviction-interval-timer-in-ms = 60000
# 服务名，默认取 spring.application.name 配置值，如果没有则为 unknown
eureka.instance.appname = eureka-client

# 实例ID
eureka.instance.instance-id = eureka-client-instance1

# 应用实例主机名
eureka.instance.hostname = localhost

# 客户端在注册时使用自己的IP而不是主机名，缺省：false
eureka.instance.prefer-ip-address = false

# 应用实例IP
eureka.instance.ip-address = 127.0.0.1

# 服务失效时间，失效的服务将被剔除。单位：秒，默认：90
eureka.instance.lease-expiration-duration-in-seconds = 90

# 服务续约（心跳）频率，单位：秒，缺省30
eureka.instance.lease-renewal-interval-in-seconds = 30

# 状态页面的URL，相对路径，默认使用 HTTP 访问，如需使用 HTTPS则要使用绝对路径配置，缺省：/info
eureka.instance.status-page-url-path = /info

# 健康检查页面的URL，相对路径，默认使用 HTTP 访问，如需使用 HTTPS则要使用绝对路径配置，缺省：/health
eureka.instance.health-check-url-path = /health

```





# DiscoveryClient

对于注册到Eureka里的微服务，通过服务发现来获得该服务的信息。



自动注入DiscoveryClient

1. 启动类上添加@DiscoveryClient

2. 通过DiscoveryClient获得服务的信息

```java
@Resource
private DiscoveryClient discoveryClient;
discoveryClient.getAllKnownRegions();
discoveryClient.getInstancesById()
```



# Ribbon

* 服务调用者负载均衡工具，Eureka-Client已经集成了Ribbon，现在已经换成LoadBalence了
* 不再更新，已经进入维护状态
* 替换方案：Spring的LoadBalance
* 默认是轮询的负载均衡算法



**与Nginx的区别**

* 一个是进程内(本地客户端)的LB，一个是进程外(服务端)的LB
* 所有请求交给nginx，由nginx转发
* ribbon会告诉消费者调用哪个



**替换Ribbon的载均衡算法**

就是自己注入一个IRule



**IRule接口**



**RoundRobinRule**

轮询算法



RandomRule



RetryRule

先按照轮询获取服务，失败的话则在可用时间内重试



响应快的



并发量最小的



在配置类中注入一个IRule，但是**不能放到ComponentScan所能扫描的包下**，因为这样就会被共享，所有客户端都会使用这种算法

1. 新建个包,添加配置类

```java

```

2. 通过@RibbonClient指定调用某个服务时使用的负载均衡算法



## 手写负载均衡算法

rest接口请求的次数%服务器个数=服务器下表，重启后置1

40-42



