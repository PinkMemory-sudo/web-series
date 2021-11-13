

# 使用



## WebSecurityConfigurerAdapter



## 配置密码编码器



推荐使用DelegatingPasswordEncoder，支持动态的多种密码加密方式，内部其实是一个Map集合，根据传递的Key（Key为加密方式）获取Map集合的Value，而Value则是具体的`PasswordEncoder`实现类。

`DelegatingPasswordEncoder`是默认的`PasswordEncoder`加密方式，所以我们可以为不同的用户配置所使用不同的密码加密方式，只需要密码格式按照：`{away}encodePassword`来进行持久化即可。

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
```



## 配置HttpSecurity实现对接口资源的保护



```java
@Override
protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests()
    .antMatchers("/user/login", "/common_api/**").permitAll()
    .antMatchers("/admin_api/**").hasAuthority("admin")
    .anyRequest().authenticated()
    .and()
    .formLogin()
    .and().csrf().disable();
}
```



## 配置AuthenticationManager

自定义一些认证细节

认证：将收到的用户信息封装成Authentication，然后与已经存储的用户信息进行比对

```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  // 可以直接将用户信息写死在内存，也可以告诉它怎么找
    auth.userDetailsService(userService);
}
```

### 获得已经存储的用户信息



#### UserDetailsService接口

需要将怎么找已经存储的用户信息告诉AuthenticationManager，而这个标准就是UserDetailService。

UserDetailService，函数式接口，通过name获得UserDetail。



#### UserDetail接口

我们存储的用户信息不一定能合适认证，所以我们可以先讲存储的用户信息查找出来，转换成UserDetail

UserService封装了认证所需要的一些信息，常见的有：用户名，密码，权限等。

SpringSecurity提供了它的实现：User



#### User

User构造函数需要三个参数：用户名，密码和权限

密码是PasswordEncoder加密后的，存储钱未加密的要先加密

权限是一个GrantedAuthority集合

我们存储的用户信息可能并不能支持UserDetail，可以将我们存储的信息转换成User



#### GrantedAuthority

函数式接口，获得权限的String

SpringSecurity提供了三个实现：

* SimpleGrantedAuthority，最简单的实现，构造方法只需要传入一个String
* SwitchUserGrantedAuthority，包含String和Authentication
* JaasGrantedAuthority， String和Principal



#### ？

Authentication

Principal















