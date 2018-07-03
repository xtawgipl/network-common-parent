package io.nettyx.network.commons.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.nettyx.network.commons.data.NettyxDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyxServerHandler extends ChannelHandlerAdapter{

	private Logger logger = LoggerFactory.getLogger(NettyxServerHandler.class);

	private NettyxDataHandler nettyxDataHandler;

	public NettyxServerHandler(NettyxDataHandler nettyxDataHandler){
		this.nettyxDataHandler = nettyxDataHandler;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("NettyxServerHandler channelActive ...");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		this.nettyxDataHandler.handler(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info("NettyxServerHandler channelReadComplete ...");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("NettyxServerHandler exceptionCaught ...", cause);
		ctx.close();
	}

}
