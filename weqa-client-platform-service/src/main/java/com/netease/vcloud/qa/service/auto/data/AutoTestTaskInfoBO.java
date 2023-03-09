package com.netease.vcloud.qa.service.auto.data;

import com.netease.vcloud.qa.auto.TaskRunStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 14:10
 */
public class AutoTestTaskInfoBO {
    private Long id ;

    private String taskType ;

    private String taskName ;

    private Long testSuitId ;

    private String operator ;

    private byte deviceType ;
    private String deviceInfo ;

    private String gitInfo ;

    private String gitBranch ;

    private TaskRunStatus taskStatus ;

    private List<TaskScriptRunInfoBO> scriptList ;

    private Long privateAddressId ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTestSuitId() {
        return testSuitId;
    }

    public void setTestSuitId(Long testSuitId) {
        this.testSuitId = testSuitId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public TaskRunStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskRunStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<TaskScriptRunInfoBO> getScriptList() {
        return scriptList;
    }

    public void setScriptList(List<TaskScriptRunInfoBO> scriptList) {
        this.scriptList = scriptList;
    }

    public Long getPrivateAddressId() {
        return privateAddressId;
    }

    public void setPrivateAddressId(Long privateAddressId) {
        this.privateAddressId = privateAddressId;
    }
}
