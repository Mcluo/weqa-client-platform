package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientQsTaskDO implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private long id;

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
     * 设备类型0为本地设备，1为远程设备
     *
     * @mbg.generated
     */
    private Byte deviceType;

    /**
     * 执行设备信息
     *
     * @mbg.generated
     */
    private String deviceInfo;

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
     * 对应项目ID
     *
     * @mbg.generated
     */
    private Long projectId;

    /**
     * 开始时间
     *
     * @mbg.generated
     */
    private Date startTime;

    /**
     * 结束时间
     *
     * @mbg.generated
     */
    private Date endTime;

    /**
     * 用户场景id
     *
     * @mbg.generated
     */
    private Long qsAppId;

    /**
     * 抽样场景数
     *
     * @mbg.generated
     */
    private Integer sampleNum;

    /**
     * 典型场景数
     *
     * @mbg.generated
     */
    private Integer typicalSceneNum;

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

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getQsAppId() {
        return qsAppId;
    }

    public void setQsAppId(Long qsAppId) {
        this.qsAppId = qsAppId;
    }

    public Integer getSampleNum() {
        return sampleNum;
    }

    public void setSampleNum(Integer sampleNum) {
        this.sampleNum = sampleNum;
    }

    public Integer getTypicalSceneNum() {
        return typicalSceneNum;
    }

    public void setTypicalSceneNum(Integer typicalSceneNum) {
        this.typicalSceneNum = typicalSceneNum;
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
        sb.append(", deviceType=").append(deviceType);
        sb.append(", deviceInfo=").append(deviceInfo);
        sb.append(", operator=").append(operator);
        sb.append(", privateId=").append(privateId);
        sb.append(", projectId=").append(projectId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", qsAppId=").append(qsAppId);
        sb.append(", sampleNum=").append(sampleNum);
        sb.append(", typicalSceneNum=").append(typicalSceneNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}