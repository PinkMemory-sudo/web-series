# ResourceUtils

framework内部使用的定位文件系统中的实用工具



```java
ClassUtils.getDefaultClassLoader().getResource("").getPath();

ResourceUtils.getURL("classpath:").getPath();



//环境变量中的属性

Properties properties = System.getProperties();

properties.getProperty("user.dir");
MonitorController.class.getProtectionDomain().getCodeSource().getLocation().getFile();
//class 获取方式 获取当前类文件所在路径
this.getClass().getResource("").getPath();
//class 获取方式 获取当前类路径
this.getClass().getResource("/").getPath();
Thread.currentThread().getContextClassLoader().getResource("");
```































```
String md5 = DigestUtils.md5DigestAsHex("123".getBytes());
```

































SqEL(Spring Expression Language)

Spring表达式语言

#{…} 和${…} 可以混合使用，但是必须`#{}外面，${}在里面,`#{ '${}' } ，注意单引号，注意不能反过来