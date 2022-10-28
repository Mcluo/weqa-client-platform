package com.netease.vcloud.qa.service.tc.data;

/**
 * TC用例基本数据结构
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 21:16
 */
public class ClientTcData {
    /**
     * 项目ID
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
