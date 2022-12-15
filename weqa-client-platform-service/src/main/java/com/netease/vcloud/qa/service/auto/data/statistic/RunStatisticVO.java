package com.netease.vcloud.qa.service.auto.data.statistic;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 15:35
 */
public class RunStatisticVO {

    private Integer total ;

    private Integer success ;

    private Integer fail ;

    private Double successRate ;

    public Integer getTotal() {
        return total;
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

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }
}
