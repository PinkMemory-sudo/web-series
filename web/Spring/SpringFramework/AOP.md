面相切面编程，是面向对象编程的一种弥补。

面向对象编程，公用的东西可以有父类实现，子类集成，这样纵向的扩展。而面向切面编程，是横向的抽取一些横切关注点，(比如com.pk.controller中的所有控制器都需要处理BadRequestException，我们可以将这段代码抽取出来，封装成一个方法。或者说com.pk.controller包下，没有进行异常处理，我们可以写个处理方法，增强进去，这样修改不影响之前的代码。

保证不修改源代码的情况下，为系统中的某些方法添加非业务功能。



AOP，对方法进行增强



SpringBoot中使用AOP：

1. 创建切面：切面有公共方法组成，所以将这些方法封装成一个类，构成一个切面。`使用@Aspect 来声明该类是切面，并通过@Component注入到容器中`
2. 设置一个公用的切入点:空方法上添加`@Pointcut("execution(public * com.pk.controller.*.*(..))")`这样做的好处是其他方法可以直接通过



Pointcut包含两部分：

* Pointcut表示式，即@Pointcut注解部分
* Pointcut签名，即方法名，使用相同的Pointcut时就可以用签名来代替

@Pointcut

execution来指定匹配规则(切入点)

修饰符  返回值类型 包名.方法名.(参数)异常类型匹配

eg

```java
// 匹配UserService里的所有方法
public * com. savage.pk.service.UserService.*(..)
// 匹配service里的所有方法
public * com. savage.pk.service..*.*(..)
```



执行时机

| 注解    | 时机                   |
| ------- | ---------------------- |
| @Before | 进入方法后，执行方法前 |
| @After  |                        |
|         |                        |



参数

JoinPoint

提供切入点可用状态和静态信息的反射访问。

| 方法             | 描述                       |
| ---------------- | -------------------------- |
| toShortString    | 切入点(被增强的方法)的描述 |
| toLongString     | 切入点的详细描述           |
| Object getThis() |                            |



术语：



原理：





把重复的非业务代码提取出来，