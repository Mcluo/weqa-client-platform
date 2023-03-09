package com.netease.vcloud.qa.service.risk.manager.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/10 16:16
 */
public class RiskBaseInfoVO {

    private Long riskId ;

    private String riskTitle ;

    private Long ruleId ;

    private String riskPriority ;

    /*********以下项目中展示的时候需要使用**********/

    private Long taskId ;

    private String taskName ;

    public Long getRiskId() {
        return riskId;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public String getRiskTitle() {
        return riskTitle;
    }

    public void setRiskTitle(String riskTitle) {
        this.riskTitle = riskTitle;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRiskPriority() {
        return riskPriority;
    }

    public void setRiskPriority(String riskPriority) {
        this.riskPriority = riskPriority;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
