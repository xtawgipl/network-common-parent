# 基于netty的springboot通信插件
基于netty的springboot通信插件，springboot自动装配方式实现。
使用时服务端maven引用：

```
   <dependency>
       <groupId>io.nettyx</groupId>
       <artifactId>network-common-server-starter</artifactId>
   </dependency>
   ```
maven配置好后项目启动时会自动装配并启动nettyx服务端监听消息
客户端maven引用：
```
<dependency>
       <groupId>io.nettyx</groupId>
       <artifactId>network-common-client-starter</artifactId>
   </dependency>
   ```
application.yml文件中添加配置    
  ```
  nettyx:
         client:
             dataHandlerClass: com.github.zhangjj.app.handler.HearbeatNettyxDataHandler
         server:
             dataHandlerClass: com.github.zhangjj.app.handler.HearbeatNettyxDataHandler
             inetHost: 127.0.0.1
             inetPort: 8765
             readTimeout: 5
             writeTimeout: 0
             maxObjectSize: 1048576
                          
             ```
## network-common-test
nettyx实现的应用心跳使用示例
