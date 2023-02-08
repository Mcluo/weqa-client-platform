package com.netease.vcloud.qa.service.risk.process.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 17:49
 */
public class RiskProjectVO {

    private String projectName ;

    private Long startTime  ;

    private Long finishTime ;

    private String status ;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
