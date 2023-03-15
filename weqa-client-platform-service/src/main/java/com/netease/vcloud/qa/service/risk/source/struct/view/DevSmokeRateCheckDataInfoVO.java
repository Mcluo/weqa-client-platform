package com.netease.vcloud.qa.service.risk.source.struct.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/15 22:02
 */
public class DevSmokeRateCheckDataInfoVO implements CheckDataVOInterface{

    private int devTvCount ;

    private int testTvCount ;

    private double currentTvRate ;

    public int getDevTvCount() {
        return devTvCount;
    }

    public void setDevTvCount(int devTvCount) {
        this.devTvCount = devTvCount;
    }

    public int getTestTvCount() {
        return testTvCount;
    }

    public void setTestTvCount(int testTvCount) {
        this.testTvCount = testTvCount;
    }

    public double getCurrentTvRate() {
        return currentTvRate;
    }

    public void setCurrentTvRate(double currentTvRate) {
        this.currentTvRate = currentTvRate;
    }
}
