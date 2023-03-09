package com.netease.vcloud.qa.service.risk.process.data;

import com.netease.vcloud.qa.risk.RiskTaskStatus;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:06
 */
public class RiskTaskInfoBO {

    private long id ;

    private String taskName ;

    private Long taskId ;

    private List<RiskTaskPersonBO> personList ;

    private RiskTaskStatus status ;

    private String jiraInfo ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public List<RiskTaskPersonBO> getPersonList() {
        return personList;
    }

    public void setPersonList(List<RiskTaskPersonBO> personList) {
        this.personList = personList;
    }

    public RiskTaskStatus getStatus() {
        return status;
    }

    public void setStatus(RiskTaskStatus status) {
        this.status = status;
    }

    public String getJiraInfo() {
        return jiraInfo;
    }

    public void setJiraInfo(String jiraInfo) {
        this.jiraInfo = jiraInfo;
    }
}
