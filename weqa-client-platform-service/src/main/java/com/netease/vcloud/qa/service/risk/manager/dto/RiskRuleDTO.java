package com.netease.vcloud.qa.service.risk.manager.dto;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/16 20:18
 */
public class RiskRuleDTO {

    private String range ;

    private String status ;

    private String name ;

    private String priority ;

    private String ruleType ;

    private String ruleDesc ;

    private String ruleCheckInfo;

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleCheckInfo() {
        return ruleCheckInfo;
    }

    public void setRuleCheckInfo(String ruleCheckInfo) {
        this.ruleCheckInfo = ruleCheckInfo;
    }
}
