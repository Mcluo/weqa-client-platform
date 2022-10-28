package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * 基础测试用例信息
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 15:36
 */
public class ClientTestCaseInfoDO {
    /**
     * id
     */
    private long id ;
    /**
     * 创建时间
     */
    private Date gmtCreate ;
    /**
     * 更新时间
     */
    private Date gmtUpdate ;
    /**
     * TC项目名
     */
    private Long projectId ;
    /**
     * TC测试用例ID
     */
    private Long caseId ;
    /**
     * TC测试用例名
     */
    private String caseName ;
    /**
     * TC实现步骤
     */
    private String executionSteps ;
    /**
     * TC期望结果
     */
    private String expectedResult ;

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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getExecutionSteps() {
        return executionSteps;
    }

    public void setExecutionSteps(String executionSteps) {
        this.executionSteps = executionSteps;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }
}
