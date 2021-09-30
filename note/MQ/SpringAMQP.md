**自动配置**

RabbitAutoConfiguration

1. 连接工厂(RabbitProperties中的用户名，密码，vhost，channel等)

2. 还提供了RabbitTemplate来发送和接受消息
3. 还提供了AmqpAdmin（RabbitMQ系统管理组件）



# RabbitTemplate

发送消息

自己封装

自动序列化转换



**序列化**

默认MessageConvert是Simple。。。使用的是JDK序列化

![image-20210302222304404](/Users/chenguanlin/Documents/note/0img/image-20210302222304404.png)



**监听**

启动类上添加@EnableRabbit

@RabbitListener（）监听消息只要有消息来，就会调用该方法

逍遥获得消息头，可以将参数设置成Message

![image-20210302222840508](/Users/chenguanlin/Library/Application Support/typora-user-images/image-20210302222840508.png)



**AmqpAdmin**

用来管理Exchange，Queue，Binding等





Spring AMQP由两部分组成spring-amqp和spring-rabbit

AMQP模块进行抽象，不依赖于某个AMQP broker

然后可以使用特定的broker实现，例如spring-rabbit



**AMQP**



**Message**

AMQP没有定义一个Message类或者接口，当执行basicPublish()等操作时，消息会作为一个byte数组传递，其他属性作为另一个独立的参数进行传递，SpringMessage定义了一个类Message来封装Message，包括两个属性：byte[]和MessageProperties

The MessageProperties interface defines several common properties, such as 'messageId', 'timestamp', 'contentType', and several more.



```
Starting with versions 1.5.7, 1.6.11, 1.7.4, and 2.0.0, if a message body is a serialized Serializable java object, it is no longer deserialized (by default) when performing toString() operations (such as in log messages). This is to prevent unsafe deserialization. By default, only java.util and java.lang classes are deserialized. To revert to the previous behavior, you can add allowable class/package patterns by invoking Message.addAllowedListPatterns(…​). A simple wildcard is supported, for example com.something., *.MyClass. Bodies that cannot be deserialized are represented by byte[<size>] in log messages.
```



**Exchange**



**Queue**



**Binding**



**Connection Factory**

三种连接工厂：

* PooledChannelConnectionFactory

* ThreadChannelConnectionFactory

* CachingConnectionFactory



前两个是2.3添加的

PooledChannelConnectionFactory 



基于AMQP消息模型，提供了高度抽象的消息发送和接收模板

It also provides support for Message-driven POJOs with a "listener container"

spring-amqp is the base abstraction

spring-rabbit is the RabbitMQ implementation.

@RabbitListener

@RabbitHandler



消息应答机制



RabbitTemplate来接收发送消息



RabbitAdmin for automatically declaring queues, exchanges and bindings



























Broker 代表MQ程序

Exchange将消息转发到Queue，按一定的规则将消息路由转发到某个队列

Queue 消息没有直接发给Queue，Queue绑定到Exchange，将消息发送给Exchange































ACK消息确认机制

RabbitMQ有一个ACK机制。当消费者获取消息后，会向RabbitMQ发送回执ACK，告知消息已经被接收。不过这种回执ACK分两种情况：

- 自动ACK：消息一旦被接收，消费者自动发送ACK
- 手动ACK：消息接收后，不会发送ACK，需要手动调用



- 如果消息不太重要，丢失也没有影响，那么自动ACK会比较方便
- 如果消息非常重要，不容丢失。那么最好在消费完成后手动ACK

如何手动ACK































work消息模型

多个消费者使用同一个队列时，怎么负载均衡？

让消费者同一时间智能处理一个消息，能者多劳







多个消费者都有自己的队列，多个队列绑定到同一个Exchange

Exchange决定时广播还是单独发送给某个队列或者丢弃消息



**Exchange（交换机）只负责转发消息，不具备存储消息的能力，因此如果没有任何队列与Exchange绑定，或者没有符合路由规则的队列，那么消息会丢失！**





发布订阅

生产者不再指定队列，而是指定Exchange



路由模型

Exchange的类型为转发

生产者，向Exchange发送消息，发送消息时，会指定一个routing key

Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列

每个消费者一个队列



5 Topics 通配符模式

Exchange的类型：topics 



https://blog.csdn.net/kavito/article/details/91403659



# AmqpAdmin





# 使用



1. 获得RabbitTemplate和AmqpAdmin

与Mongo，MySQL相似，通过在配置文件中配置后直接注入template

2. 通过AmpqAdmin创建Exchange，queue，Binding
3. 通过RabbitTemplate发送消息









default exchange

 default routing key





声明Exchange

需要指定名字，那种路由规则(5个实现类) + RouteKey

Exchange接口有5个实现类：

* DirectExchange
* FanoutExchange
* TopicExchange
* HeadersExchange
* CustomExchange



**DirectExchange**

指定名字，是否持久化(默认true)，不实用时是否自动删除(默认false)，还可以用Map封装其他参数





**声明Queue**

使用amqp提供的Queue类

指定名字，是否持久化(默认true)，是否排他(只能被声明者使用，默认false)，不用时是否自动删除，其他queue需要的参数通过Map传入



**Binding**

使用amqp提供的Binding类

只有一个构造函数

指定队列名



```java

```



@RabbitListener

指定类型





默认的Exchange和Queue











