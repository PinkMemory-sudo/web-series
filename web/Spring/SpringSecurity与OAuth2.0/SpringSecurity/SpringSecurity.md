1. 密码编码器
2. userDetail
3. H
4. ttpSecurity





































作用

* 认证
* 授权
* 安全防护



优点在于根据需求非常容易扩展



？oauth2.0

* 
* 密码存储
* PasswordEncoder，密码存储前需要转换，验证时也要用到
* 
* JWT
* Restructured authorization logs
* Restructured Form Login and HTTP Basic authentication logs
* Added Resource Server logs



* AuthorizationServerTokenServices
* LettuceConnectionFactory
* AuthenticationManager
* ResourceServerConfigurerAdapter
* 



。

* 需要提供什么就是注入一个什么对象



强大的，高度定制的认证和访问控制框架。提供认证，授权，常见攻击防御，针对常见漏洞的保护默认是开启的。



特性：

* 全面的，可扩展的认证和授权
* 防止攻击，like session fixation, clickjacking, cross site request forgery, etc
* 集成了Servlet API





TODO 

* 重构了日志
* LDAP, OpenID



### PasswordEncoding

没有绝对安全的密码，因为硬件的发展，每秒能进行数十亿次hash计算，直接枚举出所有密码。

TODO可以配置一个增长因素，随着CPU，内存等的增长而增长。但这样增加了消耗，会降低应用性能。

加盐值，就是在密码中加random bytes，密码和盐值一起hash，生成唯一的hash值，这样，相同的密码，彩虹表中存的是直接hash的值，就与加salt的不一样了。

#### 1. DelegatingPasswordEncoder

Spring Security 5.0 之前，默认的 `PasswordEncoder` 是 `NoOpPasswordEncoder`它以明文的方式存储密码。

为了解决以下问题：

1. 许多应用使用的密码加密方法不方便迁移
2. 存储密码的最佳实践可能会改变
3. Spring Security不能频繁地做破坏性的改变

DelegatingPasswordEncoder通过以下方法来解决以上问题

1. 它确保使用最近的密码存储方案来加密
2. 允许验证现代和传统格式的密码
3. 允许在将来升级编码方式

可以使用PasswordEncoderFactories构造一个DelegatingPasswordEncoder实例

```java
PasswordEncoder passwordEncoder =
    PasswordEncoderFactories.createDelegatingPasswordEncoder();
```

***自定义DelegatingPasswordEncoder***

```java
String idForEncode = "bcrypt";
Map encoders = new HashMap<>();
encoders.put(idForEncode, new BCryptPasswordEncoder());
encoders.put("noop", NoOpPasswordEncoder.getInstance());
encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
encoders.put("scrypt", new SCryptPasswordEncoder());
encoders.put("sha256", new StandardPasswordEncoder());

PasswordEncoder passwordEncoder =
    new DelegatingPasswordEncoder(idForEncode, encoders);
```

***密码的存储格式***

通常格式是`{id}encodedPassword`

id来确定使用哪个PasswordEncoder

encodedPassword表示被指定PasswordEncoder加密后的密码

不用担心id方式会被黑客知道，因为存储的密码不依赖于加密算法，另外，没有前缀黑客也很容易直到加密算法，比如BCrypt开头总是$2a$

存储的密码格式没有{id}时就会报错`java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"`解决办法就是显式的去指定PasswordEncoder(暴露一个PasswordEncoder对象)

***eg：使用不同密码加密器来存储 "password"***

```tex
{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG 
{noop}password 
{pbkdf2}5d923b44a6d129f3ddf3e3c8d29412723dcbde72445e8ef6bf3b508fbf17fa4ed4d6b99ca763d8dc 
{scrypt}$e0801$8bWJaSu2IKSn9Z9kM+TPXfOc/9bdYSrN1oD9qfVThWEwdRTnO7re7Ei+fUZRJ68k9lTyuTeUp4of4g24hHnazw==$OAOec05+bXxvuu/1qZ6NUR+xQYvYv7BeL1QxwRpY5Pc=  
{sha256}97cde38028ad898ebc02e690819fa220e88c62e0699403e94fff291cfffaf8410849f27605abcbc0
```

***密码的匹配***

密码的匹配基于{id},matches(CharSequence, String),不传入id会`IllegalArgumentException`，可以自定义设置：`DelegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(PasswordEncoder)`



#### 2. BCryptPasswordEncoder

使用[bcrypt](https://en.wikipedia.org/wiki/Bcrypt) 算法对密码进行散列，为了使它更抵抗密码破解，bcrypt故意缓慢，可以调整系统每秒验证的密码的数量(通过控制strength)。BCryptPasswordEncoder默认的strength是10。通过设置不同的strength，来测试测试系统每秒能验证几个密码。

比如想每秒只验证一个密码，通过设置不同strength，找到合适的strength，例如strength为16时

```java
// Create an encoder with strength 16
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```



#### 3. Argon2PasswordEncoder

使用[Argon2](https://en.wikipedia.org/wiki/Argon2)算法进行加密，[Password Hashing Competition](https://en.wikipedia.org/wiki/Password_Hashing_Competition)的获胜者。Argon2是一个故意缓慢的算法，**需要大量的内存**，来防止使用硬件来打败加密。

```java
// Create an encoder with all the defaults
Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```



#### 4. Pbkdf2PasswordEncoder

使用[PBKDF2](https://en.wikipedia.org/wiki/PBKDF2) 算法，还是故意缓慢算法，当需要FIPS认证时，该算法是一个很好的选择。

```java
// Create an encoder with all the defaults
Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```



#### 5. SCryptPasswordEncoder

使用[scrypt](https://en.wikipedia.org/wiki/Scrypt)算法，故意缓慢，需要大量内存。

```java
// Create an encoder with all the defaults
SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```



#### 6. Other PasswordEncoders

还有很多其他的PasswordEncoder实现完全是为了向后兼容而存在的。他们不再被认为是安全的，所以不建议使用，只是因为之前的系统还是用才存在



# 常见漏洞的防护

Spring Security提供了针对常见漏洞的保护，默认是开启的。



## CSRF Attack

什么是CSRF Attack？

跨站请求伪造。

假设银行网站提供了一个表单，允许从当前登录的用户转移资金到另一个帐户。

银行发送的表单

```html
<form method="post"
    action="/transfer">
<input type="text"
    name="amount"/>
<input type="text"
    name="routingNumber"/>
<input type="text"
    name="account"/>
<input type="submit"
    value="Transfer"/>
</form>
```

发送的HTTP请求

```tex
POST /transfer HTTP/1.1
Host: bank.example.com
Cookie: JSESSIONID=randomid
Content-Type: application/x-www-form-urlencoded

amount=100.00&routingNumber=1234&account=9876
```

假设你先登录的银行账户，然后没有退出，一个玩游戏的网站给你发了一个页面

```html
<form method="post"
    action="https://bank.example.com/transfer">
<input type="hidden"
    name="amount"
    value="100.00"/>
<input type="hidden"
    name="routingNumber"
    value="evilsRoutingNumber"/>
<input type="hidden"
    name="account"
    value="evilsAccountNumber"/>
<input type="submit"
    value="Win!"/>
</form>
```

只显示了一个按钮，当你点击时，你以为是玩游戏赢了，实际是用你的银行账户转账了，尽管钓鱼网站没有你的信息，但是当点击按钮时会给银行发请求，带上cookie。整个过程还可以使用JavaScript自动完成，甚至不需要点击按钮。

Spring提供了两种机制来防止CSRF攻击

* The [Synchronizer Token Pattern](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#csrf-protection-stp)
* Specifying the [SameSite Attribute](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#csrf-protection-ssa) on your session cookie

防止CSRF攻击的主要和最全面的方法是 [Synchronizer Token Pattern](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)_Prevention_Cheat_Sheet#General_Recommendation:_Synchronizer_Token_Pattern)这个解决方案是确保每个HTTP请求除了会话cookie之外，还必须在HTTP请求中出现一个称为CSRF token的安全随机生成的值。如果值不匹配，则应该拒绝HTTP请求。



## Filter



* Spring Security的默认配置将会创建一个叫做springSecurityFilterChain的bean(一个servlet的filter)，这个bean来负责应用的所有安全；
* 将springSecurityFilterChain注册到每个请求；



Spring Boot不需要进行太多配置，但它做了很多工作，比如

* 与应用程序的任何交互都需要用户进行认证
* 生成了一个默认的登录页面
* 创建了一个名为user的user，密码打印在了控制台，基于表单验证
* 使用BCrypt进行密码的安全存储
* CSRF攻击防御
* [Session Fixation](https://en.wikipedia.org/wiki/Session_fixation)保护
* Security Header integration
* 集成了下面一些Servlet API methods











## Ahentication



# Spring Security的主要组件：



* GrantedAuthority，Authentication中，代表用户权限的接口，返回权限名



* AuthenticationManager，定义Spring Security的Filter怎么去执行认证的PAI，***将认证信息放入上下文***，委托给了一系列ProviderManager
* ProviderManager，实现了AuthenticationManager，每个Provider都有机会认证成功/失败，或者不能处理交给下游处理，每一个能处理时就认证失败，抛出ProviderNotFoundException异常。指示ProviderManager未配置为支持传递给它的Authentication类型。
* AuthenticationProvider，被ProviderManager使用来进行不同类型的认证
* AuthenticationEntryPoint，





![image-20201123101553882](/Users/chenguanlin/Documents/note/0img/SecurityContext.png)

***SecurityContextHolder***

securitycontexts tholder是Spring Security存储身份验证的详细信息的地方。Spring Security并不关心securitycontext是如何填充的。如果它包含一个值，那么它将被用作当前经过身份验证的用户。所以想要表示用户已通过身份验证的最简单方法是直接往securitycontext holder中填值。

```java
// 创建一个控的SecurityContext来避免产生Spring多线程的竞争关系
SecurityContext context = SecurityContextHolder.createEmptyContext(); 
// 创建一个Authentication，Spring Security并不关心在SecurityContext上设置了什么类型的身份验证实现，也可以使用另一个简单的：UsernamePasswordAuthenticationToken
Authentication authentication =
    new TestingAuthenticationToken("username", "password", "ROLE_USER"); 
context.setAuthentication(authentication);
// 最后，我们将SecurityContext传入SecurityContextHolder，将使用这些信息进行认证
SecurityContextHolder.setContext(context); 
```

通过SecurityContextHolder来获得认证信息

```java
SecurityContext context = SecurityContextHolder.getContext();
Authentication authentication = context.getAuthentication();
String username = authentication.getName();
Object principal = authentication.getPrincipal();
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
```



TODO

默认情况下，SecurityContext使用ThreadLocal来存储这些细节，这意味着SecurityContext始终对同一线程中的方法可用，即使SecurityContext没有显式地作为参数传递给这些方法。如果在当前主体的请求被处理后清除线程，那么以这种方式使用ThreadLocal是非常安全的。Spring Security的FilterChainProxy确保SecurityContext总是被清除



***Authentication***

![image-20201215140647845](/Users/chenguanlin/Documents/note/Authentication.png)

两个作用：

1. 存储用户认证的证书传给AuthenticationManager
2. 存储用户的认证信息，表示当前经过身份验证的用户，可以从SecurityContext中获得

Authentication包含了

* Principal： 用户详情，使用用户名/密码进行身份验证时，这通常是UserDetails的实例。
* Credentials—凭证，通常是密码。在许多情况下，这将在用户经过身份验证后被清除，以确保它不会泄漏
* Authorities—，权限集合授予的权限是授予用户的高级权限。一些例子是角色或作用域。



***GrantedAuthority***

可以从Authentication获得GrantedAuthority集合。

使用基于用户名/密码的身份验证时，GrantedAuthority通常由UserDetailsService加载



***AuthenticationManager***

AuthenticationManager定义了过滤器如何进行认证。控制器调用AuthenticationManager，将Authentication设置到SecurityContextHolder里。如果你没有与Spring Security s过滤器集成，你可以直接设置securitycontext，不需要使用AuthenticationManager



***ProviderManager***

每个Provider执行一种认证，比如账号密码认证，SAML认证，支持多重认证，职工开一个AuthenticationManager

![image-20201215144637471](/Users/chenguanlin/Documents/note/0img/多重认证交给一个AuthenticationManager.png)

认证后会清楚敏感信息,多个providerManager也可以共享一个AuthenticationManager。







SecurityFilterChain

authenticationManagerResolve

hasAnyRole

ClientRegistrations

RequestCache

RequestRejectedHandler

StrictHttpFirewall

AesBytesEncryptor



User实体类

***基于内存的加密***

```java
UserBuilder users = User.withDefaultPasswordEncoder();
User user = users
  .username("user")
  .password("password")
  .roles("USER")
  .build();
User admin = users
  .username("admin")
  .password("password")
  .roles("USER","ADMIN")
  .build();
```

这的确对密码进行了hash，但是信息还是暴露再了源代码中，所以并不能用于生产环境，只是为了看下效果。



***外部散列***

Encode with Spring Boot CLI(命令行)

使用命令`spring encodepassword password`将会使用DelegatingPasswordEncoder加密成`{bcrypt}$2a$10$X5wFBtLrL/kHcmrOGGTrGufsBX8CJ0WpQpF3pgeuxBB/H73BK1DW6`





1. Spring Security

存储密码，PasswordEncoder接口来加密密码







OAuth 2.0 Client

OAuth 2.0Resource Server





































WebSecurityConfigurerAdapte的几个重载configure()

























Spring Security实现OAuth2.0

启动类上添加

@EnableAuthorizationServer
@EnableResourceServer



























10通过用户名和密码进行认证

Username/Password Authentication

1. 读取用户名和密码

从HttpServletRequest读取用户名和密码

- [Form Login](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-form)
- [Basic Authentication](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-basic)
- [Digest Authentication](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-digest)



2. 存储机制

可以利用任何受支持的存储机制，

- Simple Storage with [In-Memory Authentication](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-inmemory)
- Relational Databases with [JDBC Authentication](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-jdbc)
- Custom data stores with [UserDetailsService](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-userdetailsservice)
- LDAP storage with [LDAP Authentication](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-ldap)



10.1表单登录

Spring Security支持通过html表单提供用户名和密码

用户是如何被重定向到登录表单的？

![image-20201124173610804](/Users/chenguanlin/Documents/note/0img/form-login.png)

用户请求资源时没有认证->[`FilterSecurityInterceptor`](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authorization-filtersecurityinterceptor)抛出异常，拒绝访问->重定向到登录页面





1. UsernamePasswordAuthenticationFilter根据传进来的userName和passwoed生成一个Authentication的子类：UsernamePasswordAuthenticationToken。
2. 将token传入AuthenticationManager进行认证，AuthenticationManager的认证依赖于选择的用户信息存储的方式
3. 认证失败：
   * 清空SecurityContextHolder
   * 记住密码功能loginFail
   * 执行认证失败处理器
4. 认证成功
   * 通知SessionAuthenticationStrategy一个新的登录
   * RememberMeServices.loginSuccess执行
   * ApplicationEventPublisher发布一个InteractiveAuthenticationSuccessEvent
   * 执行AuthenticationSuccessHandler

SpringSecurity默认开启表单登录，但是，只要提供了任何基于servlet的配置，就必须显式地提供基于表单的登录

```java
protected void configure(HttpSecurity http) {
    http
        // ...
        .formLogin(withDefaults());
}
```

自定义登录页面

```java
protected void configure(HttpSecurity http) throws Exception {
    http
        // ...
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
        );
}
```

10.2基于servlet的应用程序提供基本HTTP身份验证支持。

![image-20201124183205925](/Users/chenguanlin/Documents/note/0img/Basic Authentication01.png)

用户请求资源时没有认证->[`FilterSecurityInterceptor`](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authorization-filtersecurityinterceptor)抛出异常，拒绝访问->BasicAuthenticationEntryPoint 返回一个header：WWW-Authenticate。

当客户端收到WWW-Authenticate头时，它知道应该使用用户名和密码重试。



默认支持，显式声明一下

```java
protected void configure(HttpSecurity http) {
    http
        // ...
        .httpBasic(withDefaults());
}
```



10.3 Digest Authentication

不安全，不推荐使用

10.4In-Memory Authentication

Spring Security’s `InMemoryUserDetailsManager` implements [UserDetailsService](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#servlet-authentication-userdetailsservice) ，实现基于内存检索用户名和密码。InMemoryUserDetailsManager通过实现UserDetailsManager接口来提供对UserDetails的管理。

```java
@Bean
public UserDetailsService users() {
    UserDetails user = User.builder()
        .username("user")
        .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
        .roles("USER")
        .build();
    UserDetails admin = User.builder()
        .username("admin")
        .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
        .roles("USER", "ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
}
```

10.5JDBC Authentication

Spring Security’s JdbcDaoImpl implements UserDetailsService，可以通过JDBC匹配用户名和密码。JdbcUserDetailsManager继承了JdbcDaoImpl，通过UserDetailsManager接口提供对用户详细信息的管理。



Spring Security为基于JDBC的身份验证提供了默认查询。JdbcDaoImpl需要获得用户的密码、帐户状态(启用或禁用)和权限(角色)列表

```sql
create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
```

UserDetails由UserDetailService返回，DaoAuthenticationProvider验证来验证UserDetail然后返回Authentication，Authentication的principal属性就是UserDetail

UserDetailsService是给DaoAuthenticationProvider使用的，来获得user的详情信息，Spring Security提供了UserDetailsService的实现：基于内存和JDBC。我们可以自定义一个UserDetailsService，通过暴露一个UserDetailsService的实现类

***DaoAuthenticationProvider***利用UserDetailsService和PasswordEncoder来验证用户名和密码

![image-20201124190143884](/Users/chenguanlin/Documents/note/0img/DaoAuthenticationProvider.png)

1. 读取...封装成...传给...
2. ProviderManager调用...
3. 获得
4. 校验
5. 认证成功，返回UsernamePasswordAuthenticationToken，包含令牌和权限，UsernamePasswordAuthenticationToken最终被放入SecurityContextHolder



10.9LDAP Authentication

LDAP经常被组织用作用户信息的中央存储库和身份验证服务





# bilibili



OAuth2.0

分布式系统中第三方应用认证协议



**认证是为了保护系统资源**



**会话**

用户不用每次访问都进行一次认证，认证成功之后可以创建一个会话。

# ***基于session的认证授权***

用户登录成功后，服务器保存用户的认证信息，将sessionId中返回客户端，客户端将sessionId存入cookie中，以后每次发送请求都会携带cookie，这样服务器找到sessionId时就不需要再进行认证了。

## **通过使用servlet的拦截器实现**

创建实体类来保存用户信息

创建根据用户名查找用户信息的方法

根据用户名查找密码与传入的密码进行比较

登录成功后将用户信息以用户名为key，用户实体类为value存入session中

创建拦截器，拦截请求进行认证授权

定义一个拦截器/过滤器，prehandel里来判断用户请求的路径是否在用户的权限范围内

```java
拦截器
```



## ***SpringSercurity实现***

SpringSercurity简化了许多代码，而且服务器也不用存session了，我们根据需求，提供一个查询用户信息的方法给SpringSercurity。它的本质也是基于Filter，各个功能通过各个Filter来完成，其中xxxProvider是真正干活的人。

* 提供了拦截功能，需要我们配置请求拦截机制，对用户请求进行限制。config(HttpSercurity)
* 用户密码校验(需要提供UserDetailService来告诉它怎么通过用户名找用户信息)，
* 提供了封装用户信息的实体类UserDetail，
* 密码不能明文存储的，所以要提供一个密码编码器PasswordEncoder来比对密码

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // http配置，对请求进行拦截,规定了什么请求可以访问什么url
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                 可以添加过滤器
//               .addFilterBefore()
//                .antMatcher("/*").authenticationProvider()

                //  对url进行配置
                .authorizeRequests()
                // antMatchers中可以传入多个url，请求方法或请求方法+多个url,拥有某种权限或角色的可以访问
                .antMatchers("/excel/download/**").hasAuthority("excel_downLoad")
                .antMatchers("/admin/**").access("hasRole('anmin') and hasRole('manager')")
                // 其余的认证后可直接访问，anyRequest能匹配到所有url
                .anyRequest().authenticated()
                // 其余的不需认证，可直接访问
                .anyRequest().permitAll()
                // 表单登录配置，表示没有认证时，提供登录页面，不设置时直接返回403错误码
                .and().formLogin()
                // 允许httpBasic认证
                .and().httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService() {
      /*
          返回一个UserDetailsService实现类，到时候可以查redis等，现在模拟安名字查信息
          查到了该用户有excel_downLoad下载权限
       */
        return (userName) -> {
            // 用户权限列表
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(() -> "excel_downLoad");
            String encodePassword = passwordEncoder().encode("111");
            return new User("test", encodePassword, grantedAuthorities);
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/index.html")
                .antMatchers("/error")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-resources");
    }
    
}
```



**业务流程**：

1. 登录时查询数据库信息(Mongo/MySQL等)
2. 将该用户信息存入redis，这样不用每次去查数据库，直接从redis中找；
3. 认证时从redis中找UserDetail，找到后重置失效时间，



**授权的数据模型**有两种：基于角色和基于资源，hasRole就是基于角色，hasAuthority就是基于资源



RBAC，R作为Role，基于角色的访问控制，

资源->权限->角色->用户

一个资源可以抽象出多个权限

一个角色就是权限的打包，可以有多个角色

一个用户可以有一个或多个角色

两个关系表：角色和权限间的关系表，用户和角色间的关系表



RBAC，R作为Resource，直接判断权限，基于资源的访问控制，粒度小，更灵活

***用户*** 可以对哪些 ***资源*** 进行 ***什么操作***资源包括功能的资源，数据资源等

基于角色的访问控制的缺点：

经理可以查工资，判断是经理时才能进行工资查询操作，单现在部门经理也要能查工资，就需要该代码了。

基于资源的访问控制，如果当前角色拥有对某个资源的某种操作，就能访问资源。即***直接判断有没有该权限，直接用权限进行判断，中间不再使用角色***

对比

```java
if("奖励".equals(role)){...}
if(user.hasPermission){...}; // 这样可以给用户添加权限，如果部门经理都需要，就给整个role添加权限
```



**认证流程**

![image-20201213182505425](/Users/chenguanlin/Documents/note/0img/认证流程.png)

最终将认证信息存入了安全上下文

会话管理？

会话超时：server.servlet.session.timeout=3600s

![image-20201213190456876](/Users/chenguanlin/Documents/note/0img/会话管理.png)

**安全会话cookie**

![image-20201213190735907](/Users/chenguanlin/Documents/note/0img/安全会话cookie.png)

**授权流程**

![image-20201213183044322](/Users/chenguanlin/Documents/note/0img/授权流程.png)

授权策略

web授权

web授权是通过url拦截进行授权

![image-20201213202744414](/Users/chenguanlin/Documents/note/0img/web授权.png)



***方法授权***

方法授权是通过方法拦截进行授权，在controller上(可以但不建议service/dao/)上加注解进行设置

两者同时存在时会先进行web授权，再进行方法授权

@PreAuthorize，在配置类中添加注解开启，`@EnableGlobalMethodSecurity(prePostEnabled = true)`

在方法上@PreAuthorize来在访问前进行授权例如`@PreAuthorize("hasAuthority('add')")`

@PostAuthorize

@Secured,需要在配置类中开启才能使用

```java
@EnableGlobalMethodSecurity(securedEnable=true)
```

@Secured("XXX")会传给AccessDecisionManager，让他做试驾的决定

XXX要符合规范，不推荐使用@Secured



***域对象授权***



? 

为什么要关闭csrf



实体类：

上下文中的方法：获得、清空



# ServletSecurity



## SpringSecurity对servlet的支持是基于Filter

对Filter的一个回顾

```java
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    // do something before the rest of the application
    chain.doFilter(request, response); // invoke the rest of the application
    // do something after the rest of the application
}
```



### DelegatingFilterProxy

Spring提供了一个实现[`DelegatingFilterProxy`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/web/filter/DelegatingFilterProxy.html/) 接口的Filter，它建立起Spring与Servlet的**桥梁**。

Servlet container允许使用自己的标准进行Filter注册，但是它识别不了Spring管理的Bean。DelegatingFilterProxy的作用就是使用Servlet的机制将自己注册进FliterChain，将实际工作委托给Spring中的bean操作。



<img src="/Users/chenguanlin/Documents/note/0img/detecationFilterProxy.png" alt="image-20210110225310506" style="zoom:25%;" />

`DelegatingFilterProxy` looks up *Bean Filter0* from the `ApplicationContext` and then invokes Bean Filter0



### FilterChainProxy

完成认证授权，一个过滤器是不行的，SpringSecurity提供了一个特殊的Filter：`FilterChainProxy`,它通过SecurityFilterChain委托给多个Filter实例。

<img src="/Users/chenguanlin/Documents/note/0img/FilterChainProxy.png" alt="image-20210110225924841" style="zoom:25%;" />



### SecurityFilterChain

SecurityFilerChain决定调用哪些Filter。

<img src="/Users/chenguanlin/Documents/note/0img/SecurityFilterChain.png" alt="image-20210110230046506" style="zoom:25%;" />

Security Filters都是通过FilterChainProxy注册的，所以想要调试时可以在FilterChainProxy上打断点。

使用FilterChainProxy注册Security Filters的好处键=见官网

此外，SecurityFilterChain是多个的。就想在配置HttpSecurity时，会根据URL选FilterChain。



### Filter的完整顺序

ChannelProcessingFilter

WebAsyncManagerIntegrationFilter

SecurityContextPersistenceFilter

HeaderWriterFilter

CorsFilter

CsrfFilter

LogoutFilter

OAuth2AuthorizationRequestRedirectFilter

Saml2WebSsoAuthenticationRequestFilter

X509AuthenticationFilter

AbstractPreAuthenticatedProcessingFilter

CasAuthenticationFilter

OAuth2LoginAuthenticationFilter

Saml2WebSsoAuthenticationFilter

UsernamePasswordAuthenticationFilter

OpenIDAuthenticationFilter

DefaultLoginPageGeneratingFilter

DefaultLogoutPageGeneratingFilter

ConcurrentSessionFilter

DigestAuthenticationFilter

BearerTokenAuthenticationFilter

BasicAuthenticationFilter

RequestCacheAwareFilter

SecurityContextHolderAwareRequestFilter

JaasApiIntegrationFilter

RememberMeAuthenticationFilter

AnonymousAuthenticationFilter

OAuth2AuthorizationCodeGrantFilter

SessionManagementFilter

ExceptionTranslationFilter

FilterSecurityInterceptor

SwitchUserFilter



## Security异常处理



### ExceptionTranslationFilter

通过ExceptionTranslationFilter将AccessDeniedException和AuthenticationException转换为HTTP响应

ExceptionTranslationFilter作为一个SecurityFilter存在于

<img src="/Users/chenguanlin/Documents/note/0img/ExceptionTranslationFilter.png" alt="image-20201124181819341" style="zoom: 50%;" />

伪代码来解释

```java
try {
    filterChain.doFilter(request, response); 
} catch (AccessDeniedException | AuthenticationException ex) {
    if (!authenticated || ex instanceof AuthenticationException) {
        startAuthentication(); 
    } else {
        accessDenied(); 
    }
}
```



# Authentacation



基于Servlet的SpringSecurity认证的重要组件：



## 安全上下文



<img src="/Users/chenguanlin/Documents/note/securityContext.png" alt="image-20210111164252410" style="zoom:25%;" />

### SecurityContextHolder

保存着SecurityContext

可以通过SecurityContextHolder获得当前认证的用户

```java
SecurityContextHolder.getContext().getAuthentication().getName()
```



### SecurityContext

通过SecurityContextHolder获得，包含着Authentication对象



### Authentication

用来保存用户的认证信息，在SpringSecurity中有两个用途：

1. 作为输入，给AuthenticationManager提供用户信息
2. 作为输出，通过SecurityContext获得当前用户信息

**Principal**：用来鉴别用户，比如UserDetail

**credentials**：提供密码

**authorities**：提供权限 



## 认证管理器



### AuthenticationManager

AuthenticationManager定义了Spring Security’s Filters怎么进行认证

AuthenticationManager通过调用the controller (i.e. Spring Security’s Filterss)，将Authentication设置到SecurityContextHolder，委托给了一系列ProviderManager。

虽然AuthenticationManager的实现可以是任何形式，但最常见的实现是ProviderManager。



### ProviderManager

