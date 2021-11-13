###### AuthorizationServerTokenServices接口

有三个方法：

***createAccessToken***

通过指定的证书生成token

***refreshAccessToken***

刷新令牌

***getAccessToken***

根据提供的authentication key从数据库中获得存储获得token

***实现子类*** ：DefaultTokenServices



###### ResourceServerTokenServices 接口

两个方法：

***loadAuthentication***  

通过token加载认证信息

***readAccessToken***

获得token详情

***实现子类*** ：RemoteTokenServices、DefaultTokenServices





AuthorizationServerEndpointsConfigurer



token 的自定义存储



