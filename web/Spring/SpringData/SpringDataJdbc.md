* 查询
* 存储
* 集成MyBatis
* ApplicationListener
* EntityCallback
* 事务





In order to achieve this it does NOT offer caching, lazy loading, write behind or many other features of JPA

- CRUD operations for simple aggregates with customizable `NamingStrategy`.
- Support for `@Query` annotations.
- Support for MyBatis queries.
- Events.
- JavaConfig based repository configuration by introducing `@EnableJdbcRepositories`.



Java世界中关系数据库的主要持久API当然是JPA，为什么还用SpringDataJdbc.

JPA做了很多事情来帮助开发人员。除此之外，它还跟踪实体的更改。它为你做懒加载。它允许您将大量的对象构造映射到同样广泛的数据库设计。

But it often gets really confusing as to why JPA does a certain thing. Also, things that are really simple conceptually get rather difficult with JPA.

所以Spring Data JDBC 优势是简单



## Domain Driven Design and Relational Databases

？



# 使用

1. 添加依赖

   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-jdbc</artifactId>
   </dependency>
   ```

2. 配置数据源,可以使用基于注解/配置文件

3. 创建实体类

4. 创建接口



实体类

实体类中属性的类型

1. Java的基本数据类型
2. String，数组，集合，枚举等
3. Anything your database driver accepts
4. References to other entities. ***Embedded entities***
5. ***id必须用@Id修饰***，Spring Data JDBC uses the ID to identify entities。



实体类状态感知

探测实体类是不是新的

* 默认通过Id属性，没有id属性的实体类会被当成新的实体类
* 实体实现Persistable接口，调用isNew来检查实体类是不是新的
* 实现EntityInformation接口



数据库设为自动增长时，实体类在保存后会被设置id，就不再是新实体类了，而当数据库没有把Id设置成自动增长时，我们可以使用`BeforeSave` listener。保证存储过的实体类不再被当成是新的。



支持乐观锁 @Version



# Query

* 创建接口继承CrudRepository/PagingAndSortingRepository来获得基本的常见操作

* 接口中可通过命名策略自定义查询
* 接口中通过@Query手动指定SQL语句



方法命名的关键字

1. 比较大小

   GreaterThan，GreaterThanEqual，LessThan，LessThanEqual

2. 范围

   Between，NotBetween，In，NotIn

3. 比较时间

   After，Before，

4. 模糊查询

   Like，StartingWith，EndingWith，NotLike，Containing，NotContaining

5. 值判断

   NotNull，Null，True，False

6. Distinct



## 与Mybatis集成





## 监听器

进行CRUD操作时触发的事件

before/after save、delete、Convert

Spring Data JDBC的监听器依赖于ApplicationEventMulticaster

```java
@Bean
public ApplicationListener<BeforeSaveEvent<Object>> loggingSaves() {

	return event -> {

		Object entity = event.getEntity();
		LOG.info("{} is getting saved.", entity);
	};
}
```

也可以指定Entity

通过实现AbstractRelationalEventListener

```java
public class PersonLoadListener extends AbstractRelationalEventListener<Person> {

	@Override
	protected void onAfterLoad(AfterLoadEvent<Person> personLoad) {
		LOG.info(personLoad.getEntity());
	}
}
```





## EntityCallback



回调函数用于在调用某些方法之前和之后修改实体

与监听器比，提供了同步和反应式api的集成点，以保证在处理链中定义良好的检查点按顺序执行，从而返回可能修改的实体或反应式包装类型

 ***The Entity Callback API has been introduced with Spring Data Commons 2.2***



## 事务

CRUD methods on repository instances are transactional by default

For reading operations, the transaction configuration `readOnly` flag is set to `true`

All others are configured with a plain `@Transactional`

可以在方法上declared，自定义一些属性来改变默认行为

**@Transactional**可以加在方法上，也可以加到泪殇



















































