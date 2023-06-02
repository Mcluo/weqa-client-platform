package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * 自动化测试项目覆盖情况
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/2 17:45
 */
public class ClientTestCaseProjectCoverInfoDO {

    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    /**
     * 项目ID
     */
    private Long projectId ;
    /**
     * 任务ID
     */
    private Long taskId ;
    /**
     * 执行集ID
     */
    private Long TVId ;
    /**
     * 执行集内ID
     */
    private Long TestSuitId;
    /**
     * 原生TCID
     */
    private Long TestCaseId ;
    /**
     * 是否覆盖，0：否，1：是
     */
    private byte isCover ;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTVId() {
        return TVId;
    }

    public void setTVId(Long TVId) {
        this.TVId = TVId;
    }

    public Long getTestSuitId() {
        return TestSuitId;
    }

    public void setTestSuitId(Long testSuitId) {
        TestSuitId = testSuitId;
    }

    public Long getTestCaseId() {
        return TestCaseId;
    }

    public void setTestCaseId(Long testCaseId) {
        TestCaseId = testCaseId;
    }

    public byte getIsCover() {
        return isCover;
    }

    public void setIsCover(byte isCover) {
        this.isCover = isCover;
    }
}
