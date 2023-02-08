package com.netease.vcloud.qa.service.risk.manager.data;

import com.netease.vcloud.qa.risk.RiskCheckRange;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:52
 */
public class RiskDetailInfoBO {

    private Long id ;

    private Long ruleId ;

    private String ruleName ;

    private RiskCheckRange checkRage ;
    /**
     * 任务/项目id
     */
    private Long rangeId ;

    private String currentResult ;

    private boolean hasRisk ;

    private String riskDetail ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public RiskCheckRange getCheckRage() {
        return checkRage;
    }

    public void setCheckRage(RiskCheckRange checkRage) {
        this.checkRage = checkRage;
    }

    public Long getRangeId() {
        return rangeId;
    }

    public void setRangeId(Long rangeId) {
        this.rangeId = rangeId;
    }

    public String getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(String currentResult) {
        this.currentResult = currentResult;
    }

    public boolean isHasRisk() {
        return hasRisk;
    }

    public void setHasRisk(boolean hasRisk) {
        this.hasRisk = hasRisk;
    }

    public String getRiskDetail() {
        return riskDetail;
    }

    public void setRiskDetail(String riskDetail) {
        this.riskDetail = riskDetail;
    }
}
