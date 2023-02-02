package com.netease.vcloud.qa.service.auto.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/1 11:49
 */
public class TaskStatisticInfoVO {

    private int waitingNumber ;

    private int runningNumber ;
    private int successNumber ;

    private int failNumber ;

    private int cancelNumber ;

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
}
