package com.netease.vcloud.qa.service.auto.data.statistic;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 15:35
 */
public class RunStatisticVO {

    private String runningInfo ;

    private Integer total ;

    private Integer success ;

    private Integer fail ;

    private String successRate ;

    public Integer getTotal() {
        return total;
    }

    public String getRunningInfo() {
        return runningInfo;
    }

    public void setRunningInfo(String runningInfo) {
        this.runningInfo = runningInfo;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }
}
