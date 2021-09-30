# Basic



**概述**

基于AMQP(高级消息队列协议)



摘要：

* 定义与用途
* 组件的作用
* 消息处理流程
* 最终一致性,持久化，传输确认，发布确认
* 广播
* 削峰
* 路由
* 集群



## 什么是消息队列

生产者生产消息，将消息发送到broker，再发送到目的地。

消息队列有两种目的地：队列和主题



## 消息队列的应用场景

1. 异步处理(将一些不需要立即生效的)
2. 应用解耦
3. 流量削峰，消息满了直接返回失败



## 名词



**broker**

消息的代理



**destination**

目的地，包含队列和主题两种形式



**queue**

(point-to-point)点对点的消息通信



**topic**

(Publish/subscribe)发布订阅的消息通信



**queue与topic的区别**

queue中可以有多个消费者，但是只会被一个消费掉

topic中也可以有多个消费者，但它的所有消费者都能收到消息





**JMS**

Java MessageServer实际上是指JMS API,包括创建，发送和接收

 JMS提供了两种消息模型，peer-2-peer(点对点)以及publish-subscribe（发布订阅）模型。

 常见的消息队列，大部分都实现了JMS API，可以担任JMS provider的角色，如ActiveMQ，Redis以及RabbitMQ等。

* 实现：ActivityMQ





**AMQP**

advanced message queuing protocol

最早用于解决金融领不同平台之间的消息传递交互问题

AMQP不从API层进行限定，而是直接定义网络交换的数据格式。这使得实现了AMQP的provider天然性就是跨平台的。意味着我们可以使用Java的AMQP provider，同时使用一个python的producer加一个rubby的consumer。

AMQP的生产者并不会直接将消息发布到队列中。AMQP在消息的生产者以及传递信息的队列之间引入了一种间接的机制：Exchange

* 实现：RabbitMQ
* 兼容JMS
* 包括点对点/发布订阅在内，有5种消息模型，与发布订阅差不多，但是路由更细致
* 与JMS不同，消息类型只能序列化成byte[]后再发送，所以可以跨平台跨语言
* 两种Spring都支持，并且提供了JmsTemplate和AmqpTemplate



区别：

AMQP为消息定义了线路层（wire-level protocol）的协议，而JMS所定义的是API规范

AMQP是跨语言跨平台的

JMS 支持TextMessage、MapMessage 等复杂的消息类型；而AMQP 仅支持 byte[] 消息类型（复杂的类型可序列化后发送）

由于Exchange 提供的路由算法，AMQP可以提供多样化的路由方式来传递消息到消息队列，而 JMS 仅支持 队列 和 主题/订阅 方式两种



# Rabbit核心概念





![image-20210302214341805](/Users/chenguanlin/Documents/note/0img/image-20210302214341805.png)

即：

消息生产者将消息发送到broker中的一个vhost中的一个Exchange，Exchange通过Binding发送到一个Queue。

消息消费者与消息队列建立一个连接，可以有多个通道，通过通道来获得消息



**Message**

由消息头和消息体组成，消息头时透明的，消息体是不透明的。

消息头由一系列可选属性组成(路由键routing-key，优先级priority，delivery-mode)

消息体是我们自定义

透明：指的是由规范的

**Publish**

消息的生产者，将消息发送给broker中的Exchange

**Exchange**

交换器，将收到的消息路由到指定的队列

有4种Exchange：direct，fanout，topic，headers。(后三者是发布订阅模式)

Exchange+Route Key来确定路由规则，消息发送到指定的Exchange后，将消息的route key按照Exchange的模式去匹配Exchange的route key，路由到不同消息队列。

**Queue**

消息队列，用来存储消息，一个消息可以保存到一个或多个消息队列中，直到消费者连接到这个队列把消息取走。

**Binding**

就是基于路由键将Exchange与Queue连接起来的路由规则。Exchange与Queue可以是多对多的关系，Route Key决定了消息会到哪个消息队列。

**Chanel**

每一次来取消息都建立一个TCP连接是非常消耗资源的，所以它会再一条连接中建立多个信道。

信道是用来节省资源

**Consumer**

从消息队列取消息的程序

**Virtue Host**

虚拟主机，简称vhost。

可以在消息队列服务器上划分多个vhost，每个vhost都相当于是一个RabbitMQ服务器，拥有自己的Exchange，Queue等资源，必须在连接是指定，RabbitMQ默认vhost是/(vhost通过路径来划分)

**Broker**

消息代理(消息队列服务器)



## 运行机制



核心就是路由的机制

Exchange通过路由键和binding将消息派发到队列



## **Exchange 的路由算法**



- Direct：如果 消息的routing key 与 binding的routing key 相同的，消息将会路由到该队列上；典型的点对点消息模型
- Topic：如果 消息的routing key 与 binding的routing key 符合通配符匹配的话，消息将会路由到该队列上；*匹配一个字符，#匹配一个或多个
- Headers：如果 消息参数表中的头信息和值 都与 bingding参数表中 相匹配，消息将会路由到该队列上；用的少
- Fanout：广播，不管消息的routing key和参数表的头信息/值是什么，消息将会路由到所有队列上。





Message Queue

多用于分布式系统之间的通信，实现MQ的两种主流方式：AMQP、JMS。

* JMS限定了必须使用Java语言；AMQP只是协议，不规定实现方式
* JMS规定了两种**消息模型**；而AMQP的消息模型更加丰富



RabbitMQ：基于AMQP协议

ActiveMQ：基于JMS

RocketMQ：基于JMS

Kafka：分布式消息系统



RabbitMQ由Erlang语言开发，需要安装与RabbitMQ版本对应的Erlang语言环境







使用MQ间接通信的优势

* 应用解耦
* 异步提速
* 削峰填谷



将不需要同步处理的并且耗时长的操作由消息队列通知消息接收方进行异步处理。减少了应用程序的响应时间





劣势

* 系统可用性降低
* 系统复杂度提高
* 一致性问题







MQ消息队列，通过生产者消费者模型，进行解耦

消息的可靠性，消息的幂等性

MQ的高可用

















## 消息模型





AMQP











## 使用：



# Docker安装RabbitMQ

1. 获得镜像。选择带management标签的(带web界面)
2. 运行。两个端口映射：5672 是主机端口，15672是web页面端口
3. 默认账号和密码是guest，可以在运行的时候通过-e设置，或者在管理界面添加
4. 可以在web管理界面创建vhost，Exchange，Queue，发送接收消息





```
docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:management
```













