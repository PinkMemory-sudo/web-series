# 概述



* 停止更新
* 功能很强大：服务降级，服务熔断，服务限流，服务隔离等



扇出：A调B，C，B和C由调用其他服务，这就是所谓的扇出。



解决的问题：

​		分布式系统，各个服务直接相互调用，调用链路太长，一个服务超时出错，可能会引起多个服务阻塞，造成雪崩。一个服务出错时，**不要影响其他的服务。不长时间等待占用资源**,占这资源不放，而消费者由访问这个出错的生产者，资源都堵到这，把tomcat的线程池占满，其他服务得不到线程也会被拖死。



# 服务降级



调用某个服务异常时，备选方案是什么，不要影响其他服务，避免连锁故障





## 什么时候会服务降级

* 服务调用异常或超时
* 服务熔断触发了服务降级
* 线程池/信号量打满时



服务降级客户端和服务端都可以做，但是一般放在客户端



**服务提供者**

服务提供者的自身加固

超时或者出错的备选方案



**服务消费者**

1. 开启hystrix
2. 主启动类上加注解
3. 业务类(备选方案)

56-64







# 服务熔断



消费者达到最大访问量时，直接拉闸，拒绝访问，然后通过服务降级直接返回，不再请求生产者。与服务降级的区别就是直接返回了，没有再去调用服务提供者。





# 服务限流

高并发时，服务消费者设置每秒接收多少个请求



