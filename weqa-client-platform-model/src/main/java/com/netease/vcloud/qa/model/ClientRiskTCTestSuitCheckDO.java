package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/2 16:35
 */
public class ClientRiskTCTestSuitCheckDO {

    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private byte rangeType ;

    private long riskRangeId;

    private Long tvID ;

    private Long projectID ;

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

    public Long getTvID() {
        return tvID;
    }

    public void setTvID(Long tvID) {
        this.tvID = tvID;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }
}
