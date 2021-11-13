* 使用场景
* 使用方法
* 配置文件

安装

常用命令

配置







安装配置



配置高可用集群

# 概述



[参考文档](http://nginx.org/en/docs/http/ngx_http_gzip_module.html)

**主要功能：**

* web server

* reverse proxy
* load balancer
* and HTTP cache



**为什么是nginx？**

* 高性能的反向代理服务器
* 高并发性能很好，能够支撑 5 万的并发量
* 运行时内存和 CPU 占用率低，配置简单，容易上手，而且运行非常稳定

* nginx可以作为静态页面的服务器
* 同时它还可以提供 IMAP/POP3 邮件代理服务等功能



**代理服务器**：

​		代理用户去获取信息，是用户和服务之间的中转站，转发用户的请求

代理的基本功能：

* 一个IP供多个用户使用

在IPV4中IP地址是宝贵的资源

* 缓冲资源
* 对x网络用户进行权限管理，监控等



**正向代理与反向代理**

* 正向代理用来代理客户隐藏客户身份，部署在客户端，反向代理用来代理服务器隐藏，部署在服务器端

* 正向代理时客户直到服务器，但是没办法连接，需要限连接代理服务器去请求
* 反向代理时用户不知道服务器，直接访问代理服务器，又代理服务器决定请求路径



# 使用



## 安装

[安装参考](https://www.runoob.com/linux/nginx-install-setup.html)



## 目录结构

**配置文件**

/etc/nginx

**命令文件**

/usr/sbin/nginx

**站点文件**

/usr/share/nginx

静态文件



日志文件







## nginx常用命令

| 命令            | 描述             |
| --------------- | ---------------- |
| nginx -v        | 查看版本         |
| ./nginx         | 启动nginx        |
| nginx -s stop   | 关闭nginx        |
| nginx -s reload | 重新加载配置文件 |



快速停止

`./nginx -s stop`

平缓停止(不再接受新请求)

`./nginx -s quit`

重启

`./nginx -s reload`通常我们使用nginx修改最多的便是其配置文件 nginx.conf。修改之后想要让配置文件生效而不用重启 nginx，便可以使用此命令。

检查配置文件是否规范

`nginx -t -c /usr/local/nginx/conf/nginx.conf`

docker run  --name nginx -d -p 80:80 -v /Users/chenguanlin/Documents/workspace/nginx/log:/var/log/nginx -v /Users/chenguanlin/Documents/workspace/nginx/config:/etc/nginx/nginx.conf  -v /Users/chenguanlin/Documents/workspace/nginx/html:/usr/share/nginx/html nginx





**docker中部署nginx**

配置文件的路径：/etc/nginx

日志的目录：/var/log/nginx

default.conf：/etc/nginx/conf.d/default.conf



# 目录文件



# 配置文件



etc/nginx下

# nginx.conf



由三部分组成全局块，events块，https块

```
...              #全局块

events {         #events块
   ...
}

http      #http块
{
    ...   #http全局块
    server        #server块
    { 
        ...       #server全局块
        location [PATTERN]   #location块
        {
            ...
        }
        location [PATTERN] 
        {
            ...
        }
    }
    server
    {
      ...
    }
    ...     #http全局块
}
```

 

## 全局块：



| 配置                 | 描述                             |
| -------------------- | -------------------------------- |
| work_processes       | 值越大，并发越强，但受硬件等制约 |
| error_log            | 日志位置和级别                   |
| worker_rlimit_nofile |                                  |
| pid                  | nginx进程pid存放路径             |
| user                 | 运行nginx服务器的用户组          |



## events块



影响nginx服务器与用户网络的连接



| 配置               | 描述       |
| ------------------ | ---------- |
| worker_connections | 最大连接数 |
| use epoll          |            |



### http全局块

包含http全局块和server块



| 配置                  | 描述 |
| --------------------- | ---- |
| include               |      |
| default_type          |      |
| log_format            |      |
| access_log            |      |
| sendfile              |      |
| tcp_nopush            |      |
| keepalive_timeout     |      |
| proxy_connect_timeout |      |
| proxy_read_timeout    |      |
| proxy_send_timeout    |      |
| send_timeout          |      |
| upstream              |      |



### server块

一个server块代表一个虚拟主机，进行主机设置





#### 全局server块

| 配置            | 描述               |
| --------------- | ------------------ |
| listen          |                    |
| port            | 监听的端口         |
| server_name     | 主机名称           |
| gzip            | 开关               |
| gzip_min_length | 小于该长度的不压缩 |
| gzip_buffers    |                    |
| gzip_disable    |                    |
| gzip_comp_level |                    |
| gzip_types      |                    |
| gzip_vary       |                    |
|                 |                    |



#### location块

用来路由

```
location [pattern] {

}
```



| 配置                 | 描述 |
| -------------------- | ---- |
| index                |      |
| root                 |      |
| alias                |      |
| proxy_pass           |      |
| include              |      |
| fastcgi_pass         |      |
| fastcgi_index        |      |
| fastcgi_param        |      |
| deny                 |      |
| rewrite              |      |
| client_max_body_size |      |
| add_header           |      |
| autoindex            |      |
| autoindex_exact_size |      |
| return               |      |



还可以写if等表达式



**alias与root的区别**

* alias用来指定具体的文件，root则是用来指定文件的上级目录，即alias显示的内容是固定的，而root显示的内容=root指定的目录/location的路径
* 它们下载的文件名都是location指定的文件名
* 使用alias标签的目录块中不能使用rewrite的break。
* alias在使用正则匹配时，必须捕捉要匹配的内容并在指定的内容处使用。
* alias只能位于location块中







**pattern**

[参考](cnblogs.com/jpfss/p/10232980.html)

| 符号 | 说明                   |
| ---- | ---------------------- |
| =    | =开头表示精确匹配      |
| ^~   |                        |
| ~    | 区分大小写             |
| ~*   | 不区分大小写的正则匹配 |
| /    | 表示任意路径           |































# 常用命令





# 反向代理配置



即用户不直接请求服务，而是将请求发送给nginx，nginx将请求转发到服务器。



```
server {
	port 80;
	server_name localhost;
	location / {
		proxy_pass http://localhost:8080/hello
	}
	location ~ /hello{
		proxy_pass localhost:8081
	}
	location ~ /hi{
		proxy_pass localhost:8082
	}
}
```



# 负载均衡配置



将请求分发到不同服务器



## HTTP Load Balancing

类似于SpringCloud，有一个服务器列表，调用服务时不用具体的host，而是用服务名，nginx根据负载均衡从服务列表中选一个host来用

Http的upstream模块控制这HTTP的负载均衡，定义了请求如何负载均衡



```
upstream myserver{
	server 10.10.12.45:80 weight=1; 
	server app.example.com:80 weight=2;
}
server {
	port 80;
	server_name localhost;
	location / {
		proxy_pass http://localhost:8080/hello
	}
	location ~ /hello{
		proxy_pass localhost:8081
	}
	location ~ /hi{
		proxy_pass myserver
	}
}
```



**负载均衡策略**



load-balancing algorithms, such as round robin, least con‐ nection, least time, IP hash, and generic hash

轮询

默认所有地址的weight都是1



weight

可以根据服务器的情况分配权重



ip_hash

每个客户固定访问一个服务器,根据访问ip的hash值分配服务器

直接在upstream中添加ip_hash

```
upstream myserver{
	ip_hash;
	server 192.168.0.1:8080;
	server 192.168.0.1:8081;
}
```



fair

第三方，按照响应时间分配。

```
upstream myserver{
	fair;
	server 192.168.0.1:8080;
	server 192.168.0.1:8081;
}
```



## 案例

通过两个nginx服务来实现负载均衡

1. 模拟集群服务器

假设一个服务有两个主机提供服务

```
    server {
        listen       8001;
        server_name  localhost;
        location / {
           proxy_pass https://www.baidu.com;
        }
    }
    server {
        listen       8888;
        server_name  localhost;
        location / {
           proxy_pass https://www.elastic.co/guide/en/elasticsearch/client/java-api/5.6/java-docs-index.html;
        }
    }
```



2. 另一个nginx做负载均衡

```
    upstream pop {
        server localhost:8001 weight=3;
        server localhost:8888 weight=1;
    } 

    server {
        listen       80;
        server_name  localhost;
        location / {
           proxy_pass http://pop;
        }
    }
```



3. 访问localhost，发现在百度和ES间

































## TCP LoadBalance



```
stream {
upstream mysql_read {
server read1.example.com:3306 weight=5; server read2.example.com:3306;
server 10.10.12.34:3306
server {
listen 3306;
proxy_pass mysql_read; }
}
```























**高可用配置**

高可用集群配置

nginx集群

keepalived

所有服务器，都需要安装一个keepalived，将服务器绑定到一个虚拟地址中

1. 安装keepalived

`yum install keepalived -y`

2. 修带keepalived的配置文件























对外开放访问的端口：

firewall-cmd --add-port=8080/tcp --permanent

fireawll-cmd --reload



查看已经开放的端口号

firewall-cmd --list-all



# 动静分离配置



使用alias/root

```
location /index.html {
	root /usr/share/nginx/html/;
}
```

```
location /hello {
    root /usr/share/nginx/html/hello.html/;
}
```



想要只下载时：

`add_header Content-Disposition "attachment;"`

```
// 所有文件都不打开
location / {
    add_header Content-Disposition "attachment;filename*=utf-8'zh_cn'$arg_n";
}
```

修改下载名

```
location / {
　　if ($request_uri ~* ^.*\?n=([^&]+)$) {
　　　　add_header Content-Disposition "attachment;filename*=$arg_n";
　　}
}
```















出错

`nginx: [emerg] unknown directive "port" in /etc/nginx/nginx.conf:33`

















```
user root;
worker_processes  2;
 
error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;
 
#pid        logs/nginx.pid;
 
events {
    worker_connections  2048;
}
 
http {
    include       mime.types;
    default_type application/json;
    log_format main '$remote_addr - $remote_user [$time_local] "$host:$server_port" "$request" '
                        '$status $body_bytes_sent "$request_body" "$http_referer" '
                                            '"$http_user_agent" "$http_x_forwarded_for" "$request_time"';
    access_log  logs/access.log  main;
 
    sendfile        on;
    #tcp_nopush     on;
 
    keepalive_timeout  600s;
    proxy_connect_timeout 600s;
    proxy_read_timeout 600s;
    proxy_send_timeout 600s;
    send_timeout  600s;
 
    upstream mingjing-interface-api {
        server 10.186.221.29:8085 weight=1;
        server 10.186.237.14:8085 weight=1;
        server 10.186.221.25:8085 weight=1;
        server 10.186.221.21:8085 weight=1;
    }
    
    server {
        listen       8787;
        server_name  yunjing.baidu.com;
 
        gzip                on;
        gzip_min_length     1000;
        gzip_buffers 16 8k;
        gzip_disable        "msie6";
        gzip_comp_level  5;
        gzip_types          text/plain text/css application/json application/javascript application/x-javascript text/xml application/xml application/xml+rss text/javascript;
        gzip_vary           on;
 
#        location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|html)$ {  
#            expires    8h;
#            root html;  
#        } 
 
        location ^~ /api/ {
            rewrite /api/(.+)$ /$1 break; 
            proxy_pass http://localhost:8082;
            client_max_body_size 5m;
        }
 
        location ^~ /api_zhuji/ {
            rewrite /api_zhuji/(.+)$ /$1 break;
            proxy_pass http://10.60.152.40:8089;
            client_max_body_size 5m;
        }
 
        location ^~ /label_engine/ {
            rewrite /label_engine/(.+)$ /$1 break;
            proxy_pass http://10.133.143.27:8080;
            client_max_body_size 5m;
        }
 
        location ^~ /relation-analysis/ {
            rewrite /relation-analysis/(.+)$ /$1 break;
            proxy_pass http://10.90.93.28:8459;
            client_max_body_size 5m;
        }
        
        # 诸暨私有化rest api
        location ^~ /rest_api_zhuji/ {
            rewrite /rest_api_zhuji/(.+)$ /$1 break;
            proxy_pass http://10.60.152.40:8189;
            client_max_body_size 20m;
        }
 
        # 明镜API对外传输数据接口
        location ^~ /mingjing_interface_api/ {
            rewrite /mingjing_interface_api/(.+)$ /$1 break;
            proxy_pass http://mingjing-interface-api;
            client_max_body_size 20m;
        }
 
        #mingjing_demo_fe转发到内网
 
        location ^~ /8082/relation-analysis/ {
            rewrite /8082/relation-analysis/(.+)$ /$1 break;
            proxy_pass http://10.124.119.21:8522/;
        }
 
        location ^~ /8082/keySuspect/ {
            rewrite /8082/(.+)$ /$1 break;
            proxy_pass http://10.124.119.21:8521/;
        }
        
        location ^~ /8082/relation/ {
            rewrite /8082/(.+)$ /$1 break;
            proxy_pass http://10.124.119.21:8522/;
        }
 
        location ^~ /8086/extract/ {
            add_header Access-Control-Allow-Origin "*";
            add_header Access-Control-Allow-Methods "POST,GET,OPTIONS,PUT,DELETE";
            add_header Access-Control-Allow-Headers "authorization";
            add_header Access-Control-Allow-Credentials "true";
            rewrite /8086/(.+)$ /$1 break;
            proxy_pass http://10.88.41.42:8899/;
        }
        
        location ^~ /8086/archive/ {
            rewrite /8086/(.+)$ /$1 break;
            proxy_pass http://10.88.41.42:8899/;
        }
 
        location ^~ /8086/search/ {
            add_header Access-Control-Allow-Origin "*";
            add_header Access-Control-Allow-Methods "POST,GET,OPTIONS,PUT,DELETE";
            add_header Access-Control-Allow-Headers "authorization";
            add_header Access-Control-Allow-Credentials "true";
            rewrite /8086/(.+)$ /$1 break;
            proxy_pass http://10.88.41.42:8233/;
        }
 
        location ^~ /8086/knowledge/ {
            add_header Access-Control-Allow-Origin "*";
            add_header Access-Control-Allow-Methods "POST,GET,OPTIONS,PUT,DELETE";
            add_header Access-Control-Allow-Headers "authorization";
            add_header Access-Control-Allow-Credentials "true";
            rewrite /8086/(.+)$ /$1 break;
            proxy_pass http://10.124.119.21:8522/;
        }
 
        location ^~ /8086/timespace/ {
            add_header Access-Control-Allow-Origin "*";
            add_header Access-Control-Allow-Methods "POST,GET,OPTIONS,PUT,DELETE";
            add_header Access-Control-Allow-Headers "authorization";
            add_header Access-Control-Allow-Credentials "true";
            rewrite /8086/(.+)$ /$1 break;
            proxy_pass http://10.88.41.42:8233/;
        }
 
        location ^~ /8086/relation-analysis/ {
            add_header Access-Control-Allow-Origin "*";
            add_header Access-Control-Allow-Methods "POST,GET,OPTIONS,PUT,DELETE";
            add_header Access-Control-Allow-Headers        $http_access_control_request_headers;
            add_header Access-Control-Allow-Credentials "true";
            rewrite /8086/relation-analysis/(.+)$ /$1 break;
            proxy_pass http://10.90.93.28:8459/;
        }
 
        location ^~ /8091/dynamicrel/ {
            rewrite /8091/(.+)$ /$1 break;
            proxy_pass http://10.88.41.42:8321;
        }
 
       location ^~ /8091/relation/ {
            rewrite /8091/(.+)$ /$1 break;
            proxy_pass http://10.124.119.21:8300;
        }
 
        location ^~ /8091/onlineMining/ {
            rewrite /8091/(.+)$ /$1 break;
            proxy_pass http://10.88.41.42:8321;
        } 
        
        # redirect server error pages to the static page /50x.html
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
 
    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;
 
    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
 
 
    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;
 
    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;
 
    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;
 
    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;
 
    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
 
}
```





```
The Complete NGINX Cookbook
```



查看日志



**docker部署nginx**

nginx镜像包含了默认的配置，所以需要





include

配置文件可以拆分，将多个配置文件include到主配置文件中



default_type

nginx的默认相应类型

Nginx 会根据mime type定义的对应关系来告诉浏览器如何处理服务器传给浏览器的这个文件，是打开还是下载
如果Web程序没设置，Nginx也没对应文件的扩展名，就用Nginx 里默认的 default_type定义的处理方式。







