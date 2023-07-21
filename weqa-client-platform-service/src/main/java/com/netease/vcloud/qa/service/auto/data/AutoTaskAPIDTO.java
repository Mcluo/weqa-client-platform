package com.netease.vcloud.qa.service.auto.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/18 15:55
 */
public class AutoTaskAPIDTO {

    private String name;

    private String version ;

    private Long buildID ;

    private String buildGroupId ;

    private String script ;
    private String operator ;

    private JSONObject cropParameter ;

    private JSONObject url ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getBuildID() {
        return buildID;
    }

    public void setBuildID(Long buildID) {
        this.buildID = buildID;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBuildGroupId() {
        return buildGroupId;
    }

    public void setBuildGroupId(String buildGroupId) {
        this.buildGroupId = buildGroupId;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public JSONObject getCropParameter() {
        return cropParameter;
    }

    public void setCropParameter(JSONObject cropParameter) {
        this.cropParameter = cropParameter;
    }

    public JSONObject getUrl() {
        return url;
    }

    public void setUrl(JSONObject url) {
        this.url = url;
    }
}
