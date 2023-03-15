package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * 冒烟测试执行情况
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 11:57
 */
public class ClientRiskSmokeRateDO {
    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private byte rangeType ;

    private long riskRangeId;

    private long developTVId ;

    private long qaTVId ;

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

    public byte getRangeType() {
        return rangeType;
    }

    public void setRangeType(byte rangeType) {
        this.rangeType = rangeType;
    }

    public long getRiskRangeId() {
        return riskRangeId;
    }

    public void setRiskRangeId(long riskRangeId) {
        this.riskRangeId = riskRangeId;
    }

    public long getDevelopTVId() {
        return developTVId;
    }

    public void setDevelopTVId(long developTVId) {
        this.developTVId = developTVId;
    }

    public long getQaTVId() {
        return qaTVId;
    }

    public void setQaTVId(long qaTVId) {
        this.qaTVId = qaTVId;
    }
}
