# Spring Data

将核心 Spring 概念应用于使用许多关系和非关系数据存储的解决方案的开发.





**ObjectMapping**



核心概念

Repository

Cruddrepository

PagingAndSortingRepository









使用步骤



多个SpringData模块；@Entity @Document









































## SpringData  JDBC

SpringData 之一，增强基于JDBC的数据访问层



***The Aggregate Root***

ORM：对象关系映射，实现通过操作实体类对象来操作数据库

学习前提：

Spring framework：Ioc，type conversion system，expression language，JMX integration，DAO exception hierarchy



**SpringData JDBC支持的数据库**

- DB2
- H2
- HSQLDB
- MariaDB
- Microsoft SQL Server
- MySQL
- Postgres



**Features**

- CRUD operations for simple aggregates with customizable `NamingStrategy`.
- Support for `@Query` annotations.
- Support for MyBatis queries.
- Events.
- JavaConfig based repository configuration by introducing `@EnableJdbcRepositories`.



**2.0新特性**

- Optimistic Locking support.
- Support for `PagingAndSortingRepository`.
- Query Derivation
- Full Support for H2.
- All SQL identifiers know get quoted by default.
- Missing columns no longer cause exceptions.



**1.1新特性**

- `@Embedded` entities support.
- Store `byte[]` as `BINARY`.
- Dedicated `insert` method in the `JdbcAggregateTemplate`.
- Read only property support.



**1.0新特性**

- Basic support for `CrudRepository`.
- `@Query` support.
- MyBatis support.
- Id generation.
- Event support.
- Auditing.
- `CustomConversions`.



## SpringData jdbc的使用

1. 配置数据源
2. 写接口，接口直接归Spring容器管理，实体类等

### 核心概念

查询关键字

#### 核心接口

SpringData JDBC的核心接口是***Repository***，泛型是实体类和ID，只声明接口就能访问数据库

Repository的子类***CrudRepository*** 中有许多标准的查询，统计删除等crud方法，其他特定的持久化技术，如`JpaRepository` `MongoRepository`等，都继承了***CrudRepository***

```java
public interface CrudRepository<T, ID> extends Repository<T, ID> {

  <S extends T> S save(S entity);      

  Optional<T> findById(ID primaryKey); 

  Iterable<T> findAll();               

  long count();                        

  void delete(T entity);               

  boolean existsById(ID primaryKey);   

  // … more functionality omitted.
}

```



CrudRespository的子类***PagingAndSortingRepository***来简化分页和排序

```java
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

  Iterable<T> findAll(Sort sort);

  Page<T> findAll(Pageable pageable);
}
```

eg:每页20条，访问第二页

```java
PagingAndSortingRepository<User, Long> repository = // … get access to a bean
  // 通过PageRequest的静态方法of来创建Pageable，精简了创建对象的代码
Page<User> users = repository.findAll(PageRequest.of(1, 20));
```



```java
Page<User> findByLastname(String lastname, Pageable pageable);

Slice<User> findByLastname(String lastname, Pageable pageable);

List<User> findByLastname(String lastname, Sort sort);

List<User> findByLastname(String lastname, Pageable pageable);
```

Page保存了页面信息和总页数等，Slice只知道下一页能不能用，花销要小

**Sort包含Pageable**

如果仅仅需要排序，不进行分页，可以使用org.springframework.data.domain.Sort



排序EX

```java
Sort sort = Sort.by("firstname").ascending()
  .and(Sort.by("lastname").descending());
```

根据姓升序，名降序进行排序

more type-safe

```java
TypedSort<Person> person = Sort.sort(Person.class);

Sort sort = person.by(Person::getFirstname).ascending()
  .and(person.by(Person::getLastname).descending());
```

TypedSort.by(…)通常使用CGlib进行运行时动态代理



#### 派生的crud

除了继承的方法外，我们也可以通过定义抽象方法完成派生(只需要根据规则写抽象方法)

findMmmByXxx 此外还有delete,remove,read,query,count等

Mxx可以是All/实体类名/实体类名s/First/Distinct/Top

Xxx是实体类中的属性名



### 通过SpringData JDBC对数据库进行CRUD操作

通过SpringData JDBC实现标准Crud方法需要四步(先导入依赖和配置数据源，实体类名对应表名)

1. 定义接口，继承于Repository(或它的子类)，指定domain class 和 ID type，如果想暴露CRUD方法，可以继承CrudRepository等，也可以自己定义(再说：Fine-tuning Repository Definition)

```java
interface PersonRepository extends Repository<T, ID> { … }
```



创建一个common base interface

```java
@NoRepositoryBean
interface MyBaseRepository<T, ID> extends Repository<T, ID> {

  Optional<T> findById(ID id);

  <S extends T> S save(S entity);
}

interface UserRepository extends MyBaseRepository<User, Long> {
  User findByEmailAddress(EmailAddress emailAddress);
}
```

@NoRepositoryBean表示Spring Data should not create instances at runtime.

2. 在接口中声明抽象方法()

   find.By...

   get...By...

   Read...By...

   Query...By...

   Count...By...

   Distinct

   and

   or

   Between

   LessThan

   GreaterThan

   Like

   IgnoreCase

   OrderBy

   ```java
   interface PersonRepository extends Repository<Person, Long> {
   
     List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
   
     // Enables the distinct flag for the query
     List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
     List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
   
     // Enabling ignoring case for an individual property
     List<Person> findByLastnameIgnoreCase(String lastname);
     // Enabling ignoring case for all suitable properties
     List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
   
     // Enabling static ORDER BY for a query
     List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
     List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
   }
   ```

   

   如findBy属性名

   findFirst[n]By属性，查找第n个

   返回值可以是collection/set、Iterable等，还可以用SpringData提供的***Streamable***，**它提供了filter，map等方法**。

3. 将接口注入(注解或JavaConfig等)，会生成代理对象

4. 自动注入，调用



***Streamable***

自定义Streamable Wrapper Types

```java
// 实体类
class Product { 
  MonetaryAmount getPrice() { … }
}

// 自定义一个Streamable来存储实体类集合
@RequiredArgConstructor(staticName = "of")
class Products implements Streamable<Product> { 

  private Streamable<Product> streamable;

  public MonetaryAmount getTotal() { 
    return streamable.stream() //
      .map(Priced::getPrice)
      .reduce(Money.of(0), MonetaryAmount::add);
  }
}

// 用自定义的实体类集合来接收查询结果
interface ProductRepository implements Repository<Product, Long> {
  Products findAllByDescriptionContaining(String text); 
}
```



### Query

**Limiting Query Results**

查询结果有多个，可以使用first，top，Distinct，后面可以加数字，表示第n个，默认为1



所有查询结果可以使用Optional包裹



limit与sort联合使用来查询最值



##### Vavr？



#### Using Repositories with Multiple Spring Data Modules

有时，一个SPringBoot应用中使用了多个SpringData模块(JDBC,JPA,Mongo,ES),这种情况下必须区分持久化技术，当它发现有多种持久化技术时，SpringData会进入严格配置模式。

严格配置模式根据details on the repository或者domain class来决定使用什么：

1. 如果定义了特定的接口，他就是对应的持久化技术
2. 如果实体类上使用了特定模块的注解，它就是对应的持久化技术
3. 用**packages**指明

例如：

使用特定模块接口

```java
interface MyRepository extends JpaRepository<User, Long> { }

@NoRepositoryBean
interface MyBaseRepository<T, ID> extends JpaRepository<T, ID> { … }

interface UserRepository extends MyBaseRepository<User, Long> { … }
```

上面使用的都是Spring Data JPA module技术



```java
interface AmbiguousRepository extends Repository<User, Long> { … }

@NoRepositoryBean
interface MyBaseRepository<T, ID> extends CrudRepository<T, ID> { … }

interface AmbiguousUserRepository extends MyBaseRepository<User, Long> { … }
```

上面只使用了Repository和CrudRepository，适用于只使用到一个SpringData模块的情况下



```java
interface PersonRepository extends Repository<Person, Long> { … }

@Entity
class Person { … }

interface UserRepository extends Repository<User, Long> { … }

@Document
class User { … }
```

Person使用了Entity注解，所以他是SPringData JPA

@Document是Mongo



一个实体类混合使用多个持久化技术的注解

```java
interface JpaPersonRepository extends Repository<Person, Long> { … }

interface MongoDBPersonRepository extends Repository<Person, Long> { … }

@Entity
@Document
class Person { … }
```

SpingData不能区分，将发生不可预知的行为



用**packages**指明

```java
@EnableJpaRepositories(basePackages = "com.acme.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.acme.repositories.mongo")
class Configuration { … }
```



#### Null Handling of Repository Methods

Spring Data 2.0起，支持多重包裹

* `Java8的Optional`

- `com.google.common.base.Optional`
- `scala.Option`
- `io.vavr.control.Option`

使用多重数据库



返回结果允许使用Stream包裹，注意使用完后要关闭，可以使用try-with-resources，不是所有Spring Data modules 都支持返回结果用Stream包裹

#### 异步查询

?









查询



Docker安装mysql，注意run的时候一定要指定tag，否则就是最新的



|      |            |
| ---- | ---------- |
| @ID  | 表明是主键 |
|      |            |
|      |            |







插入中文报错

Incorrect string value: '\xE7\x94\xB7' for column 'gander' at row 1



## JPA

* JpaRepository

ExampleMatcher 

创建匹配器即如何使用查询条件

* matchingAll
* withIgnorePaths   忽略属性
* withIgnoreNullValues

创建Example实例基于注解的配置



***实体类上要加@Entity注解***

底层默认的是依赖 Hibernate JPA 来实现的



#### `@Query`

使用的事实体类属性，不是数据库中的字段名

Spring Data JPA写的SQL叫`JPQL`，不支持limit函数。是站在实体类的角度上操作的，可以使用like，会转换成JPQL query (removing the `%`)

使用@Param绑定参数

```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
  User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
}
```





在注解内增加参数`nativeQuery`，当加入`nativeQuery`参数时，`@Query`内的SQL是按原生SQL写法来写，limit函数生效，不加入`nativeQuery`参数则是`JPQL`，limit函数不生效

```

```





### 配置

Example：  **Spring Data JPA repositories using JavaConfig**

```java
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
class ApplicationConfig {

  @Bean
  public DataSource dataSource() {
		// 建立了嵌入式HSQL数据库spring-jdbc
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder.setType(EmbeddedDatabaseType.HSQL).build();
  }

  @Bean
  // 创建了EntityManagerFactory，并将Hibernate作为the sample persistence provider
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new 			      			LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.acme.domain");
    factory.setDataSource(dataSource());
    return factory;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }
}

```

创建数据源，通过数据源创建EntityManagerFactory，最后创建EntityManagerFactory， 通过@EnableJpaRepositories激活Spring Data JPA repositories 

#### 启动模块

Spring Data JPA repositories默认情况下是单例的，在启动期间，他们已经相互作用了(通过JPA EntityManager进行验证和元数据分析。因为该过程通常占用的时间长，Spring Framework 支持JPA的EntityManagerFactory在后台进行初始化。为了有效地利用后台初始化，我们需要确保JPArepositories尽可能早地初始化。

从Spring Data JPA 2.1开始，可以使用@EnableJpaRepositories配置一个BootstrapMode

* DEFAULT-Repositories将尽早创建，除非实用@Lazy声明的，但是client bean needs an instance of the repository时@Lazy失效
* LAZY-repository beans懒加载，当第一次与repository交互时才创建Repository instances
* DEFERRED-与LAZY类似，但是ContextRefreshedEvent来触发创建实例，保证应用程序启动前创建

***建议：***

	* 如果不实用异步JPA，选DEFAULT
	* 如果实用异步JPA，选DEFERRED，因为他能确保EntityManagerFactory启动后在启动
	* 如果是测试和本地开发，选LAZY，因为你清楚repositories会被正确引导



### Persisting Entities



#### 存储

如何通过JPA存储Entity

实用CrudRepository.save(…)来保存实体，通过EntityManager来persists or merges实体，实体类不存在就persists，存在就merges

#### 实体类状态检测策略

检测实体类是不是新的，决定是persisits还是merge

1. Version-Property and Id-Property(默认)： JPA首先会检查是否有非基本数据类型的Version-property，如果Version-property为null，则就是new。如果没有Version-property，则会去检查ID，ID为空则是new
2. 实现Persistable：实现了Persistable的实体类，JPA会调用isNew方法
3. 实现EntityInformation：可以自定义EntityInformation创建JpaRepositoryFactory子类，重写getEntityInformation方法，最后必须注册到SPring容器

对于需要手动设置ID的实体类选项一不适用，因为ID常常是non-null。

这种情况下，通常的做法是实用一个公共的基类，该基类有个flag，默认创建一个新实例，然后JPA的的声明周期回调函数翻转flag

**Example**：**A base class for entities with manually assigned identifiers**

```java
@MappedSuperclass
public abstract class AbstractEntity<ID> implements Persistable<ID> {

  // @Transient暂时的，保证该属性不会存入数据库
  @Transient
  private boolean isNew = true; 

  @Override
  public boolean isNew() {
    return isNew; 
  }

  // JPA回调函数，当调用save方法或者实用the persistence provider创建实例后
  @PrePersist 
  @PostLoad
  void markNotNew() {
    this.isNew = false;
  }

  // More code…
}
```



### Query

***查询策略***：JPA支持通过String或者方法名派生来创建Query

***派生谓词：***IsStartingWith，StartingWith，StartsWithIs，EndingWith，EndingWith，EndsWith，IsNotContaining，NotContaining，NotContains，IsContaining，IsContaining，Contains

如果参数实际包含由LIKE通配符识别的字符，则这些字符将被转义，因此它们仅作为文字匹配。所使用的转义字符可以通过设置来配置escapeCharacter所述的@EnableJpaRepositories注释。

***查询的声明：***尽管从方法名派生一个查询很方便，有时方法名解析器不支持我们想要想使用的关键字，或者方法名不必要地变得丑陋，我们可以使用@Query或查看[Using JPA Named Queries](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.named-queries)

Example：

```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.emailAddress = ?1")
  User findByEmailAddress(String emailAddress);
}
```



***查询的创建：***我们声明的方法本质上，Spring Data JPA进行属性检查并遍历嵌套的属性，转换成SQL语句

| Keyword                | Sample                                                       | JPQL snippet                                                 |
| :--------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `And`                  | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                   | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is`, `Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`              | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`             | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`        | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`          | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`     | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`                | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`               | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`, `Null`       | `findByAge(Is)Null`                                          | `… where x.age is null`                                      |
| `IsNotNull`, `NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`                 | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`              | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`         | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1` (parameter bound with appended `%`) |
| `EndingWith`           | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1` (parameter bound with prepended `%`) |
| `Containing`           | `findByFirstnameContaining`                                  | `… where x.firstname like ?1` (parameter bound wrapped in `%`) |
| `OrderBy`              | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`                  | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                   | `findByAgeIn(Collection<Age> ages)`                          | `… where x.age in ?1`                                        |
| `NotIn`                | `findByAgeNotIn(Collection<Age> ages)`                       | `… where x.age not in ?1`                                    |
| `True`                 | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`                | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`           | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`                      |



***本地查询***

？

***排序***

通过PageRequest或者Sort进行排序

JPA拒绝Sort中的属性表达式包含方法调用，如果想包含方法调用，可以不实用Sort，实用JpaSort.unsafe去创建一个Order

```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.lastname like ?1%")
  List<User> findByAndSort(String lastname, Sort sort);

  @Query("select u.id, LENGTH(u.firstname) as fn_len from User u where u.lastname like ?1%")
  List<Object[]> findByAsArrayAndSort(String lastname, Sort sort);
}

repo.findByAndSort("lannister", new Sort("firstname")); 
// 属性中包含方法调用，会抛异常
repo.findByAndSort("stark", new Sort("LENGTH(firstname)"));    
// 包含方法调用，使用JpaSort.unsafe
repo.findByAndSort("targaryen", JpaSort.unsafe("LENGTH(firstname)")); 
repo.findByAsArrayAndSort("bolton", new Sort("fn_len")); 
```



***参数绑定***

JPA默认根据位置进行参数绑定，有可能会出现参数位置不对应的情况，我们可以使用@Param将 @Query中的参数与方法中的参数绑定

```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
  User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
}
```

?

` As of version 4, Spring fully supports Java 8’s parameter name discovery based on the `-parameters` compiler flag. By using this flag in your build as an alternative to debug information, you can omit the `@Param` annotation for named parameters.`



***SpEL Expressions***

从1.4起支持实用@Query手动定义query时使用SpEL模板表达式

Spring Data JPA支持一个名为entityName的变量，它的用法是：

select x from #{#entityName} x

它会把repository中指定的domain type插入到entityName

如果domain type设置了@Entity注解的那么属性，会将设置的名字插入，否则会将实体类的类名插入

SqEl与@Entity的name属性组合，可以将实体类映射成不同的名字

\#{#entityName}另一个使用案例是当你想要写一个通用接口的时候

```java
@MappedSuperclass
public abstract class AbstractMappedType {
  …
  String attribute
}

@Entity
public class ConcreteType extends AbstractMappedType { … }

@NoRepositoryBean
public interface MappedTypeRepository<T extends AbstractMappedType>
  extends Repository<T, Long> {

  @Query("select t from #{#entityName} t where t.attribute = ?1")
  List<T> findAllByAttribute(String attribute);
}

public interface ConcreteRepository
  extends MappedTypeRepository<ConcreteType> { … }
```

这样，继承它的都能用



#### QueryByExample(QBE)

使用QBE，允许动态创建Query，不需要写包含字段名的查询。

根据entity封装查询条件，null会忽略



QBE API包含三部分：

1. Probe：用entity封装的条件
2. ExampleMatcher：带有如何匹配字段的详细信息，可以被多个Example使用
3. Example：由两个部分组成，Probe和ExampleMatchere，没有ExampleMatchere时使用默认的





自定义匹配规则

***ExampleMatcher***

每个方法都会返回一个ExampleMatcher

| 方法                                                  | 描述                                                       |
| ----------------------------------------------------- | ---------------------------------------------------------- |
| matching()                                            | 等于matchingAll                                            |
| matchingAll()                                         | 匹配所有字段                                               |
| matchingAny()                                         | 匹配任意一个                                               |
| withIgnorePaths(String... ignoredPaths)               | 去掉实体类中封装的一些条件。返回的ExampleMatcher不能再改变 |
| withIgnoreCase()                                      |                                                            |
|                                                       |                                                            |
|                                                       |                                                            |
| withStringMatcher(StringMatcher defaultStringMatcher) |                                                            |

QBE非常适合的场景：

1. 使用动态或静态数据来查询存储的数据
2. 频繁重构域对象，而不必担心破坏现有查询
3. 独立于基础数据存储API进行工作



QBE的局限性：

1. 不支持属性嵌套或分组，如`firstname = ?0 or (firstname = ?1 and lastname = ?2)`
2. 字符串仅支持starts/contains/ends/regex匹配和其他类型的精确匹配



使用：创建Example，执行

在使用QBE前，需要一个domain object(?pojo)

**Sample Person object**

```java
public class Person {

  @Id
  private String id;
  private String firstname;
  private String lastname;
  private Address address;

  // … getters and setters omitted
}
```

可以用上面的例子创建Example，***null的字段默认会被忽略***。

创建Example的两种方式：用of工厂方法或者使用Example Matcher创建

***使用实体类对象创建Example***

```java
Person person = new Person(); 
// set the properties to query
person.setFirstname("Dave");                          

Example<Person> example = Example.of(person); 
```

***使用Example Matchers创建Example***

```java
Person person = new Person();                          
person.setFirstname("Dave");                           

ExampleMatcher matcher = ExampleMatcher.matching()     
  .withIgnorePaths("lastname")                         
  .withIncludeNullValues()                             
  .withStringMatcherEnding();                          

Example<Person> example = Example.of(person, matcher);
```



执行Example查询，需要持久化接口继承QueryByExampleExecutor<T>

```java
public interface QueryByExampleExecutor<T> {

  <S extends T> S findOne(Example<S> example);

  <S extends T> Iterable<S> findAll(Example<S> example);

  // … more functionality omitted.
}
```











































SpringData JPA
基于JPA，简化数据库访问



## Features

- Sophisticated support to build repositories based on Spring and JPA
- Support for [Querydsl](http://www.querydsl.com/) predicates and thus type-safe JPA queries
- Transparent auditing of domain class
- Pagination support, dynamic query execution, ability to integrate custom data access code
- Validation of `@Query` annotated queries at bootstrap time
- Support for XML based entity mapping
- JavaConfig based repository configuration by introducing `@EnableJpaRepositories`.



1.11新特性

- mproved compatibility with Hibernate 5.2.
- Support any-match mode for [Query by Example](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#query-by-example).
- Paged query execution optimizations.
- Support for the `exists` projection in repository query derivation.

1.10新特性

- Support for [Projections](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#projections) in repository query methods.
- Support for [Query by Example](https://docs.spring.io/spring-data/jpa/docs/2.3.4.RELEASE/reference/html/#query-by-example).
- The following annotations have been enabled to build on composed annotations: `@EntityGraph`, `@Lock`, `@Modifying`, `@Query`, `@QueryHints`, and `@Procedure`.
- Support for the `Contains` keyword on collection expressions.
- `AttributeConverter` implementations for `ZoneId` of JSR-310 and ThreeTenBP.
- Upgrade to Querydsl 4, Hibernate 5, OpenJPA 2.4, and EclipseLink 2.6.1.



核心概念中的接口和SPringData JDBC一样



使用























​	



