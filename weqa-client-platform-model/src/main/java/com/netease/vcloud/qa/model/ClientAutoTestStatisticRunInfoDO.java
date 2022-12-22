package com.netease.vcloud.qa.model;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 17:36
 */
public class ClientAutoTestStatisticRunInfoDO {

    private String runInfo ;

    private Integer count ;

    private int success ;

    private int fail ;

    public String getRunInfo() {
        return runInfo;
    }

    public void setRunInfo(String runInfo) {
        this.runInfo = runInfo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }
}
