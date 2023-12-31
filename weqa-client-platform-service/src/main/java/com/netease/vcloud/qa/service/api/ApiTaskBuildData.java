package com.netease.vcloud.qa.service.api;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/8 10:55
 */
public class ApiTaskBuildData {

    private byte deviceType ;

    private List<Long> deviceList ;

    private String  urls ;

    public byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(byte deviceType) {
        this.deviceType = deviceType;
    }

    public List<Long> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Long> deviceList) {
        this.deviceList = deviceList;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
