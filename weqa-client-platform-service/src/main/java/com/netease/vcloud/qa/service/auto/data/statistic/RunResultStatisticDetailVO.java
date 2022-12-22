package com.netease.vcloud.qa.service.auto.data.statistic;

import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 15:19
 */
public class RunResultStatisticDetailVO {

    private String runningInfo ;

    private Long startTime ;

    private Long finishTime ;

    Map<String , Long>  errorInfoCountMap  ;

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

    public Map<String, Long> getErrorInfoCountMap() {
        return errorInfoCountMap;
    }

    public void setErrorInfoCountMap(Map<String, Long> errorInfoCountMap) {
        this.errorInfoCountMap = errorInfoCountMap;
    }
}
