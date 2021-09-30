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



# Basic Concept



 Spring AMQP实现了 AMQP 解决方案。提供了发送和接收消息的抽象模板。目前只有一个 RabbitMQ 实现。但是，抽象已经在。

* 异步处理消息的监听器
* RabbitTemplate 来收发消息
* RabbitAdmin 管理exchange，queue和bindings



？

* `@RabbitListener`指定内容类型

* 消息转换器Jackson2JMessageConverter

* @SpringRabbitTest

* ReturnCallback->ReturnsCallback

* 现在可以使用一个新的侦听器容器属性 consumeday; 在使用 RabbitMQ Sharding Plugin 时，它非常有用。













路由

更复杂的路由可以通过各种路由组合起来













# 使用



**Quick Start**

1. 添加spring-rabbit

```xml
<dependency>
  <groupId>org.springframework.amqp</groupId>
  <artifactId>spring-rabbit</artifactId>
  <version>2.3.10</version>
</dependency>
```

2. 发送接收消息

```java
ConnectionFactory connectionFactory = new CachingConnectionFactory();
AmqpAdmin admin = new RabbitAdmin(connectionFactory);
admin.declareQueue(new Queue("myqueue"));
AmqpTemplate template = new RabbitTemplate(connectionFactory);
template.convertAndSend("myqueue", "foo");
String foo = (String) template.receiveAndConvert("myqueue");
```

// 没有指定Exchange，使用默认的Exchange和binding



**JavaConfig**

```java
@Configuration
public class RabbitConfiguration {

    @Bean
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue myQueue() {
       return new Queue("myqueue");
    }
}
```









## **生产消费模型**

生产消费模型实际使用默认的Exchange和binding就可以,	写下更清除。

```java
DirectExchange exchange = new DirectExchange("TestExchange");
        Queue queue = new Queue("TestQueue");
        Binding binding = new Binding("TestQueue", 		         		     	Binding.DestinationType.QUEUE,
          "TestExchange", "test", null);
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);
// 添加消息
template.convertAndSend("test", "foo");
// 获得消息
String foo = (String) template.receiveAndConvert("TestExchange");
```



## **发布订阅模型**

1. 创建Exchange，queue，binding

```java
@SpringBootTest
public class PubSubTest {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        TopicExchange fodderTopic = new TopicExchange("fodderTopic");
        Queue rabbit = new Queue("rabbit");
        Queue hen = new Queue("hen");
        Binding tomHenBinding = BindingBuilder.bind(hen).to(fodderTopic).with("tom.*");
        Binding tomRabbitBinding = BindingBuilder.bind(rabbit).to(fodderTopic).with("tom.*");
        Binding henBinding = BindingBuilder.bind(hen).to(fodderTopic).with("hen.*");
        Binding rabbitBinding = BindingBuilder.bind(hen).to(fodderTopic).with("rabbit.*");
        amqpAdmin.declareQueue(rabbit);
        amqpAdmin.declareQueue(hen);
        amqpAdmin.declareExchange(fodderTopic);
        amqpAdmin.declareBinding(tomRabbitBinding);
        amqpAdmin.declareBinding(tomHenBinding);
        amqpAdmin.declareBinding(henBinding);
        amqpAdmin.declareBinding(rabbitBinding);
    }

    @Test
    public void sendMessage() {
        // Message(byte[] body, MessageProperties messageProperties)
        // 同构消息头个消息体构造Message
        HashMap<String, String> map = new HashMap<>();
        map.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // 如果不需要消息头，可使用convertAndSend
        rabbitTemplate.convertAndSend("fodderTopic", "hen.", map);
        rabbitTemplate.convertAndSend("fodderTopic", "tom.", map);
    }

    @Test
    public void receiveTest() {
        log.info("rabbit:{}", rabbitTemplate.receiveAndConvert("rabbit"));
        log.info("hen: {}", rabbitTemplate.receiveAndConvert("hen"));
    }
}
```

**Exchange，queue，binding之间的关系**

AMQP模型的三大件

queue就是存消息的，而binding用来定义哪些key可以到该queue。好像queue与binding结合，就能实现key到queue的映射，为什么还要经过Exchange？一个queue可以定义多个binding，exchange可以给这些binding分组，比如hen的queue，跟饲养相关的消息发送到一个Exchange，跟售卖相关的绑定到另一个Exchange。所以在创建Binding时，除了说明routingKey与queue的关系，还需要指定这个binding分给哪个Exchange。

所以平时思考时可不考虑Exchange，只思考queue和binding的关系：queue用来存储消息，binding用来指定queue可以接收什么类型的消息，一个queue可以用过个binding，所以可以接收多种消息。



一般通过@bean将Exchange，queue，binding都注入，发消息时直接使用。



## 异步处理

比如你发工资了，然后卖了白酒的基金，有卖了医药的基金。这种操作实际可以异步的并行处理：将你发工资的好消息发送到医药和白酒的消息队列，白酒和医疗就分别购入。

这次用监听器来写

```java
@Slf4j
@Configuration
public class SyncTestConfig {

    // 白酒的消息队列
    @Bean
    public Queue whiteWine() {
        return new Queue("whiteWine");
    }

    // 医药的消息队列
    @Bean
    public Queue medicine() {
        return new Queue("medicine");
    }

    // Exchange
    @Bean
    public TopicExchange funtExchange() {
        return new TopicExchange("fund");
    }

    // 白酒bind工资信息
    @Bean
    public Binding wwBind() {
        return BindingBuilder.bind(whiteWine()).to(funtExchange()).with("salary");
    }

    // 医药bind工资消息
    @Bean
    public Binding mdBind() {
        return BindingBuilder.bind(medicine()).to(funtExchange()).with("salary");
    }

    // 告诉券商们，我发工资了
    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> template.convertAndSend("fund","salary", "998");
    }

    // 监听队列
    @RabbitListener(queues = "whiteWine")
    public void wlisten(String in) {
        log.info("ALLIN: {}",in);
    }

    // 监听队列
    @RabbitListener(queues = "medicine")
    public void mlisten(String in) {
        log.info("ALLIN: {}",in);
    }

}
```



## 流量削峰





## 解耦

A服务需要向B,C服务发送消息，现在D也需要怎么办？MQ





**发布是异步的，怎么检测是否成功？**

不能路由的消息由 RabbitMQ 删除



