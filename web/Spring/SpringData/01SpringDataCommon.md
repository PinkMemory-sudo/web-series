核心概念



核心接口

Repository

特定持久化技术接口

JpaRepository

MongoRepository

持久化技术无关的接口

CrudRepository

PagingAndSortingRepository



使用：

1. 声明接口
2. 声明方法
3. 代理
4. 注入使用



使用多个SpringData模块

1. 如果使用特定的repository，就使用相应的SpringData模块

2. 如果实体类使用了特定的注解，就是用相应的SpringData模块
3. 配置中说明

```
@EnableJpaRepositories(basePackages = "com.acme.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.acme.repositories.mongo")
class Configuration { … }
```



?

```
List<Person> findByAddressZipCode(ZipCode zipCode);
```



```
List<Person> findByAddress_ZipCode(ZipCode zipCode);
```



**创建查询**

limit

```
ueryFirst10ByLastname
```





**特殊参数**

Pageable

Page消耗大，可以使用Slice

Sort



**查询的返回**

还支持返回 Spring Data 的 Streamable ，以及 Vavr 提供的集合类型



**Streamable**

可以使用 Streamable 作为 Iterable 或任何集合类型的替代。它提供了方便的方法来访问非并行流(在 Iterable 中缺失) ，并且能够直接... . filter (...)和... . map (...)遍历元素，并将 Streamable 连接到其他元素:

```
Streamable<Person> result = repository.findByFirstnameContaining("av")
  .and(repository.findByLastnameContaining("ea"));
```



**异步查询**



**Query by Example** 

























SpringDataJpa是它的一个实现

所有的模块都基于`Spring Data Commoms`模块进行扩展，定义了一系列的操作标准及接口。



Repository接口

标记行接口，指定实体类型和id类型

都是基于存储仓库`Repository`对实体类对象进行`CRUD`操作的。
其中，最核心的是`Repository`接口



指定SpringData模块

每个SpringData子模块对应一种存储方式，使用单个SpringData子模块不会出现问题，使用多个子模块时，要指定使用的是哪个项目。

指定模块的方法有两种：接口和注解



扩展查询方法

扩展的查询方法通常是findxxbyxxx，通过by来拆分查询目标和查询条件，用and/or进行连接



排序

通过传入Sort进行排序查询



分页

通过传入Pageable进行分页查询



limit

通过查询方法first/top加数字实现



返回结果支持Stream



Repository fragments

提供的通用的CRUD方法不一定能满足我们的所有需求，是要自己手动实现查询方法的，这种做法就是Repository fragments。

以数据库为中心变成以实体类为中心





@Eneity表示该类是实体类，将来会在数据库中生成一张表

@Table用来指定数据库中的表名

@Id表明字段时Id

@GeneratedValue指定主键的生成策略，默认为GenerationType.AUTO

@Basic，基本数据类型上，两个属性fetch指定是否懒加载(默认否)，optional表示是否可空(默认是)

@Column指定列名，是否唯一，是否可空，进行insert时是否可插入，进行更新操作时，是否可更新，指定字符串长度，precision精度等级

@Transient，加到数据库中没有的字段上，不往数据库中存

@Temporal，加在日期字段上，指明类型

@Enumerated，映射枚举字段

@Embedded，一个实体类不需要存入数据库，但其他实体类需要



























