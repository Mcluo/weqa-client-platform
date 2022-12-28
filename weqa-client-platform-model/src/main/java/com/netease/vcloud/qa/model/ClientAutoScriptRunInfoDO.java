package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/9 22:03
 */
public class ClientAutoScriptRunInfoDO {
    /**
     * 主键KEY，可以自动生成
     */
    private long id ;

    /**
     *创建时间
     */
    private Date gmtCreate ;

    /**
     *最后修改时间
     */
    private Date gmtUpdate ;

    private long taskId ;

    private String scriptName ;

    private String scriptDetail ;

    private String execClass ;

    private String execMethod ;

    private String execParam ;

    private byte execStatus ;

    private Long runTime ;

    private String errorInfo ;

    private String logInfo ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getScriptDetail() {
        return scriptDetail;
    }

    public void setScriptDetail(String scriptDetail) {
        this.scriptDetail = scriptDetail;
    }

    public String getExecClass() {
        return execClass;
    }

    public void setExecClass(String execClass) {
        this.execClass = execClass;
    }

    public String getExecMethod() {
        return execMethod;
    }

    public void setExecMethod(String execMethod) {
        this.execMethod = execMethod;
    }

    public String getExecParam() {
        return execParam;
    }

    public void setExecParam(String execParam) {
        this.execParam = execParam;
    }

    public byte getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(byte execStatus) {
        this.execStatus = execStatus;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }
}
