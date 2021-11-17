# Spring



**什么是Spring**



**DispatcherServlet的工作原理**



**Spring中的bean是线程安全的吗**







## AOP



**什么是AOP**

面向切面编程，通过预编译和运行时动态代理来实现在不修改源码的情况下，给程序统一添加功能的技术。可以将非业务代码统一提取出来处理。



**代理模式**





**AOP的名词**



**AOP都有哪些实现**

Spring AOP就是基于动态代理的，如果要代理的对象，实现了某个接口，那么Spring AOP会使 用JDK Proxy，去创建代理对象，而对于没有实现接口的对象，就无法使用 JDK Proxy 去进行代 理了，这时候Spring AOP会使用Cglib。



**SpringAOP和AspectJAOP的区别**

SpringAOP是运行时增强，AspectJ是编译时增强。 

Spring AOP 基于代理(Proxying)，而 AspectJ 基于字节码操作(Bytecode Manipulation)。

Spring AOP 已经集成了 AspectJ，AspectJ 相比于 Spring AOP 功能更加强大，但是 Spring AOP 相对来说更简单。但是，当切面太多的话，最好选择 AspectJ ，它比Spring AOP 快很多。





## IOC



**什么是控制反转**

就是将对象的创建和管理交给Spring去做，怎么创建对象提前定义好，需要时直接使用。



**什么是Spring容器**



**Spring中有多少Ioc容器**



WebApplicationContext，BeanFactory



**IOC实现原理**



**什么是依赖注入**



**依赖注入有几种方法**





**bean的作用域**



| 作用域         | 描述 |
| -------------- | ---- |
| singleton      |      |
| prototype      |      |
| request        |      |
| session        |      |
| global-session |      |



**Spring中的bean声明周期**





**容器的声明周期**



**SpringBean是线程安全的吗**



**@Component 和 @Bean 的区别是什么**

* @Component 注解作用于类，而 @Bean 注解作用于方法。
* @Component通过@ComponentScan扫描自动装配，@Bean方法返回一个bean，与@Configuration一起使用



## 配置文件



## MVC



**用到了哪些设计模式**





## 注解



**@Required有什么用**



**@Qualifier**



**@Lazy(true)**

延迟初始化



**@PostConstruct**



**@PreDestory**



**@Primary**



**优先级**



谈谈自己对于 **Spring IoC** 和 **AOP** 的理解

IoC(Inverse of Control:控制反转)是一种设计思想，就是 将原本在程序中手动创建对象的控制 权，交由**Spring**框架来管理。将对象之间的相互依赖关系交给 IoC 容器来管理，并由 IoC 容器完成对象的注入。这样可以很大 程度上简化应用的开发，把应用从复杂的依赖关系中解放出来。

AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关，却为业务模块所共 同调用的逻辑或责任(例如事务处理、日志管理、权限控制等)封装起来，做横向的扩展。



**SpringAOP**

Spring AOP就是基于动态代理的，如果要代理的对象，实现了某个接口，那么Spring AOP会使 用JDK Proxy，去创建代理对象，而对于没有实现接口的对象，就无法使用 JDK Proxy 去进行代 理了，这时候Spring AOP会使用Cglib.



**什么是SpringIoc容器**



**可以有几种方式完成依赖注入**



**Spring AOP 和 AspectJ AOP 有什么区别?**



**SpringIOC 注入的几种方式**



**SpringMVC工作流程**



**Spring 中 beanFactory 和 ApplicationContext 的联系和区别**



**Spring bean 的生命周期**



**Spring 中的 bean 的作用域有哪些**



**Spring 中的单例 bean 的线程安全问题了解吗**



**@Component 和 @Bean 的区别是什么**





**Spring 框架中都用到了哪些计模式**

工厂模式

代理模式

单例模式

观察者模式

适配器模式



**Spring 中的事件处理？**



**Spring 支持的几种 bean 的作用域**



**什么是 spring 自动装配**



**Spring 的重要注解**



**@Component, @Controller, @Repository, @Service 有何区别**



**spring 事务**



**Spring 的事务传播行为**



**Spring中事务的隔离级别**



# SpringBoot



**过滤程序包**



排除不需要的自动配置



**启动SpringBoot时运行一些代码**



**日志框架**



**定时任务**



**spring-boot-starter-parent**





**多数据源配置**



**多数据源的事务**





**什么是SpringBoot**



**自动配置原理**



## 事务



## Cache





**什么是SpringBoot**

Springboot是一个微服务框架，简化创建Spring的配置，springboot提供了许多自动配置



**Spring，SpringMVC，SpringBoot的关系**

spring核心就是控制反转(IOC),和面向切面(AOP)

Spring MVC属于SpringFrameWork的后续产品，已经融合在Spring Web Flow里面。SpringMVC是一种web层mvc框架，用于替代servlet



**SpringBoot的核心注解，主要有哪几个注解组成**

@SpringBootApplication，主要由@SpringBootConfiguration，@EnableAutoConfiguration，@ComponentScan三个注解组成

@SpringBootConfiguration：等同于@Configuration,说明它是一个配置类，可以用来注入bean

@ComponentScan：自动扫描本包和子包路径下的 @Component(以及@Service等) 注解进行注册 bean 实例到 context 中

@EnableAutoConfiguration：开启自动配置，通过@Import，根据有什么依赖判断开启某个功能，注入到Spring容器



**什么是JavaConfig**

用Java来配置spring容器，减少/消除xml配置。



**SpringBoot常用的starter有哪些**

web，test，data-jpa，data-xxx

也是一个pom文件，定义了相关的依赖



**SpringBootStarter的工作原理**



**spring-boot-starter-parent的作用**

也是一个pom文件

定义子标签的依赖版本号，所以我们不需要在写版本号

可以自定义，在dependencyManagement定义版本号，子标签就不用定义了

定义编码为utf-8

定义编译期版本为utf-8



**SpringBoot配置加载顺序**

properties, yml,系统环境变量，命令行参数

类路径下的，类路径的config下，jar包同级的，jar包同级目录config下的



**SpringBoot读取配置的方式**

@Value用来获得单个值

@ConfigurationProperties，有时候需要读取很多值，可以分组，根据前缀取映射到字段，前缀后的和属性名一致时自动映射

@PropertySource，配置信息不一定都放在application中，使用PropertySource可以指定要读取那个配置文件，可以与@Value和@ConfigrationProperties组合使用(yml暂不支持读取自定义的配置文件)



**如何将配置文件中的属性赋值给静态变量**



**如何处理控制器层的异常**

@ControllerAdvice可以与`@ExceptionHandler` 等注解注解配套使用

@ControllerAdvice可以进行全局的异常处理；

创建一个类，类上添加注解@ControllerAdvice，然后在该类中写处理异常的方法，在方法上添加@ExceptionHandler(XXX.class)指定异常的类型



**SpringBoot热部署**

生产环境中是禁用的



**SpringBoot监视器**

可以访问生产环境中程序的状态



actuator



**SpringBoot监听器流程**



**SpringBatch**



**SpringBoot指定运行环境/配置文件**



**运行SpringBoot有集中方式**



**什么是嵌入式服务器，为什么要使用嵌入式服务器**



**SpringBoot自动配置原理**

通过判断类路径下有没有相应配置的jar包来确定是否加载和配置这个功能，读取jar包下的配置文件，将需要的bean注入到spring容器中



**SpringBootSecurity**



**SpringBoot集成MQ**



**Swagger**



**如何保护SpringBoot程序**

使用https

使用snyk检查依赖关系

启用CSRF







**什么是CSRF攻击**

欺骗用户去访问一个认证过(身份未过期)的网站中的一些接口，即盗用用户的身份



**什么是跨域**

浏览器从一个域名的网页去请求另一个域名的资源时，域名、端口、协议任一不同，都是跨域

浏览器的同源策略造成的

域名，协议，端口



**后端如何解决跨域**

1. CorsFilter
2. WebMvcConfigurerAdapter



**SpringBoot如何支持跨域请求**



**微服务同时调多个接口，怎么使用事务**

分布式事务，消息的补偿机制



**怎么设计无状态服务**



**Spring Cache常用的缓存注解**

@CacheAble

@CachePut

@CacheEvit



**排除程序包**

basepackage

@SpringBootApplication(exclude=Xxx.class)，注意是全限定类名



**如何配置多数据源**



**多数据源事务如何管理**



**readonly事务管理**



**如何禁用一个自动装配类**



**如何在SpringBoot启动前运行一段指定的代码**

通过ApplicationRunner或者CommandRunner接口



**SpringBoot支持哪些日志框架，默认的日志框架是哪个**

默认使用logback作为日志框架



**如何配置log4j**



**异步方法的调用**



**SpringBootMavenPlugin的作用**

插件？

run 运行

repackage 打包

start，stop 管理应用程序的声明周期





**FreeMarker**



**什么是webSocket**



**SpringBoot优化**



**SpringBoot如何实现共享session**

通常一个项目被分成了多个微服务，各自部署在不同的服务器上，在物理空间上隔离开了，但是我们经常需要在多个微服务间共享session。常见的方案是spring session+redis来实现。

将微服务的session同一保存在redis上，各个微服务读写session都取操作redis。

spring session基于spring中的代理过滤器实现，对于开发人员来说是透明的



**SpringBoot打的jar包和普通的jar包和war包有什么区别**

war是一个可以直接运行的web模块，将war文件包放置它的\webapps\目录下，启动Tomcat,这个包可以自动进行解压，也就是你的web目录，相当于发布了

jar只包含class文件main_class之后是可以是可以用java命令运行的

spring打成的jar包不可以作为普通jar包引入其他项目，无法直接引用。



**如何实现定时任务**

1. 基于注解的，单线程的，多个定时任务，任务的执行时机受上一个任务执行时间的影响

添加@EnableScheduling开启定时任务

方法上添加@Scheduled(cron = "0/5 * * * * ?")指定执行时间

cron中的,-/*?

2. 基于SchedulingConfigurer接口，从数据库中读取指定时间来动态执行定时任务，创建runnable和trigger



# SpringBootCloud



restful与rpc的对比



spring，springboot，springcloud的关系



**bootstrap.yml与application.yml的区别**

bootstrap的使用场景



？

Kafka