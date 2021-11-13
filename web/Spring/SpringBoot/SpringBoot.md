

`Enable*`注释可以是一个很好的起点

@Import

@ComponentScan





# 自动配置



SpringBoot会根据引入的jar包尝试自动配置。

通过@EnableAutoConfiguration` or `@SpringBootApplication来开启自动配置，通常会选一个加载主类上



@SpringBootApplication包含了三个注解

@Configuration、@EnableAutoConfiguration、@ComponentScan



## 更改自动配置

自动配置是非侵入式的，可以使用我们的配置来代替自动配置，就是我们提供自己的配置(注入bean或在配置文件中更改)



## 禁用指定的自动配置



**@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})**

排除掉自动配置的类



如果类不在类路径下，可以使用excludeName来指定全限定类名



一可以通过配置文件spring.autoconfigure.exclude指定要排除的自动配置



# SpringBean and Dependency Injection



@Component, @Service, @Repository, @Controller会将对象注册成bean

@ComponentScan来发现bean

@Autowired来通过构造自动注入

















---



# ApplicationRunner&CommandLineRunner

如果启动后需要运行一些特定的代码，可以实现`ApplicationRunner`或`CommandLineRunner`接口。该方法在`SpringApplication.run(…)`完成之前就被调用。

该合同非常适合应在应用程序启动后但开始接受请求之前运行的任务







# 缓存



## 抽象缓存

Spring从3.1开始定义了org.springframework.cache.Cache和org.springframework.cache.CacheManager接口来统一不同的缓存技术；并支持使用Java Caching（JSR-107）注解简化我们进行缓存开发；Spring Cache 只负责维护抽象层，具体的实现由你的技术选型来决定。将缓存处理和缓存技术解除耦合。

- Cache：缓存抽象的规范接口，缓存实现有：RedisCache、EhCacheCache、ConcurrentMapCache等
- CacheManager：缓存管理器，管理Cache的生命周期



## JSR107

Java Caching（JSR-107）定义了5个核心接口，分别是CachingProvider, CacheManager, Cache, Entry和 Expiry。

- CachingProvider：创建、配置、获取、管理和控制多个CacheManager
- CacheManager：创建、配置、获取、管理和控制多个唯一命名的Cache，Cache存在于CacheManager的上下文中。一个CacheManager仅对应一个CachingProvider
- Cache：是由CacheManager管理的，CacheManager管理Cache的生命周期，Cache存在于CacheManager的上下文中，是一个类似map的数据结构，并临时存储以key为索引的值。一个Cache仅被一个CacheManager所拥有
- Entry：是一个存储在Cache中的key-value对
- Expiry：每一个存储在Cache中的条目都有一个定义的有效期。一旦超过这个时间，条目就自动过期，过期后，条目将不可以访问、更新和删除操作。缓存有效期可以通过ExpiryPolicy设置



## 重要注解

- @Cacheable：针对方法配置，能够根据方法的请求参数对其结果进行缓存
- @CacheEvict：清空缓存
- @CachePut：既调用方法，又更新缓存数据
- @EnableCaching：开启基于注解的缓存
- @Caching：定义复杂的缓存规则





## 使用

1. 添加依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

2. 启动类上添加`@EnableCaching`开启缓存

3. 通常在查询的方法上加@Cacheable

   @Cacheable的几个重要属性

   * cacheNames/value：指定缓存组件的名字，数组形式
   * key指定键值对的键，可以使用SqEL
   * keyGenerator：指定key的生成器的组件id(key/keyGenerator：二选一)
   * cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
   * condition：指定符合条件的情况下才缓存；使用SpEl表达式
   * unless:指定不缓存的情况
   * sync：是否使用异步模式

   ```java
   @Cacheable(value = {"emp"}, key = "#id",condition = "#a0>=1",unless = "#a0!=2")
   public Employee getEmp(Integer id) {
     Employee employee = this.employeeMapper.getEmpById(id);
     LOG.info("查询{}号员工数据",id);
     return employee;
   }
   ```

   也可以自定义key生成器，注入一个KeyGenerator

   ```java
   @Configuration
   public class CacheConfig {
   
       @Bean(value = {"myKeyGenerator"})
       public KeyGenerator keyGenerator(){
           return new KeyGenerator() {
               @Override
               public Object generate(Object target, Method method, Object... params) {
                   return method.getName()+"["+ Arrays.asList(params).toString()+"]";
               }
           };
       }
   }
   ```

   通常在更新的方法上加@CachePut注解，表示调用方法，更新缓存

   ```java
   @CachePut(value = {"emp"}, key = "#result.id")
   public Employee updateEmp(Employee employee){
     employeeMapper.updateEmp(employee);
     LOG.info("更新{}号员工数据",employee.getId());
     return employee;
   }
   ```

   在删除方法上加@CacheEvic注解

   - key：指定要清除的数据
   - allEntries = true：指定清除这个缓存中所有的数据
   - beforeInvocation = false：默认代表缓存清除操作是在方法执行之后执行
   - beforeInvocation = true：代表清除缓存操作是在方法运行之前执行

   ```java
    @CacheEvict(value = {"emp"}, beforeInvocation = true,key="#id")
   public void deleteEmp(Integer id){
     employeeMapper.deleteEmpById(id);
   }
   ```

4. @Caching 可以定义复杂的缓存逻辑，继承查询和更新操作

   ```java
   @Caching(
     cacheable = {
       @Cacheable(/*value={"emp"},*/key = "#lastName")
     },
     put = {
       @CachePut(/*value={"emp"},*/key = "#result.id"),
       @CachePut(/*value={"emp"},*/key = "#result.email")
     }
   )
   public Employee getEmpByLastName(String lastName){
     return employeeMapper.getEmpByLastName(lastName);
   }
   ```

5. @CacheConfig用来抽取缓存的公共配置，然后在类加上就可以

​      `@CacheConfig(cacheNames = {"emp"},cacheManager = "employeeCacheManager")`





@EnableCaching开启缓存

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class MathService {

    @Cacheable("piDecimals")
    public int computePiDecimal(int i) {
        // ...
    }

}
```

本示例说明了在可能耗资巨大的操作上使用缓存的方法。在调用之前`computePiDecimal`，抽象将在`piDecimals`高速缓存中查找与`i`参数匹配的条目。如果找到条目，则高速缓存中的内容会立即返回给调用方，并且不会调用该方法。否则，将调用该方法，并在返回值之前更新缓存。

spring-boot-starter-cache启动依赖

**spring.cache.cache-names**=cache1,cache2 **spring.cache.redis.time-to-live**=10m



# SQEL表达式



# Messaging



# RestTemplate



REST风格





使用RestTemplate调用远程rest服务

由于`RestTemplate`实例在使用前通常需要自定义，因此Spring Boot不提供任何单个自动配置的`RestTemplate`bean。需要我们自己注入。



**注入RestTemplate**

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
  // Do any additional configuration here
  return builder.build();
}
```



**forEntity与forObject的区别**

entity表示responseEntity，包含了相应头，Object是只有响应体



**传参**



**exchange**

通用的发送请求的方法



**HttpEntity**

Represents an HTTP request or response entity, consisting of headers and body.





* Get请求分为，无参/带参，map传参/占位符传参，只要返回体/状态和body全要
* Post请求，必须有参数，但可以是null。与get相比多了postForLocation，即提交新资源后返回新资源的uri
* 封装的post没有返回体，可使用exchange
* 封装的delete没有返回体，可使用exchange
* 通用的请求方法，上面的都是高度封装的，根据需求，可以使用exchange，指定url，请求方法，HttpEntity，返回类型。













# 定时任务

 一、基于注解

1. 开启定时任务

   启动类上添加@EnableScheduling

2. 添加定时任务要执行的方法

   方法上添加@Scheduled注解



@Scheduled

***cron参数***

| 单位 | 允许值                      | 允许通配符    |
| :--- | :-------------------------- | :------------ |
| 秒   | 0-59                        | , - * /       |
| 分   | 0-59                        | , - * /       |
| 时   | 0-23                        | , - * /       |
| 日   | 1-31                        | , - * / ? L W |
| 月   | 1-12 或 JAN-DEC(大小写均可) | , - * / ?     |
| 周   | 1-7 或 SUN-SAT(大小写均可)  | , - * / ? L # |

| 符号     | 含义                                                         |
| :------- | :----------------------------------------------------------- |
| *        | 所有值，每一秒/分钟/小时......                               |
| -        | 一组值，比如秒上的0-10表示0-10内每1秒执行一次                |
| ,        | 指定多个值                                                   |
| /        | m/n  从m开始，每隔n执行一次                                  |
| ？       | 不需要关心的值，每周一执行不用关心时几号                     |
| L        | 最后，在日字段上使用L表示当月最后一天,在周字段上使用3L表示该月最后一个周四 |
| W(work?) | 只允许用在日字段上,表示距离最近的该日的工作日.工作日指的是周一至周五 |
| #        | 只允许在周字段上,表示每月的第几个周几,如2#3,每月的第3个周二  |

































@Scheduled

String cron，包含的触发器：

- second
- minute
- hour
- day of month
- month
- day of week

| 单位 | 允许值                      | 允许通配符    |
| :--- | :-------------------------- | :------------ |
| 秒   | 0-59                        | , - * /       |
| 分   | 0-59                        | , - * /       |
| 时   | 0-23                        | , - * /       |
| 日   | 1-31                        | , - * / ? L W |
| 月   | 1-12 或 JAN-DEC(大小写均可) | , - * / ?     |
| 周   | 1-7 或 SUN-SAT(大小写均可)  | , - * / ? L # |



| 符号 | 含义                                                 |
| :--- | :--------------------------------------------------- |
| *    | 所有值.在秒字段上表示每秒执行,在月字段上表示每月执行 |
| -    | 设置一个区间                                         |
| ,    | 设置几个值                                           |
| /    | n/m,从n起，每隔m执行                                 |
| ?    | 不需要关心的值，比如每周一更新，不需要指定是几号     |
| L    | L表示当月最后一天，nL表示当月最后一周的周(n-1)       |
| W    | 只允许用在日字段上，表示距改日最近的工作日           |
| \#   | 只允许在周字段上，                                   |

***示例***



# AOP





# 异步任务



**使用**：

1. 启动类上加注解@EnableAsync
2. 需要异步执行的方法上加@Async



不需要返回值的异步任务，

调用同一个类中的异步任务会变成同步的



我们可以在Spring中注入一个线程池，在@Async时可以指定使用的线程池



**需要返回值的异步任务**

用AsyncResult或者CompletableFuture

CompletableFuture是对Feature的增强，Feature只能处理简单的异步任务，而CompletableFuture可以将多个异步任务进行复杂的组合。



提交任务

```
CompletableFuture<String> result = asyService.withResult();
return result.get();
```



获得任务执行结果：

CompletableFuture的get方法





















给继承关系添加公用的方法非常容易，但是需要为多个不具有继承关系的对象添加相同的功能时(日志记录、性能监控等)，代码量就大了。































定义切面

切点

前置通知，后置通知，环绕通知，返回通知，异常通知































启动依赖

`spring-boot-starter-web`

