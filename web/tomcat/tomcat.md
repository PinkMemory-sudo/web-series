是什么？

​	是一个servlet容器，实现了对 Servlet 和 JSP 的支持。内含了一个 HTTP 服务器，所以可以讲tomcat看成一个服务器，它包含了一个配置管理，可以用xml来进行配置。



目录结构：

| 目录     | 说明                   |
| -------- | ---------------------- |
| /bin     | 存放了一些tomcat的脚本 |
| /conf    | 存配置文件             |
| /logs    | 默认日志目录           |
| /webapps | webapp 运行的目录      |

webapp目录

|                  |      |
| ---------------- | ---- |
| webapp           |      |
| META-INF         |      |
| WEB-INF          |      |
| /WEB-INF/classes |      |
| /WEB-INF/lib     |      |
| /WEB-INF/web.xml |      |

