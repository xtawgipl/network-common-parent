package io.nettyx.network.commons.starter;

import io.nettyx.network.commons.client.NettyxClient;
import io.nettyx.network.commons.properties.NettyxClientProperties;
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
@EnableConfigurationProperties(NettyxClientProperties.class)
public class NettyxClientAutoConfiguration {

    @Autowired
    private NettyxClientProperties nettyxClientProperties;

    @Primary
    @Bean(name = "nettyxClient")
    public NettyxClient nettyxClientBean() throws InterruptedException {
        NettyxClient nettyxClient = new NettyxClient(nettyxClientProperties);
        return nettyxClient;
    }


}
