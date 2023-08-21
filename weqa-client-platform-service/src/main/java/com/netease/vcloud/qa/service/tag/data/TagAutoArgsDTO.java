package com.netease.vcloud.qa.service.tag.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/17 11:12
 */
public class TagAutoArgsDTO {


    private  Long id  ;
    /**
     * 参数名称
     */
    private String buildArgs ;

    /**
     * 匹配条件
     */
    private JSONObject argsCondition ;

    /**
     * 标签ID
     */
    private Long tagId ;
    /**
     * 操作人
     */
    private String operator ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuildArgs() {
        return buildArgs;
    }

    public void setBuildArgs(String buildArgs) {
        this.buildArgs = buildArgs;
    }

    public JSONObject getArgsCondition() {
        return argsCondition;
    }

    public void setArgsCondition(JSONObject argsCondition) {
        this.argsCondition = argsCondition;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
