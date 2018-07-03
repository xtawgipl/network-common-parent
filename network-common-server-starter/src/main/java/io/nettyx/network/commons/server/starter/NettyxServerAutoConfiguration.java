package io.nettyx.network.commons.starter;

import io.nettyx.network.commons.properties.NettyxServerProperties;
import io.nettyx.network.commons.server.NettyxServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * nettyx自动装配
 *
 * @author zhangjj
 * @create 2018-06-30 13:58
 **/
@Configuration
@EnableConfigurationProperties(NettyxServerProperties.class)
public class NettyxServerAutoConfiguration {

    @Autowired
    private NettyxServerProperties nettyxServerProperties;

    @Bean(name = "nettyxServer")
    public NettyxServer nettyxServerBean() throws InterruptedException {
        NettyxServer nettyxServer = new NettyxServer(nettyxServerProperties);
        nettyxServer.start();
        return nettyxServer;
    }


}
