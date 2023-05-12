package com.netease.vcloud.qa.service.perf.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/9 17:51
 */
public class FirstFrameTaskDTO {

    private Long id ;
    /**
     * 任务
     */
    private String taskName ;
    /**
     * 操作人员
     */
    private String operator ;
    /**
     * 设备信息
     */
    private String deviceInfo ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
