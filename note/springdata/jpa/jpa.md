**什么是JPA**

JDBC定义了数据访问的接口，各个厂商提供驱动。

Java Persistence API；Java持久化接口，Java5.0提供的ORM规范，以统一的方式访问持久层，实现对不同ORM框架的使用。

Hibernate是JPA的一个实现。



三大内容

ORM，通过XML/注解来映射对象和数据表的关系

JPA的API，通过对象实现JDBC

JPQL

EntityManager





外键和一对多的关系





实体类和表的映射关系



# JPA规范



JDBC中的接口和对象



## JPA注解

通过注解指定映射关系



实体类和表的映射关系

属性和字段的映射关系



一对多

一的一方称为主表，多的一方称为从表，用过外键来建立联结，外键在从表上



多对多

通过中间表实现



对应实体类中

* 包含关系
* 继承关系



配置一对多映射

1. 声明配置
2. 配置外键





数据类型默认长度





# Hibernate

 



映射：

复合主键映射

集合映射

关系映射





**单向多对一**

即找多的会把对应的一带出来



**单向一对多**



**双向一对多**







[Person(id=1, name=Jerry, phoneList=[Phone(id=1, phone=1231231231, value=1), Phone(id=2, phone=22222222222, value=2), Phone(id=3, phone=33333333333, value=1), Phone(id=4, phone=4444444444, value=2), Phone(id=5, phone=5555555555, value=1)]), Person(id=1, name=Jerry, phoneList=[Phone(id=1, phone=1231231231, value=1), Phone(id=2, phone=22222222222, value=2), Phone(id=3, phone=33333333333, value=1), Phone(id=4, phone=4444444444, value=2), Phone(id=5, phone=5555555555, value=1)]), Person(id=1, name=Jerry, phoneList=[Phone(id=1, phone=1231231231, value=1), Phone(id=2, phone=22222222222, value=2), Phone(id=3, phone=33333333333, value=1), Phone(id=4, phone=4444444444, value=2), Phone(id=5, phone=5555555555, value=1)])]



[Person(id=1, name=Jerry, phoneList=[Phone(id=1, phone=1231231231, value=1), Phone(id=2, phone=22222222222, value=2), Phone(id=3, phone=33333333333, value=1), Phone(id=4, phone=4444444444, value=2), Phone(id=5, phone=5555555555, value=1)]), Person(id=1, name=Jerry, phoneList=[Phone(id=1, phone=1231231231, value=1), Phone(id=2, phone=22222222222, value=2), Phone(id=3, phone=33333333333, value=1), Phone(id=4, phone=4444444444, value=2), Phone(id=5, phone=5555555555, value=1)]), Person(id=1, name=Jerry, phoneList=[Phone(id=1, phone=1231231231, value=1), Phone(id=2, phone=22222222222, value=2), Phone(id=3, phone=33333333333, value=1), Phone(id=4, phone=4444444444, value=2), Phone(id=5, phone=5555555555, value=1)])]

