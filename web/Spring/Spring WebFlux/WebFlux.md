与SpringMVC比较

SpringMVC基于ServletAPI，使用的是同步阻塞I/O模型。一个请求对应一个线程

SpringFlux是异步非阻塞的web框架，它能够充分利用多核 CPU 的硬件资源去处理大量的并发请求。需要Servlet 3.1+ 以上的容器，默认情况下使用 Netty 作为服务器，WebFlux 不支持 MySql。





**WebFlux 并不能使接口的响应时间缩短，它仅仅能够提升吞吐量和伸缩性**





I/O模型：磁盘IO/网络I/O

