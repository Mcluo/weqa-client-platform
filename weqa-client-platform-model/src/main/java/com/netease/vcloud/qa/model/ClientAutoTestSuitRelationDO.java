package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/8 11:33
 */
public class ClientAutoTestSuitRelationDO {
    /**
     * 主键KEY，可以自动生成
     */
    private long id ;

    /**
     *创建时间
     */
    private Date gmtCreate ;

    /**
     *最后修改时间
     */
    private Date gmtUpdate ;
    /**
     * 测试集id
     */
    private long suitId ;
    /**
     * 脚本名
     */
    private long scriptId ;

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

    public long getSuitId() {
        return suitId;
    }

    public void setSuitId(long suitId) {
        this.suitId = suitId;
    }

    public long getScriptId() {
        return scriptId;
    }

    public void setScriptId(long scriptId) {
        this.scriptId = scriptId;
    }
}
