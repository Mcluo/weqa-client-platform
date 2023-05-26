package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

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


    /**
     * 执行集ID
     * (根据性能测试执行集，触发对应性能测试)
     */
    private Long suitId ;

    private List<Long> deviceList;

    private String gitInfo ;

    private String gitBranch ;

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

    public Long getSuitId() {
        return suitId;
    }

    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }

    public List<Long> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Long> deviceList) {
        this.deviceList = deviceList;
    }

    public String getGitInfo() {
        return gitInfo;
    }

    public void setGitInfo(String gitInfo) {
        this.gitInfo = gitInfo;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }
}
