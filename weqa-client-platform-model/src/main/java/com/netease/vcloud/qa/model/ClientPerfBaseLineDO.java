package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:20
 */
public class ClientPerfBaseLineDO {
    /**
     * id
     */
    private Long id ;

    /**
     *创建时间
     */
    private Date gmtCreate ;

    /**
     *最后修改时间
     */
    private Date gmtUpdate ;
    /**
     * 基线
     */
    private String baseLineName ;
    /**
     * 基线类型
     */
    private byte baseLineType ;
    /**
     * 归属人
     */
    private String owner ;
    /**
     * 结果
     */
    private String resultData ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getBaseLineName() {
        return baseLineName;
    }

    public void setBaseLineName(String baseLineName) {
        this.baseLineName = baseLineName;
    }

    public byte getBaseLineType() {
        return baseLineType;
    }

    public void setBaseLineType(byte baseLineType) {
        this.baseLineType = baseLineType;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
