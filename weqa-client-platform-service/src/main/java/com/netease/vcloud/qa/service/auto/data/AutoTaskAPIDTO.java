package com.netease.vcloud.qa.service.auto.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/18 15:55
 */
public class AutoTaskAPIDTO {

    private String name;

    private String version ;

    private Long buildID ;

    private Long buildGroupID ;

    private String script ;
    private String operator ;

    private String cropParameter ;

    private String url ;

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

    public Long getBuildGroupID() {
        return buildGroupID;
    }

    public void setBuildGroupID(Long buildGroupID) {
        this.buildGroupID = buildGroupID;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getCropParameter() {
        return cropParameter;
    }

    public void setCropParameter(String cropParameter) {
        this.cropParameter = cropParameter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
