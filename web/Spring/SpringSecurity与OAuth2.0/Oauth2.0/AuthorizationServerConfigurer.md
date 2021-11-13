OAUth2 Authorization Server配置

启动类上添加@EnableAuthorizationServer来将该配置引入Spring容器



# AuthorizationServerSecurityConfigurer





# ClientDetailsServiceConfigurer



Note that password grant is not enabled.除非在AuthorizationServerEndpointsConfigurer启用了AuthenticationManager.



# AuthorizationServerEndpointsConfigurer



配置token的存储，自定义和grant types。

```
You shouldn't need to do anything by default, unless you need password grants, in which case you need to provide an AuthenticationManager.
```

