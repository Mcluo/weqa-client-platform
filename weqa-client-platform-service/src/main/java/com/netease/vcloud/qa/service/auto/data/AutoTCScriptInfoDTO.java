package com.netease.vcloud.qa.service.auto.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/16 20:28
 */
public class AutoTCScriptInfoDTO {

    private String scriptName ;

    private String scriptDetail ;

    private String execClass ;

    private String execMethod ;

    private String execParam ;

    private Long tcId ;

    private String owner ;

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

    public Long getTcId() {
        return tcId;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
