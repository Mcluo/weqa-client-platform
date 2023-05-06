package com.netease.vcloud.qa.service.auto.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 20:41
 */
public class AutoTestTaskInfoDTO {

    private String taskType ;

    private String taskName ;

    private String operator ;

    private byte deviceType ;
    private List<Long> deviceList;

    private String gitInfo ;

    private String gitBranch ;

    /**
     * 前端传递的时候，可以通过testSuit来触发
     */
    private Long testSuitId ;
    /**
     * 传递的时候，至传递id,不传递具体信息
     */
    private List<Long> testCaseScriptId ;
    /**
     * 私有地址配置
     */
    private Long privateAddressId ;
    /**
     * 关联项目ID
     */
    private Long projectId ;

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

    public Long getTestSuitId() {
        return testSuitId;
    }

    public void setTestSuitId(Long testSuitId) {
        this.testSuitId = testSuitId;
    }

    public List<Long> getTestCaseScriptId() {
        return testCaseScriptId;
    }

    public void setTestCaseScriptId(List<Long> testCaseScriptId) {
        this.testCaseScriptId = testCaseScriptId;
    }

    public Long getPrivateAddressId() {
        return privateAddressId;
    }

    public void setPrivateAddressId(Long privateAddressId) {
        this.privateAddressId = privateAddressId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
