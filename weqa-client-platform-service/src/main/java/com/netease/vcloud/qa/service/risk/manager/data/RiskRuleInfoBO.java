package com.netease.vcloud.qa.service.risk.manager.data;

import com.netease.vcloud.qa.risk.RiskCheckRange;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:36
 */
public class RiskRuleInfoBO {

    private Long id ;

    private String ruleName ;

    private RiskCheckRange range ;

    private byte stage ;

    /**
     * 优先级，常量在RiskRulePriority
     */
    private String priority ;

    /**
     * 通过标注
     */
    private RiskCheckStander checkStander;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public RiskCheckRange getRange() {
        return range;
    }

    public void setRange(RiskCheckRange range) {
        this.range = range;
    }

    public byte getStage() {
        return stage;
    }

    public void setStage(byte stage) {
        this.stage = stage;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public RiskCheckStander getCheckStander() {
        return checkStander;
    }

    public void setCheckStander(RiskCheckStander checkStander) {
        this.checkStander = checkStander;
    }
}
