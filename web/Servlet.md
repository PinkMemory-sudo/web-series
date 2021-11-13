是什么？

​		运行在WEB服务器上的程序，用来处理HTTP请求。为每个HTTP请求编写一个servlet，再在web.xml重指定好他们的映射路径来处理请求



钩子函数：

​		初始化后调用init()

​		处理http请求时调用service()

​		销毁前调用destory()

init：

​		第一次访问时初始化，容器关闭时销毁

当用户调用一个 Servlet 时，就会创建一个 Servlet 实例，每一个用户请求都会产生一个新的线程，适当的时候移交给 doGet 或 doPost 方法

service

​		容器调用service来进行实际的处理。每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。service() 方法检查 HTTP 请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法。



servlet部署



获取请求参数



参数传递



转发与重定向

session过期

 session.invalidate();





​		

