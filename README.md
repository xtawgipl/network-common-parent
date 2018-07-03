# network-common-parent
基于netty的springboot通信插件
包装netty成更容易使用的通信组件，并支持springboot方式。
在springboot项目中只需要以下三步即可使用。
1. maven中添加network-common-core包依赖
2. 在application类增加nettyx包的扫描 `@ComponentScan(basePackages={"引用工程的包路径", "io.nettyx"})`   
3. application.yml文件中添加配置    
   >nettyx:    
   &nbsp;&nbsp;&nbsp;&nbsp;client:   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dataHandlerClass: com.github.zhangjj.app.handler.HearbeatNettyxDataHandler   
   &nbsp;&nbsp;&nbsp;&nbsp;server:   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dataHandlerClass: com.github.zhangjj.app.handler.HearbeatNettyxDataHandler   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inetHost: 127.0.0.1   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;inetPort: 8765   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;readTimeout: 5   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;writeTimeout: 0   
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;maxObjectSize: 1048576   
   
## network-common-test
nettyx实现的应用心跳使用示例
