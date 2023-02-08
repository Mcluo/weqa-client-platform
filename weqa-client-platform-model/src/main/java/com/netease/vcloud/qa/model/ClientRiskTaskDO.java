package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 15:26
 */
public class ClientRiskTaskDO {
    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private String taskName ;

    private long projectId ;

    private byte taskStatus ;

    private String jiraInfo ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(byte taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getJiraInfo() {
        return jiraInfo;
    }

    public void setJiraInfo(String jiraInfo) {
        this.jiraInfo = jiraInfo;
    }
}
