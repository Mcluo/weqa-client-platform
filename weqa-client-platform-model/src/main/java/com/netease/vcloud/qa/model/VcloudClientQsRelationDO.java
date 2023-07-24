package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientQsRelationDO implements Serializable {
    private long id;

    private Date gmtCreate;

    private Date gmtUpdate;

    private Long autoTaskId;

    private Long qsTaskId;

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

    public Long getAutoTaskId() {
        return autoTaskId;
    }

    public void setAutoTaskId(Long autoTaskId) {
        this.autoTaskId = autoTaskId;
    }

    public Long getQsTaskId() {
        return qsTaskId;
    }

    public void setQsTaskId(Long qsTaskId) {
        this.qsTaskId = qsTaskId;
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
        sb.append(", autoTaskId=").append(autoTaskId);
        sb.append(", qsTaskId=").append(qsTaskId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}