package com.netease.vcloud.qa.service.risk.source.struct.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/5 19:47
 */
public class TCTestSuitCoveredDetailVO {

    private Long testSuidId ;

    private Long testCaseId ;

    private String name ;

    private int priority ;

    private int result ;

    private boolean isCovered ;

    public Long getTestSuidId() {
        return testSuidId;
    }

    public void setTestSuidId(Long testSuidId) {
        this.testSuidId = testSuidId;
    }

    public Long getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Long testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }
}
