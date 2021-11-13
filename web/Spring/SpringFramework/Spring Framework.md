SpringApplication加载配置文件的位置



核心：

​		IoC Container, Events, Resources, i18n, Validation, Data Binding, Type Conversion, SpEL, AOP

Spring Framework最核心部分：IOC，紧接着是AOP，解决了Java企业编程中80%的AOP需求。



Spring与AspectJ的集成

​		AspectJ，就特性而言，目前是最丰富的，当然也是Java企业领域中最成熟的AOP实现)		



一些名词(类名)

* ApplicationContext









# 核心



## IoC Container

SpringFramework最重要的概念，IoC也称DI(依赖注入)

`org.springframework.beans`和`org.springframework.context`包是Spring框架的IoC容器的基础。

The BeanFactory interface provides an advanced configuration mechanism capable of managing any type of object. ApplicationContext is a sub-interface of BeanFactory。

ApplicationContext添加了

- 与Spring的AOP功能轻松集成
- 消息资源处理（用于国际化）

简而言之，`BeanFactory`提供了配置框架和基本功能，并`ApplicationContext`增加了更多针对企业的功能

`ApplicationContext`接口就了代表Spring IoC容器，用来管理bean，因此在`ApplicationContext`创建和初始化后Full configured System ready for use。



Spring 3.0开始，基于Java的配置成为核心。



**实例化容器**

提供给`ApplicationContext`构造函数的一个或多个位置路径是资源字符串，可让容器从各种外部资源。

```java
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);

// use configured instance
List<String> userList = service.getUsernameList();
```



可以将已存在的对象注册到ApplicationContext



Bean scopes,Spring支持6中范围

| 范围        | 描述                       |
| ----------- | -------------------------- |
| 单例        | Spring默认的               |
| 原型        | 每次创建新的               |
| request     | 每次http请求创建一个       |
| session     | 一次会话                   |
| application | ServletContext整个生命周期 |
| websocket   | WebSocket整个声明周期      |







## Events

## Resources



Spring用Resource来描述资源，内置的资源实现

* UrlResource

* ClassPathResource

* FileSystemResource

* ServletContextResource； Web应用程序根目录中的相对路径

* InputStreamResource

* ByteArrayResource



## ResourceLoader

来加载资源(获得Resource)，函数式接口`Resource getResource(String location);`

**All application contexts implement the `ResourceLoader` interface. Therefore, all application contexts may be used to obtain `Resource` instances.**



使用

直接使用getResource(路径)获得的Resource类型取决于：

ClassPathXmlApplicationContext返回的是ClassPathResource

FileSystemXmlApplicationContext返回的是FileSystemResource

WebApplicationContext返回的是ServletContextResource

我们可以在路径前加前缀filr，classpath或者http



## ResourceLoaderAware

用来获得ResourceLoader

bean实现ResourceLoaderAware，它里面的setResourceLoader会由spring容器调用，将自己作为参数穿进去，这样就获得了ResourceLoader。

```java

public class TestBean implements ResourceLoaderAware
{
    private ResourceLoader rd;
    // 实现ResourceLoaderAware接口必须实现的方法
    // 如果把该Bean部署在Spring容器中，该方法将会由Spring容器负责调用
    // Spring容器调用该方法时，Spring会将自身作为参数传给该方法
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.rd = resourceLoader;
}
```





## i18n

## Validation、Data Binding、Type Conversion

Validator,可在应用程序每一层使用。



## 

## SpEL



Spring表达式语言，支持在运行时查询，操作对象(JSON).



文字表达式

关系运算

方法调用

可以当成字符串，调用字符串的方法甚至加号。

可以当成对象(map)，通过.调用属性	通过[]调用数组，列表或map







## AOP



# 数据访问





# Spring IOC



## Spring IoC Container and Beans

​		

​		IOC也称DI，就是对象定义他们的依赖通过1.构造方法的参数，参数再传进a factory method，或者2. 通过已经被构建或者通过a factory method返回的实例set的**一个过程**。然后，容器创建bean时注入这些依赖。这个过程将对象的创建交给了容器，

​		他们的依赖，也就是说，他们使用的其他对象，

​		`org.springframework.beans`，`org.springframework.context`是IOC容器的基础包，BeanFactory接口提供了一种高级配置机制，能够管理任何类型的对象。

​		ApplicationContext is a sub-interface of BeanFactory，It adds:

- Easier integration with Spring’s AOP features
- Message resource handling (for use in internationalization)
- Event publication
- Application-layer specific contexts such as the `WebApplicationContext` for use in web applications.

​	总之，BeanFactory提供了框架的配置和基本功能，ApplicationContext在BeanFactory的基础上添加了许多企业级功能，ApplicationContext完全支持BeanFactory。

​	ApplicationContext接口用来表示Spring IoC容器，并负责实例化、配置和组装bean，容器从XML，Java注解或Java代码中

​	Spring提供了许多ApplicationContext接口的实现：

[`ClassPathXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/ClassPathXmlApplicationContext.html) ，[`FileSystemXmlApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.0/javadoc-api/org/springframework/context/support/FileSystemXmlApplicationContext.html).

![image-20201029130029502](/Users/chenguanlin/Documents/note/Spring IOC容器.png)

<center>Spring IOC 容器</center>



## Configuration Metadata

​		配置元信息，告诉容器怎么去初始化，配置，装配对象。

​		可以使用传统的XML进行配置，例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="..." class="...">  
        <!-- collaborators and configuration for this bean go here -->
    </bean>

  	<!--懒加载，但如果其他bean需要，就不会懒加载了-->
    <bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>

  	<!--depends-on,使得先初始化其他一个或多个bean，逗号隔开-->
  	<bean id="beanOne" class="ExampleBean" depends-on="manager"/>
		<bean id="manager" class="ManagerBean" />
    <!-- more bean definitions go here -->

</beans>
```

```xml
<!--所有类都懒加载-->
<beans default-lazy-init="true">
    <!-- no beans will be pre-instantiated... -->
</beans>
```



也可以使用基于Java注解和Java代码的配置。

[Annotation-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-annotation-config)

[Java-based configuration](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-java)

metadata

| Property                 | Explained in…                                                |
| :----------------------- | :----------------------------------------------------------- |
| Class                    | [Instantiating Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-class) |
| Name                     | [Naming Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-beanname) |
| Scope                    | [Bean Scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes) |
| Constructor arguments    | [Dependency Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators) |
| Properties               | [Dependency Injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-collaborators) |
| Autowiring mode          | [Autowiring Collaborators](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-autowire) |
| Lazy initialization mode | [Lazy-initialized Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lazy-init) |
| Initialization method    | [Initialization Callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-initializingbean) |
| Destruction method       | [Destruction Callbacks](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-lifecycle-disposablebean) |

### Instantiating a Container

​		可以给ApplicationContext的构造方法传入多个xml文件地址，加载配置文件

```java
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```

### Using the Container

使用ApplicationContext的`T getBean(String name, Class<T> requiredType)`方法来获得对象



# Resources



# Validation

Hibernate Validator 是对JSR-303的实现，

JSR-303的注解



Hibernate Validator扩展的注解

用法：

1. 实体类的字段上加注解
2. 控制器的参数前加@Validated或者Validated
3. 加一个参数BindingResult来获得校验结果



```java
@Data
public class ValidParam {
    @NotEmpty(message = "name can't be null")
    private String name;

    @Min(value = 18,message = "age must gt 18")
    private int age;
}
```

```java
@GetMapping("/valid")
// @Valid表示激活自动校验，BindingResult要紧挨被校验参数，来存储校验结构
// 多个校验参数，就需要多个BindingResult
public String valid(@Valid ValidParam param, BindingResult result){
    if (result.hasErrors()){
        return result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()).toString();
    }
    return "hello"+param.getName();
}
```

结果：`[age must gt 18, name can't be null]`



分组校验

我们可以创建一个空的接口作为一个组，在注解中设置groups = {AddGroup.class}属性，然后在Controller中进行参数校验时，@Validated中指定使用的分组。

注意：指定分组后，没有分组信息的字段就不会校验了。

如果对同一个类，在不同的使用场景下有不同的校验规则，就可以使用分组校验

要进行分组校验，就必须使用`@Validated`



常用注解

| 注解  | 描述                                 |
| ----- | ------------------------------------ |
| @Null | 对象为空，注意不要用在基本数据类型上 |
|       |                                      |
|       |                                      |



























#  Data Binding, and Type Conversion





Spring Bean加载顺序

单一bean



多个bean







