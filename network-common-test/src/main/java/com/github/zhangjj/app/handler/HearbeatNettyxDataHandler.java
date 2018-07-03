package com.github.zhangjj.app.handler;

import com.github.zhangjj.app.HearbeatBean;
import io.netty.channel.ChannelHandlerContext;
import io.nettyx.network.commons.data.NettyxDataHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangjj
 * @create 2017-11-22 15:57
 **/
@Slf4j
public class HearbeatNettyxDataHandler implements NettyxDataHandler<HearbeatBean> {

    @Override
    public void handler(ChannelHandlerContext ctx, HearbeatBean hearbeat) {
        log.info(hearbeat.toString());
    }
}
