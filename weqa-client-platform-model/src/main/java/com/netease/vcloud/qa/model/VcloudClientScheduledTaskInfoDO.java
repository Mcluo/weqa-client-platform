package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientScheduledTaskInfoDO implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * 最后修改时间
     *
     * @mbg.generated
     */
    private Date gmtUpdate;

    /**
     * 任务
     *
     * @mbg.generated
     */
    private String taskName;

    /**
     * 执行代码地址
     *
     * @mbg.generated
     */
    private String gitInfo;

    /**
     * 代码分支
     *
     * @mbg.generated
     */
    private String gitBranch;

    /**
     * 操作人(邮箱前缀)
     *
     * @mbg.generated
     */
    private String operator;

    /**
     * private_id
     *
     * @mbg.generated
     */
    private String privateId;

    /**
     * 定时任务状态
     *
     * @mbg.generated
     */
    private Byte taskStatus;

    /**
     * cron表达式
     *
     * @mbg.generated
     */
    private String cron;

    /**
     * 执行设备脚本id
     *
     * @mbg.generated
     */
    private String scriptIds;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPrivateId() {
        return privateId;
    }

    public void setPrivateId(String privateId) {
        this.privateId = privateId;
    }

    public Byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Byte taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getScriptIds() {
        return scriptIds;
    }

    public void setScriptIds(String scriptIds) {
        this.scriptIds = scriptIds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", taskName=").append(taskName);
        sb.append(", gitInfo=").append(gitInfo);
        sb.append(", gitBranch=").append(gitBranch);
        sb.append(", operator=").append(operator);
        sb.append(", privateId=").append(privateId);
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", cron=").append(cron);
        sb.append(", scriptIds=").append(scriptIds);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}