package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 15:26
 */
public class ClientRiskRuleDO{
    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private String ruleName ;

    private byte checkRange ;

    private byte checkStage ;

    private String checkPriority ;

    private String checkInfo ;

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

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public byte getCheckRange() {
        return checkRange;
    }

    public void setCheckRange(byte checkRange) {
        this.checkRange = checkRange;
    }

    public byte getCheckStage() {
        return checkStage;
    }

    public void setCheckStage(byte checkStage) {
        this.checkStage = checkStage;
    }

    public String getCheckPriority() {
        return checkPriority;
    }

    public void setCheckPriority(String checkPriority) {
        this.checkPriority = checkPriority;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }
}
