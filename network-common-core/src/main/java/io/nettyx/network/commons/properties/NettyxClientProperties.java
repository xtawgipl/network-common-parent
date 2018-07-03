package io.nettyx.network.commons.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjj
 * @create 2017-11-21 10:41
 **/
@Configuration
@ConfigurationProperties(prefix="nettyx.client")
public class NettyxClientProperties {

    /** 服务器端监听 ip */
    private String inetHost;

    /** 服务器端监听 port*/
    private Integer inetPort;

    /** 消息处理类全名 */
    private String dataHandlerClass;

    /** 客户端端超时时间，读消息超时时间 超过该时间未读取到消息则服务器端主动断开连接（单位 秒），值小于等于0则表示 无限大*/
    private Integer readTimeout;

    /** 客户端端超时时间，写消息超时时间 超过该时间没有写消息则服务器端主动断开连接（单位 秒），值小于等于0则表示 无限大*/
    private Integer writeTimeout;

    /** 对象序列化的最大对象大小 byte*/
    private Integer maxObjectSize;

    public String getInetHost() {
        return inetHost;
    }

    public void setInetHost(String inetHost) {
        this.inetHost = inetHost;
    }

    public Integer getInetPort() {
        return inetPort;
    }

    public void setInetPort(Integer inetPort) {
        this.inetPort = inetPort;
    }

    public String getDataHandlerClass() {
        return dataHandlerClass;
    }

    public void setDataHandlerClass(String dataHandlerClass) {
        this.dataHandlerClass = dataHandlerClass;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Integer getMaxObjectSize() {
        return maxObjectSize;
    }

    public void setMaxObjectSize(Integer maxObjectSize) {
        this.maxObjectSize = maxObjectSize;
    }
}
