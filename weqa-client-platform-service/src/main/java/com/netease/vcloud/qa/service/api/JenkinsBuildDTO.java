package com.netease.vcloud.qa.service.api;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/14 14:25
 */
public class JenkinsBuildDTO {

    private String android ;

    private String ios ;

    private String windows ;

    private String mac ;

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
