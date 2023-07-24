package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientQsSceneDO implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private long id;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * 最后修改时间
     *
     * @mbg.generated
     */
    private Date gmtUpdate;

    private Long qsId;

    /**
     * 房间cid
     *
     * @mbg.generated
     */
    private String cid;

    /**
     * 执行标识，1为执行过，默认为0
     *
     * @mbg.generated
     */
    private Byte isRun;

    private static final long serialVersionUID = 1L;

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

    public Long getQsId() {
        return qsId;
    }

    public void setQsId(Long qsId) {
        this.qsId = qsId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Byte getIsRun() {
        return isRun;
    }

    public void setIsRun(Byte isRun) {
        this.isRun = isRun;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", qsId=").append(qsId);
        sb.append(", cid=").append(cid);
        sb.append(", isRun=").append(isRun);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}