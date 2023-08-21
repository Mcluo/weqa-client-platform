package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:09
 */
public class ClientAutoBuildTagRelationDO {
    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private String buildArgs ;

    private String argsCondition ;

    private long tagId ;

    private String operator ;

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

    public String getBuildArgs() {
        return buildArgs;
    }

    public void setBuildArgs(String buildArgs) {
        this.buildArgs = buildArgs;
    }

    public String getArgsCondition() {
        return argsCondition;
    }

    public void setArgsCondition(String argsCondition) {
        this.argsCondition = argsCondition;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
