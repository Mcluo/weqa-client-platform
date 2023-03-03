package com.netease.vcloud.qa.service.risk.process.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/15 11:09
 */
public class RiskTaskDetailVO {

    private long id ;

    private String taskName ;

    private List<UserInfoVO> userList ;

    private String status ;

    private Long projectId ;

    private String projectName ;

    private String linkUrl ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<UserInfoVO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfoVO> userList) {
        this.userList = userList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
