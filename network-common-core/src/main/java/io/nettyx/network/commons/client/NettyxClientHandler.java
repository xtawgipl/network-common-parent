package io.nettyx.network.commons.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import io.nettyx.network.commons.data.NettyxDataHandler;

public class NettyxClientHandler extends ChannelHandlerAdapter{

	private NettyxDataHandler nettyxDataHandler;

	public NettyxClientHandler(NettyxDataHandler nettyxDataHandler){
		this.nettyxDataHandler = nettyxDataHandler;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			nettyxDataHandler.handler(ctx, msg);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}
