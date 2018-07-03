package com.github.zhangjj.app;

import io.nettyx.network.commons.client.NettyxClient;
import io.nettyx.network.commons.properties.NettyxClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 心跳发送端(客户端)
 *
 * @author zhangjj
 * @create 2017-12-18 16:38
 **/
public class ClientTest {

    private static Logger logger = LoggerFactory.getLogger(ClientTest.class);

    public static void main(String[] args) throws InterruptedException {
        NettyxClientProperties properties = new NettyxClientProperties();
        properties.setDataHandlerClass("com.github.zhangjj.app.handler.HearbeatNettyxDataHandler");
        properties.setInetHost("127.0.0.1");
        properties.setInetPort(8765);
        properties.setMaxObjectSize(1024 * 1024);
        properties.setReadTimeout(0);
        properties.setWriteTimeout(5);
        NettyxClient nettyxClient = new NettyxClient(properties);
        nettyxClient.start();

        try {
            HearbeatBean hearbeatBean = new HearbeatBean();
            hearbeatBean.setAppName("客户端应用程序a");
            while (true){
                hearbeatBean.setDate(new Date());
                nettyxClient.sendData(hearbeatBean);
                Thread.sleep(2000);
            }
        }catch (Exception e){
            logger.error("", e);
        }finally {
            nettyxClient.closeFuture();
        }
    }
}
