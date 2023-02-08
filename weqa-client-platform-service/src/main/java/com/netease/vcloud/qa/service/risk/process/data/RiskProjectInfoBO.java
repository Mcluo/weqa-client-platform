package com.netease.vcloud.qa.service.risk.process.data;

import com.netease.vcloud.qa.risk.RiskProjectStatus;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:05
 */
public class RiskProjectInfoBO {

    private  Long id ;

    private String projectName ;

    private RiskProjectStatus status ;

    private String creator ;

    private Date  startTime ;

    private Date finishTime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public RiskProjectStatus getStatus() {
        return status;
    }

    public void setStatus(RiskProjectStatus status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
