package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * 测试用例执行情况
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 14:13
 */
public class ClientTestCaseExecDO {

    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    /**
     * 执行集ID
     */
    private long tvID ;

    /**
     * 运行总数
     */
    private int total ;

    /**
     * 成功数
     */
    private int accept ;

    /**
     * 失败数
     */
    private int fail ;

    /**
     * 忽略数
     */
    private int ignore ;

    /**
     * 未执行
     */
    private int unCarryOut ;

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

    public long getTvID() {
        return tvID;
    }

    public void setTvID(long tvID) {
        this.tvID = tvID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getIgnore() {
        return ignore;
    }

    public void setIgnore(int ignore) {
        this.ignore = ignore;
    }

    public int getUnCarryOut() {
        return unCarryOut;
    }

    public void setUnCarryOut(int unCarryOut) {
        this.unCarryOut = unCarryOut;
    }
}
