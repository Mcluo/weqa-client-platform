package com.netease.vcloud.qa.service.auto.data;

import com.netease.vcloud.qa.auto.ScriptRunStatus;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/10 11:26
 */
public class TaskScriptRunInfoBO {

    private Long scriptId;

    private Long taskId ;

//    private String git ;

    private String devicesInfo ;

    private String scriptName ;

    private String scriptDetail ;

    private String execClass ;

    private String execMethod ;

    private String execParam ;

    private ScriptRunStatus execStatus ;

    public Long getScriptId() {
        return scriptId;
    }

    public void setScriptId(Long scriptId) {
        this.scriptId = scriptId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDevicesInfo() {
        return devicesInfo;
    }

    public void setDevicesInfo(String devicesInfo) {
        this.devicesInfo = devicesInfo;
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

    public ScriptRunStatus getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(ScriptRunStatus execStatus) {
        this.execStatus = execStatus;
    }
}
