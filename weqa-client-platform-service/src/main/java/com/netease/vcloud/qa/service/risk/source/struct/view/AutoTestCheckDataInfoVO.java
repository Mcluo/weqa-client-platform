package com.netease.vcloud.qa.service.risk.source.struct.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/1 11:49
 */
public class AutoTestCheckDataInfoVO implements CheckDataVOInterface {

    private int waitingNumber ;

    private int runningNumber ;
    private int successNumber ;

    private int failNumber ;

    private int cancelNumber ;

    private int total ;

    private String successRate ;

    private Long autoTaskId ;

    private String autoTaskName ;

    public int getWaitingNumber() {
        return waitingNumber;
    }

    public void setWaitingNumber(int waitingNumber) {
        this.waitingNumber = waitingNumber;
    }

    public int getRunningNumber() {
        return runningNumber;
    }

    public void setRunningNumber(int runningNumber) {
        this.runningNumber = runningNumber;
    }

    public int getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(int successNumber) {
        this.successNumber = successNumber;
    }

    public int getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(int failNumber) {
        this.failNumber = failNumber;
    }

    public int getCancelNumber() {
        return cancelNumber;
    }

    public void setCancelNumber(int cancelNumber) {
        this.cancelNumber = cancelNumber;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public Long getAutoTaskId() {
        return autoTaskId;
    }

    public void setAutoTaskId(Long autoTaskId) {
        this.autoTaskId = autoTaskId;
    }

    public String getAutoTaskName() {
        return autoTaskName;
    }

    public void setAutoTaskName(String autoTaskName) {
        this.autoTaskName = autoTaskName;
    }
}
