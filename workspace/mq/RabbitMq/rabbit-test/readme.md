**模拟生产者消费者**

通过接口下单，发送下单消息，监听器收到消息后出库。



# **实现五种队列模式**



## Simple queue



```java
@RestController
@Slf4j
public class Simple {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 创建队列
    @Bean
    public Queue simpleQueue() {
        return new Queue("simple_queue");
    }

    @GetMapping("/sent_simple_msg")
    public void sentMsg() {
        rabbitTemplate.convertAndSend("simple_queue", "simple");
    }

    // 消费者1
    @RabbitListener(queues = "simple_queue")
    public void consumer1(Message message) {
        log.info("consumer get msg: {}", message);
    }
    
}
```



## work queue

一个队列对应多个消费者，消费者轮询处理消息。

```java
@RestController
@Slf4j
public class Work {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 创建队列
    @Bean
    public Queue workQueue() {
        return new Queue("work_queue");
    }

    @GetMapping("/sent_work_msg")
    public void sentMsg() {
        rabbitTemplate.convertAndSend("work_queue", "work");
    }

    // 消费者1
    @RabbitListener(queues = "work_queue")
    public void consumer1(Message message) {
        log.info("consumer1 get msg: {}", message);
    }

    // 消费者2
    @RabbitListener(queues = "work_queue")
    public void consumer2(Message message) {
        log.info("consumer2 get msg: {}", message);
    }

}
```

simple与wark相比，都是使用的没有名字的Exchange，routingKey都是队列名称，只是wark一个队列有多个消费者。



## Fanout Exchange

一个消费者一个queue，将多个queue绑定到一个Exchange。会将消息发送给每一个Queue



