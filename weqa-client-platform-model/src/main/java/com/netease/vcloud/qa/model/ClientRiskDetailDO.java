package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 15:23
 */
public class ClientRiskDetailDO {
    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private long ruleId ;

    private String ruleName ;


    private byte rangeType;

    private long rangeId ;

    private String currentResult ;

    private byte hasRisk ;

    private String riskDetail;

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

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public byte getRangeType() {
        return rangeType;
    }

    public void setRangeType(byte rangeType) {
        this.rangeType = rangeType;
    }

    public long getRangeId() {
        return rangeId;
    }

    public void setRangeId(long rangeId) {
        this.rangeId = rangeId;
    }

    public String getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(String currentResult) {
        this.currentResult = currentResult;
    }

    public byte getHasRisk() {
        return hasRisk;
    }

    public void setHasRisk(byte hasRisk) {
        this.hasRisk = hasRisk;
    }

    public String getRiskDetail() {
        return riskDetail;
    }

    public void setRiskDetail(String riskDetail) {
        this.riskDetail = riskDetail;
    }
}
