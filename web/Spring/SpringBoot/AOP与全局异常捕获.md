静态AOP：自己写代理对象

动态AOP：运行时生成代理对象

术语

织入时机



优点：

​		对原有代码没有侵入性



代理模式

动态代理的实现



Cglib，AspectJ



# SpringBoot中的使用

1. 添加依赖

   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-aop</artifactId>
   </dependency>
   ```

2. 创建切面

   ```java
   @Aspect
   @Component
   public class HttpAspect {
    
       private static final Logger logger=LoggerFactory.getLogger(HttpAspect.class);
    
       //配置切点
       @Pointcut("execution(public * com.springboot.controller.GirlController.*(..))")
       public void log(){
       }
    
       //joinPoint用于获取域切入点方法有关的信息
       @Before("log()")
       public void doBefore(JoinPoint joinPoint){
           ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
           HttpServletRequest request=requestAttributes.getRequest();
           //url
           logger.info("url={}",request.getRequestURL());
           //method
           logger.info("method={}",request.getMethod());
           //ip
           logger.info("ip={}",request.getLocalAddr());
           //类方法
           logger.info("class_method={}",joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName());
           //方法参数
           logger.info("args={}",joinPoint.getArgs());
       }
    
       @After("log()")
       public void doAfter(){
           logger.info("doAfter={}",2222222);
       }
    
       //得到response返回的数据，returning代表切入点方法返回的数据
       @AfterReturning(returning = "object",pointcut = "log()")
       public void doAfterReturning(Object object){
           logger.info("response={}",object.toString());
       }
   }
   ```

   