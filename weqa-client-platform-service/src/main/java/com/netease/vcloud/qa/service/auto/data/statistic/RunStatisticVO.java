package com.netease.vcloud.qa.service.auto.data.statistic;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 15:35
 */
public class RunStatisticVO {

    private String runInfo ;


    private String operator ;

    private String runIp ;

    private String branch ;

    private Integer total ;

    private Integer success ;

    private Integer fail ;

    private String successRate ;

    public Integer getTotal() {
        return total;
    }

    public String getRunInfo() {
        return runInfo;
    }

    public void setRunInfo(String runInfo) {
        this.runInfo = runInfo;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRunIp() {
        return runIp;
    }

    public void setRunIp(String runIp) {
        this.runIp = runIp;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
