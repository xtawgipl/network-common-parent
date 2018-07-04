package io.nettyx.network.commons.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.nettyx.network.commons.codec.KryoDecoder;
import io.nettyx.network.commons.codec.KryoEncoder;
import io.nettyx.network.commons.data.NettyxDataHandler;
import io.nettyx.network.commons.properties.NettyxClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangjj
 * @create 2017-11-21 10:38
 **/
@Slf4j
public class NettyxClient {

    private NettyxClientProperties nettyxClientProperties;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private ChannelFuture cf ;

    private static class Option{
        public static Integer defaultMaxObjectSize = 1024 * 1024;
    }

    public NettyxClient(NettyxClientProperties nettyxClientProperties){
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.nettyxClientProperties = nettyxClientProperties;
    }

    public void start(){
        final Integer size = nettyxClientProperties.getMaxObjectSize() == null ? Option.defaultMaxObjectSize : nettyxClientProperties.getMaxObjectSize();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //添加POJO对象解码器 禁止缓存类加载器
                        sc.pipeline().addLast(new KryoDecoder(size));
                        //设置发送消息编码器
                        sc.pipeline().addLast(new KryoEncoder());
                        if(nettyxClientProperties.getReadTimeout() > 0){
                            sc.pipeline().addLast(new ReadTimeoutHandler(nettyxClientProperties.getReadTimeout()));
                        }
                        if(nettyxClientProperties.getWriteTimeout() > 0){
                            sc.pipeline().addLast(new WriteTimeoutHandler(nettyxClientProperties.getWriteTimeout()));
                        }
                        sc.pipeline().addLast(new NettyxClientHandler(getDataHandler()));
                    }
                });
    }

    private NettyxDataHandler getDataHandler() throws Exception {
        Class<?> aClass = this.getClass().getClassLoader().loadClass(this.nettyxClientProperties.getDataHandlerClass());
        return (NettyxDataHandler) aClass.newInstance();
    }

    private void connect() throws InterruptedException {
        this.cf = bootstrap.connect(nettyxClientProperties.getInetHost(), nettyxClientProperties.getInetPort()).sync();
        log.info(String.format("connected to %s : %s ", nettyxClientProperties.getInetHost(), nettyxClientProperties.getInetPort()));
    }

    private ChannelFuture getChannelFuture() throws InterruptedException {
        if(this.cf == null || !this.cf.channel().isActive()){
            this.connect();
        }
        return this.cf;
    }

    public void sendData(Object msg) throws InterruptedException {
        this.getChannelFuture();
        this.cf.channel().writeAndFlush(msg);
    }

    public void closeFuture() throws InterruptedException{
        this.cf.channel().closeFuture().sync();
    }
}
