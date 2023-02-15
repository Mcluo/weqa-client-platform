package com.netease.vcloud.qa.service.risk.manager.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/15 11:40
 */
public class RiskDetailInfoVO {
    private Long riskId ;

    private String riskTitle ;

    private Long ruleId ;

    private String riskPriority ;

    /**
     * 通过标准
     */
    private String passStander ;

    /**
     * 当前值
     */
    private String currentValue ;

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

    public String getPassStander() {
        return passStander;
    }

    public void setPassStander(String passStander) {
        this.passStander = passStander;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}
