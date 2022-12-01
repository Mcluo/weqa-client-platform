package com.netease.vcloud.qa.service.auto.view;

import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.result.view.UserInfoVO;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 14:57
 */
public class TaskBaseInfoVO {

    private Long id ;

    private String taskType ;

    private String taskName ;

    private String testSuit ;

    private UserInfoVO userInfo ;

    private String gitInfo ;

    private String branch ;

    private String status ;

    private Long startTime ;

    private List<DeviceInfoVO> deviceList ;

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

    public String getTestSuit() {
        return testSuit;
    }

    public void setTestSuit(String testSuit) {
        this.testSuit = testSuit;
    }


    public String getGitInfo() {
        return gitInfo;
    }

    public void setGitInfo(String gitInfo) {
        this.gitInfo = gitInfo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public List<DeviceInfoVO> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceInfoVO> deviceList) {
        this.deviceList = deviceList;
    }
}
