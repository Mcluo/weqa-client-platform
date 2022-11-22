package com.netease.vcloud.qa.result.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 15:34
 */
public class DeviceInfoVO {

    private String ip ;

    private String port ;

    private String platform ;

    private String userId ;

    private String cpu ;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
}
