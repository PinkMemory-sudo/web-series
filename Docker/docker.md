# Basic Concept



* 用来干什么的
* 怎么用
* 创建镜像
* 更新镜像
* 使用镜像



## 硬件虚拟化与容器

[参考](https://www.zhihu.com/question/28300645/answer/585166942)



***硬件虚拟化***

有时候需要多个环境，如果用多个计算机就太浪费，一个计算机装多个操作系统切换太麻烦，硬件虚拟化技术就出现了。

一个计算机绝大多数时间内实际是硬件过剩的，只有运行一些特殊任务或游戏等时才满负荷运行。

硬件虚拟换就是一个特殊软件软件模拟出来一套计算机的硬件资源，可以在这个软件上安装操作系统等软件。特殊软件上的应用访问资源需要通过这个特殊软件去调用硬件资源，而对于这些软件而言和运行在一个独立的物理机上一样。这类特殊的软件有一个名词：**Hypervisor**



Hypervisor根据其对硬件资源的访问方式，可以分为两大类：直接调用硬件和通过宿主机的操作系统调用硬件

* Type I是Hypervisor直接访问硬件资源，通常会有另一个操作系统运行于Hypervisor之上来对硬件资源，例如VMware EXSi，Windows的Hyper-V，Linux的Xen；
* Type II是Hypervisor和普通的应用一样，运行在某个操作系统（例如Windows或者Linux等，这里称之为宿主机操作系统，Host OS）之上，Hypervisor通过Host OS访问硬件资源，例如VMware Workstation，Virtual Box等。



***容器***

一个Hypervisor上可以运行多个虚拟机，虚拟机中都有各自的操作系统，如果启动了多个虚拟机，其中大部分甚至全部的操作系统是一样的，就比较消耗资源了。能不能让Hypervisor上的应用都使用同一个操作系统？

容器(*操作系统层的虚拟化*)可以来解决这个问题。 在Linux可以通过控制组（Control Group，通常简写为cgroup）隔离，并把应用和运行库打包在一起，来实现这个目的 。

```
+-----+-----+-----+-----+                                   +-----+-----+-----+-----+
|App A|App B|App C|App D|     +-----+-----+-----+-----+     |App A|App B|App C|App D|
+-----+-----+-----+-----+     |App A|App B|App C|App D|     +-----+-----+-----+-----+
|+---------------------+|     +-----+-----+-----+-----+     |Guest|Guest|Guest|Guest|
||   Runtime Library   ||     |Lib A|Lib B|Lib C|Lib D|     | OS0 | OS1 | OS2 | OS3 |
|+---------------------+|     +-----+-----+-----+-----+     +-----+-----+-----+-----+
||       Kernel        ||     |    Container Engine   |     |        Hypervisor     |
|+---------------------+|     +-----------------------+     +-----------------------+
|   Operating System    |     |         Host OS       |     |         Host OS       |
+-----------------------+     +-----------------------+     +-----------------------+
|       Hardware        |     |        Hardware       |     |        Hardware       |
+-----------------------+     +-----------------------+     +-----------------------+
    Physical Machine                  Container                 Type II Hypervisor
```

可以看出容器的**优点**：

* 启动快：没有硬件初始化的过程，启动时间短
* 消耗少：无需安装/运行App不需要的运行库/操作系统服务，内存占用、存储空间占用都小的多

**缺点**:

*  因为共用内核，只靠cgroup隔离，应用之间的隔离是不如虚拟机彻底的，如果某个应用运行时导致内核崩溃，所有的容器都会崩溃 



 Docker把App和Lib的文件打包成为一个镜像 ,并通过联合文件系统实现了：

* 多个App可以共用相同的底层镜像（初始的操作系统镜像） 
*  App运行时的IO操作和镜像文件隔离 
*  通过挂载包含不同配置/数据文件的目录或者卷（Volume），单个App镜像可以同时用来运行无数个不同业务的容器 



## UnionFS

联合文件系统，分层，轻量级，高性能的文件系统，类似git，他支持对文件系统的修改作为一次次提交来一层层叠加，同时可以将不同目录挂载到同一个虚拟文件系统下。

联合文件系统会把隔层文件系统叠加起来，行程最终的文件系统包含所有文件和目录

UnionFS的Docker镜像的基础，镜像可以通过分层来进行继承，基于基础镜像来构建各种应用镜像。

**docker镜像的加载原理**

docker镜像实际由一层层的文件系统组成

最底层的bootfs包含bootloadwe和kernel

rootfs在boofs之上，宝蓝了典型Linux中的/etc,/dev,/bin等，rootfs就是不同的Linux发行版本

**为什么要使用UnionFS**

为了共享资源



## docker是什么



docker在一个隔绝的容器中运行进程，容器是宿主机上运行的一个进程，宿主机可以是本地的或者远程的。CS架构，docker的守护进程运行在宿主机上，接收客户端的命令并管理运行在宿主机上的容器。



# Image

镜像就像是一个类，通过类可以创建许多功能相同，属性不同的实例，而一个镜像也可以来创建多个容器。

镜像的本质就是一层层文件(联合文件)

 **查看本地镜像**

`docker images`

还可以根据需求添加参数

| 参数       | 说明                                             |
| ---------- | ------------------------------------------------ |
| -a         | 所有                                             |
| -q         | 只显示镜像id，可以与其他需要镜像ID的操作组合实用 |
| --digests  | 多显示一个镜像的摘要信息                         |
| --no-trunc | 显示完整的ID                                     |



**查找镜像**

`docker search image-name:tag`  去dockerhub中查找，tag默认=latest

也可以去dockerhub页面查完复制命令来拉取

| 参数       | 描述                       |
| ---------- | -------------------------- |
| -s 数字    | 只显示应用数量超过给定值的 |
| --no-trunc | 显示完整的说明             |



**拉取镜像**

`docker pull image-name:tag`

tag默认=latest



**删除镜像**

`docker rmi image-name:tag/image-id`，可以一次删除多个镜像

| 参数 | 描述                             |
| ---- | -------------------------------- |
| -f   | 运行中的镜像不能删除，-f强制删除 |

删除全部镜像

docker rmi -f $(docker images -aq)





# Container

运行docker run时，运行的容器进程是独立的，它有自己的文件系统，网络和独立于主机的进程树。



**启动容器**

`docker run [options] iamges-id/name [command] [args]`

本地没有改镜像是会去dockerhub中拉取(最新的)

参数

| 参数                      | 描述                                              |
| ------------------------- | ------------------------------------------------- |
| -it                       | 提供交互式终端，启动并进入容器/目录，返回交互界面 |
| -v  宿主机目录:容器目录   | 数据共享，目录不存在时会自动创建                  |
| -d                        | 后台运行，不进入容器                              |
| -p  宿主机端口:docker端口 | 端口映射                                          |
| -P                        | 随机端口映射                                      |
| --name                    | 为容器命名                                        |



**docker run -d后docker ps发现已经退出**

docker容器要后台运行，就必须有一个前台进程，如果没有一直挂起的命令(top,tail)，就会自动退出。即因为docker容器中没有命令运行就退出了



**查看容器**

| 命令                | 描述                   |
| ------------------- | ---------------------- |
| docker ps           | 查看正在运行的容器     |
| docker ps -a        | 查看所有的容器         |
| docker ps -l        | 查看上一个停止的容器   |
| docker ps -n 数字   | 显示上数字个停止的容器 |
| docker ps -q        | 只显示容器id           |
| --no-trunc          | 显示完整的ID           |
| docker top 容器     | 查看容器内的进程       |
| docker inspect 容器 | 查看容器描述           |



**停止容器**

| 命令             | 描述     |
| ---------------- | -------- |
| docker stop 容器 | 慢慢停止 |
| docker kill 容器 | 强制关闭 |



**退出容器**

| 命令     | 描述                                                     |
| -------- | -------------------------------------------------------- |
| exit     | 关闭并退出，如果是通过exec进入的容器，那他就只是退出容器 |
| ctrl+p+q | 只退出                                                   |



**重启容器**

`docker start ID`

`docker restart ID`



**重新进入容器**

两种方法：exec和attach

`docker exec -it 容器`

`docker attach -it 容器`

attach是进入到容器，然后执行命令

exec时进入到容器，执行命令，然后把执行结果返回到宿主机

`docker exec -it 容器 /bin/bash` = `docker attach -it 容器`



**查看容器日志**

`docker logs [options] 容器`

| 参数        | 描述             |
| ----------- | ---------------- |
| -t          | 加入时间戳       |
| -f          | 滚动             |
| --tail 数字 | 只显示最后多少条 |



**删除容器**

删除已经停止的容器

`docker rm 容器`，可以删除多个



| 参数 | 描述     |
| ---- | -------- |
| -f   | 强制停止 |



**拷贝容器内的文件**

宿主机拷贝到容器内：

`docker cp  文件 容器ID:目录`

容器内拷贝到宿主机：

`docker cp  容器id:文件 目录`



**提交容器**

提交一个容器，使之成为一个新的镜像

对一个容器进行了设计和修改，把它提交车给你一个新镜像，方便以后使用。

docker commit -m="描述" -a="作者" 容器ID 镜像名:tag



**导入导出容器**

`docker export 1e560fca3906 > test.tar

导出容器1e560fca3906 快照到本地文件 test.tar

可以使用 docker import 从容器快照文件中再导入为镜像



操作  命令  进入容器  docker exec -it 容器id /bin/bash  停止容器  docker stop 容器  重启容器  docker restart 容器  复制容器文件到宿主机  docker cp 容器:文件  宿主路径  复制文件到容器  docker cp 文件 容器:路径  查看容器内部日志  docker logs -f b容器  查看容器内的进程  dicker top  查看容器配置和状态信息  docker inspect 容器  查找镜像  search 名字:版本  修改容器名  docker rename 原名  新名字



## docker的网络模式

docker安装后默认会创建三种模式(bridge，host，none)，可以通过docker network ls查看，docker run时可以通过--net指定网络模式。

***bridge***

默认模式，docker启动时连接到网桥，为每个容器分配设置ip并连接到docker0虚拟网桥

***host***

容器和主机公用一个namespace，容器将不会虚拟出自己的网卡，配置自己的IP等，而是使用宿主机的IP和端口

***none***

Docker容器拥有自己的Network Namespace，但是没有进行任何网络设置(王桥连接，ip等)，保证容器安全性

***container***

新创建的容器和一个已经存在的容器公用一个namespace。新创建的容器不会创建自己的网卡，配置自己的 IP，而是和一个指定的容器共享 IP、端口范围





# DockerFile

用来描述镜像，就像是Java程序的Java文件，来创建镜像



dockerfile的解析过程(顺序)



保留字指令都需要大写，并且至少有一个参数，**每条指令都会创建一个新的镜像层**，并对镜像进行提交，最终生成一个镜像



| 保留字指令 | 作用                                                         |
| ---------- | ------------------------------------------------------------ |
| FROM       | 新创建的镜像基于那个镜像，  srcatch 相当于Java的Object，所有dockerfile的基类 |
| MAINTAINER | 作者<邮箱>                                                   |
| RUN        | 需要运行的命令                                               |
| EXPORT     | 暴露端口号                                                   |
| WORKDIR    | 指定容器纯构建后，终端登录后默认进入的工作目录，没有指定就是根目录 |
| ENV        | ENV key value 创建了一个环境变量，可以在后面通过$引用        |
| ADD        | 往镜像中添加文件，与copy一样，只不过add能解压缩              |
| COPY       | 将构建上下文目录中的文件/目录复制到新一层镜像中              |
| VOLUME     | 数据共享与持久化                                             |
| CMD        | 指定容器启动时要运行的命令，但只有最后一个生效               |
| ENTRYPOINT | 与CMD相同，只是他不会被覆盖，会变成追加                      |
| ONBUILD    |                                                              |

 

用来制作镜像的文本文件，包含了构建镜像所需要的指令和说明



文件名必须是Dockerfile





**构建镜像**

docker build

上下文路径是指在构建镜像的时候，有需要用到的本机文件。

docker 的运行模式是 C/S。我们本机是 C，docker 引擎是 S。实际的构建过程是在 docker 引擎下完成的，所以这个时候无法用到我们本机的文件。这就需要把我们本机的指定目录下的文件一起打包提供给 docker 引擎使用。

上下文路径下不要放无用的文件，因为会一起打包发送给 docker 引擎，如果文件过多会造成过程缓慢



将需要的文件复制到和DockerFile同目录下

FROM

该镜像基于什么镜像



RUN

用来执行后面跟着的任务，两种命令：shell和exec

docker命令每执行一次就会新建一层，不要曹成过多的无意义层



COPY

从上下文路径中赋值文件货目录到容器内指定路径

```shell
COPY [--chown=<user>:<group>] <源路径1>...  <目标路径>
COPY [--chown=<user>:<group>] ["<源路径1>",...  "<目标路径>"]
```

将上下文路径中的一个/多个文件复制到容器中的一个/多个目录中，容器内的目录不存在会新建



ADD

同样的需求下，官方建议用COPY

优点：赋值的文件是压缩文件时，会赋值名解压到指定的容器目录中

缺点：



CMD

与RUN类似，但运行时机不同，就像是讲台代码和构造函数

- CMD 在docker run 时运行。
- RUN 是在 docker build。

* 如果 Dockerfile 中如果存在多个 CMD 指令，仅最后一个生效
* 可被 docker run 命令行参数中指定要运行的程序所覆盖。



ENTRYPOINT

* 类似于 CMD 指令，但其不会被 docker run 的命令行参数指定的指令所覆盖，命令行参数会被当作参数送给 ENTRYPOINT 指令指定的程序。
* docker run 时使用了 --entrypoint 选项，将覆盖 CMD 指令指定的程序
* 仅最后一个生效



ENV

设置环境变量

```shell
ENV <key> <value>
ENV <key1>=<value1> <key2>=<value2>...
```



ARG

与ENV类似，但作用于不同，只在DockerFile构建时有效



VOLUME

匿名数据卷，启动容器忘记挂载数据卷就会自动挂载到匿名劵

- 避免重要的数据，因容器重启而丢失，这是非常致命的。
- 避免容器不断变大。



EXPOSE

声明端口



WORKDIR

指定工作目录。用 WORKDIR 指定的工作目录，会在构建镜像的每一层中都存在。



USER

切换后续命令的执行者



HEALTHCHECK

指定某个程序或者指令来监控docker服务运行状态



ONBUILD

用于延迟侯剑命令的延迟执行，在本次构建镜像的过程中不会执行，在FROM执行是才执行





# practice



## Redis集群



## ES



## Mongo



## MySQL



## SpringBoot

FROM java:8
COPY web-demo-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar



# interview



**Docker与虚拟机的区别，为什么docker启动快**

虚拟机模拟了整套硬件和操作系统，占用资源多。docker由更少的抽象层，而docker不需要Hypervisor实现硬件资源虚拟化，运行在docker上的城区都是直接使用的宿主机上的硬件资源和系统内核，不需要重新加载操作系统。



**为什么要用Docker**

保证环境和配置一致，保证系统，JDK，tomcat等版本一致，数据参数一致。不再只提交代码，而是联通环境和配置，打包成镜像

方便集群部署，扩容，升级

docker集装箱

将程序运行需要的环境都打包，方便交付，解决了环境的差异问题

docker起到环境隔离的作用并且开销比虚拟机小



**什么是容器数据卷**

干什么：有时需要将容器运行时产生的数据进行持久化，相当于硬盘来保存数据，用来数据共享和持久化

*容器内添加数据卷* ：

1. 直接用命令添加 docker run -it -v/宿主机绝对路径:/容器目录:[row权限]  镜像名   目录不存在时会新建，存在时会覆盖，默认权限时读写。宿主机和容器之间可以通过关联的目录进行**数据共享**，容器退出，重新启动该容器后，文件还在(**持久化**)  

2. DockerFile添加

   ```dockerfile
   volume[""容器卷1"，"容器卷2"...]
   ```



其他容器通过挂载这个容器来实现数据共享

docker run的时候添加 volumes-from 容器名



















# **仓库**



命令



**查看docker信息**

| 命令           | 描述     |
| -------------- | -------- |
| docker version | 查看版本 |
| docker info    | 查看具体 |



**帮助命令**

| 命令               | 描述             |
| ------------------ | ---------------- |
| docker             | 查看所有帮助命令 |
| docker 命令 --help | 查看具体命令     |

docker查看所有命令



docker 命令 --help，查看具体命令的帮助



**docker run**

docker run [options] image[:tag] [command] [arg...]

运行容器

进入容器

导入导出容器





**options**



**-d**

detached

是否在后台运行

这样当我们run创建容器进程然后退出时，它会在后台运行，除非指定了-rm

指定-rm 当容器退出时会移除容器

注意

使用-d时，不要将service xxx start命令 传递进容器

例如 

```shell
$ docker run -d -p 80:80 my_image service nginx start
```

这样会成功启动容器中的nginx服务，但是它会使得后台容器失败，



--name 设置标识

容器有三种标识：长UUID，短UUID，名字

UUID 标识符来自 Docker 守护进程。如果不使用 -- name 选项分配容器名称，则守护进程将为您生成一个随机字符串名称。定义名称可能是为容器增加意义的一种方便的方法。如果指定了名称，则可以在 Docker 网络中引用容器时使用它







































**容器与虚拟机**



容器使宿主机上运行的不同进程，虚拟机包括整个操作系统和应用程序

Containers are lightweight because they don’t need the extra load of a hypervisor but run directly within the host machine’s kernel.

hypervisor每个虚拟机中都需要运行一个操作系统和其中的大量软件，但实际生产开发环境里，我们更关注的是自己部署的应用程序，如果每次部署发布我都得搞一个完整操作系统和附带的依赖环境，那么这让任务和性能变得很重和很低下。

































































































































#### 修改挂载

**方法一**: **修改配置文件**

不推荐，需要停止docker服务

1. 停止docker服务  
2. 修改配置文件  vim /var/lib/docker/containers/container-ID/config.v2.json
3. 重启docker



方法二：**提交现有容器为新镜像，然后重新运行它**





# 镜像





联合文件系统：一种分层的文件系统。可以将不同的目录挂到同一个虚拟文件系统下.即可以同时看到多个目录下的文件

docker镜像系统的每一层都是只读的,然后把每一层加载完成之后这些文件都会被看成是同一个目录,相当于只有一个文件系统.docker的这种文件系统被称之为镜像.

并且最下面的n层都是只读的,只有上面一层是可写的



linux使用了命名空间来进行资源的隔离,比如pid namespace就是用来隔离进程的,mount namespace是用来隔离文件系统的,network namespace 是用来隔离网络的.每一个network namespace都提供了一个独立的网络环境,包括网卡路由iptable规则等等,都是与以其它的network space隔离的.





















































包含软件运行所需要的所有资源，

一次加载多个文件系统

***centos*** 镜像的为什么这么小？

共有内核，只提供。。。

***UnionFS*** 联合文件系统它支持 **对文件系统的修改作为一次提交，一层层来叠加**

联合文件系统是Docker镜像的基础，一次加载多个文件系统，对外只显示一个。镜像可以通过分层进行继承，制作各种具体的应用镜像，

* 镜像是只读的，当容器启动时，一个新的可写层会加载到镜像顶部，这一层就是容器层

***Docker镜像加载原理***

docker镜像实际是一层层的文件系统组成(称为联合文件系统)

* bootfs(boot file system)，主要包含bootlooder和kernel，bootloader主要用来阴道kernel加载。Linux启动时会加载bootfs，docker镜像的最底层就是bootfs，加载完内核后系统将bootfs卸载；
* rootfs，建立在bootfs之上，包含etc，bin，dev等标准文件盒目录，rootfs就像是linux的不同发行版本，内核相同，版本定制不一样；
* 之所以这么小是因为他们公用内核，只需要rootfs中基本命令，工具和程序库，底层直接实用宿主机的kernel，每一层的资源可以共享。
* 镜像不一定都比原始的小，因为可能还需要其他环境资源
