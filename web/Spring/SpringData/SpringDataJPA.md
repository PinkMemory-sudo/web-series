使用





# SaveEntity

底层使用的是EntityManager。If the entity has not yet been persisted, Spring Data JPA saves the entity with a call to the `entityManager.persist(…)` method. Otherwise, it calls the `entityManager.merge(…)` method.

EntityManager是JPA中用于增删改查的接口，它的作用相当于一座桥梁，连接内存中的java对象和数据库的数据存储。

Entity生命周期中的Managed，Detached，Removed三种状态变化过程，





## 查询

四中途径

1. 集成。。。
2. 命名方法
3. 实体类上@NamedQuery
4. @Query



***排序***

 Spring Data JPA does not currently support dynamic sorting for native queries, because it would have to manipulate the actual query declared, which it cannot do reliably for native SQL. You can, however, use native queries for pagination by specifying the count query yourself

jpa对于native的分页排序支持不怎么好,但支持分页查询

```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1",
    countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
    nativeQuery = true)
  Page<User> findByLastname(String lastname, Pageable pageable);
}
```

添加Sort参数或者PageRequest都可以进行排序，本质都是传入一个Order

```java
Sort sort = Sort.by(Sort.Order.asc("age"));
PageRequest pageRequest = PageRequest.of(0, 1, sort);
```

和@Query一起使用时，它会转换成SQL拼接到Query中





传参



使用SqEL































是否会自动创建数据库和表？

不会,表和数据库都不会



实体类配置

id配置

字段配置，字段名和属性名不一致如何配置



接口中字段和属性不一致



# JPA

JPA是Java Persistence API的简称。Java持久化接口

提供XML和注解两种方式进行ORM

通过面向对象而非面向数据库的查询语言查询数据，避免程序的SQL语句紧密耦合



映射注解

| 注解        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| @Entity     | 声明该类是标准的JPA实体类                                    |
| @Table      | 指定实体类关联的表，默认是类名                               |
| @Column     | 指定属性对应的表字段，名字一致时可以不写                     |
| @Id         | 此属性是数据表的主键                                         |
| @JoinTable  | 关联查询时，指定多对多的关系                                 |
| @JoinColumn | 关联查询时，指定一对一，一对多的关系。声明表关联的外键作为连接表的条件，必须配合关联表注解使用。 |
| @OneToOne   | 关联表注解                                                   |
| @OneToMany  | 关联表注解                                                   |
| @ManyToOne  | 关联表注解                                                   |
| @ManyToMany | 关联表注解                                                   |
|             |                                                              |
|             |                                                              |
|             |                                                              |



**主键生成策略**

| 策略     | 说明                 |
| -------- | -------------------- |
| TABLE    | 表序列生成           |
| SEQUENCE | 序列生成             |
| IDENTITY | id自增长             |
| AUTO     | 有框架决定，基本不用 |



**JPA EntityManager**

实体管理类，用于操作数据库表









关系映射

设置联合主键









