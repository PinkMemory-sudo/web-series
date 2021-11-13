加载资源的策略接口(class path or file system resources)

* getResource()方法，获得指定路径的Resource类，获得系统文件字符串前加file: 获得类路径下的文件夹classpath：
* getClassLoader，获得类加载器

**Resource类**

Spring中用来抽象的描述file，类路径下的资源

就把他当成File吧，因为它的方法File也有类似的

Resource中的方法

* exists
* isFile
* getFilename
* isReadable
* isOpen
* getDescription 返回权限定文件名或者URL
* getFile
* contentLength返回字节数
* readableChannel
* lastModified  上次修改时间(long)
* createRelative(String relativePath)创建一个相对于此Resource的一个Resource



