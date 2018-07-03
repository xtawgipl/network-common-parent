package io.nettyx.network.commons.data;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

/**
 * @author zhangjj
 * @create 2017-11-20 18:03
 **/
public interface NettyxDataHandler<T> extends Serializable {

    void handler(ChannelHandlerContext ctx, T msg);

}
