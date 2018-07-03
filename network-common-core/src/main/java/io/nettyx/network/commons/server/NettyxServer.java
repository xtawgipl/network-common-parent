package io.nettyx.network.commons.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.nettyx.network.commons.data.NettyxDataHandler;
import io.nettyx.network.commons.properties.NettyxServerProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NettyxServer {

	private NettyxServerProperties nettyxServerProperties;

	private EventLoopGroup pGroup;
	private EventLoopGroup cGroup ;
	private ServerBootstrap boot;

	private ChannelFuture cf;

	private static class Option{
		public static String defaultIp = "127.0.0.1";
		public static Integer defaultMaxObjectSize = 1024 * 1024;
	}

	public NettyxServer(NettyxServerProperties nettyxServerProperties){
		log.info("NettyxServer init ...");
		this.pGroup = new NioEventLoopGroup();
		this.cGroup = new NioEventLoopGroup();
		this.boot = new ServerBootstrap();
		this.nettyxServerProperties = nettyxServerProperties;
	}

	public void start() throws InterruptedException {
		Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				String ip = nettyxServerProperties.getInetHost() == null ? Option.defaultIp : nettyxServerProperties.getInetHost();
				final Integer size = nettyxServerProperties.getMaxObjectSize() == null ? Option.defaultMaxObjectSize : nettyxServerProperties.getMaxObjectSize();
				boot.group(pGroup, cGroup)
						.channel(NioServerSocketChannel.class)
						.option(ChannelOption.SO_BACKLOG, 1024)
						.childHandler(new ChannelInitializer<SocketChannel>() {
							protected void initChannel(SocketChannel sc) throws Exception {
								//添加对象解码器 负责对序列化POJO对象进行解码 设置对象序列化最大长度为1M 防止内存溢出
								//设置线程安全的WeakReferenceMap对类加载器进行缓存 支持多线程并发访问  防止内存溢出
								sc.pipeline().addLast(new ObjectDecoder(size, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
								//添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
								sc.pipeline().addLast(new ObjectEncoder());
								if(nettyxServerProperties.getReadTimeout() > 0){
									sc.pipeline().addLast(new ReadTimeoutHandler(nettyxServerProperties.getReadTimeout()));
								}
								if(nettyxServerProperties.getWriteTimeout() > 0){
									sc.pipeline().addLast(new WriteTimeoutHandler(nettyxServerProperties.getWriteTimeout()));
								}
								sc.pipeline().addLast(new NettyxServerHandler(getDataHandler()));
							}
						});

				try {
					cf = boot.bind(ip, nettyxServerProperties.getInetPort()).sync();
					log.info(String.format("NettyxServer server started, lestening %s : %s", ip, nettyxServerProperties.getInetPort()));
					cf.channel().closeFuture().sync();
					shutdown();
				} catch (InterruptedException e) {
					log.error("", e);
				}
			}
		});
		serverThread.setName("nettyx-server-thread");
		serverThread.start();
	}

	private void shutdown(){
		log.warn("NettyxServer shutdown ...");
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	}

	private NettyxDataHandler getDataHandler() throws Exception {
		Class<?> aClass = this.getClass().getClassLoader().loadClass(nettyxServerProperties.getDataHandlerClass());
		return (NettyxDataHandler) aClass.newInstance();
	}
}
