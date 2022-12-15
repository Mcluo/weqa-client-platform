package com.netease.vcloud.qa.service.auto.data.statistic;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 15:18
 */
public class RunResultStatisticInfoVO {

    private String runningInfo ;

    private Long startTime ;

    private Long finishTime ;

    private List<RunStatisticVO>  runStatisticList ;

    public String getRunningInfo() {
        return runningInfo;
    }

    public void setRunningInfo(String runningInfo) {
        this.runningInfo = runningInfo;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public List<RunStatisticVO> getRunStatisticList() {
        return runStatisticList;
    }

    public void setRunStatisticList(List<RunStatisticVO> runStatisticList) {
        this.runStatisticList = runStatisticList;
    }
}
