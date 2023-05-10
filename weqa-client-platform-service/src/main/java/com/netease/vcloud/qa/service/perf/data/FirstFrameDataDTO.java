package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

public class FirstFrameDataDTO {

    private long taskId ;
    private String devices ;
    private String type ;
    private List<Long> data ;


    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }
}
