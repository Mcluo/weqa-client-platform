package com.netease.vcloud.qa.service.auto.data.statistic;

import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 15:16
 */
public class RunSummerInfoVO {

    private RunStatisticVO week ;

    private RunStatisticVO month ;

//    private RunStatisticVO year ;
    private Map<String,Long> runInfo ;

    public RunStatisticVO getWeek() {
        return week;
    }

    public void setWeek(RunStatisticVO week) {
        this.week = week;
    }

    public RunStatisticVO getMonth() {
        return month;
    }

    public void setMonth(RunStatisticVO month) {
        this.month = month;
    }

    public Map<String, Long> getRunInfo() {
        return runInfo;
    }

    public void setRunInfo(Map<String, Long> runInfo) {
        this.runInfo = runInfo;
    }
}
