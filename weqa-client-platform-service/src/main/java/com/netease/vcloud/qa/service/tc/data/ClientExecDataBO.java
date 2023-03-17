package com.netease.vcloud.qa.service.tc.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:35
 */
public class ClientExecDataBO {
    /**
     * tvID
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
