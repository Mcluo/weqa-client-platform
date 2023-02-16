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

    private String checkInfo ;

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

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }
}
