package com.github.zhangjj.app;

import java.io.Serializable;
import java.util.Date;

/**
 * 心跳bean
 *
 * @author zhangjj
 * @create 2017-12-18 17:15
 **/
public class HearbeatBean implements Serializable {

    private String appName;

    private Date date;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HearbeatBean{" +
                "appName='" + appName + '\'' +
                ", date=" + date +
                '}';
    }
}
