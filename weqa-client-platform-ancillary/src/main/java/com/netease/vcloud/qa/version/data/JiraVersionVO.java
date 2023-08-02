package com.netease.vcloud.qa.version.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/31 21:38
 */
public class JiraVersionVO {

    private Long jiraId ;

    private String jiraName ;

    private String jiraKey ;

    private Long projectId  ;

    public Long getJiraId() {
        return jiraId;
    }

    public void setJiraId(Long jiraId) {
        this.jiraId = jiraId;
    }

    public String getJiraName() {
        return jiraName;
    }

    public void setJiraName(String jiraName) {
        this.jiraName = jiraName;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
