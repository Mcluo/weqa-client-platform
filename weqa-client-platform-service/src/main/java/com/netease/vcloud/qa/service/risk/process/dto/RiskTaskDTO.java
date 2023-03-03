package com.netease.vcloud.qa.service.risk.process.dto;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/10 11:52
 */
public class RiskTaskDTO {

    private Long projectId ;

    private String name ;
    /**
     * 任务链接，当前应该为jira链接
     */
    private String taskLink ;

    private List<String>  employeeList ;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskLink() {
        return taskLink;
    }

    public void setTaskLink(String taskLink) {
        this.taskLink = taskLink;
    }

    public List<String> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<String> employeeList) {
        this.employeeList = employeeList;
    }
}
