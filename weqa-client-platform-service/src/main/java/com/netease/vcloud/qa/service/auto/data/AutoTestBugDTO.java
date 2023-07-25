package com.netease.vcloud.qa.service.auto.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/25 15:23
 */
public class AutoTestBugDTO {

    private Long taskId ;

    private Long scriptRunId ;

    private String summary ;
    private String desc ;
    private String reporter ;
    private String checkUser ;
    private String handleUser ;
    private int priority ;

    private String version ;

    private String fixVersion ;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getScriptRunId() {
        return scriptRunId;
    }

    public void setScriptRunId(Long scriptRunId) {
        this.scriptRunId = scriptRunId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFixVersion() {
        return fixVersion;
    }

    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }
}
