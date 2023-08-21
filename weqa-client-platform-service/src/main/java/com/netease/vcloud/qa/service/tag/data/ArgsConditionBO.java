package com.netease.vcloud.qa.service.tag.data;

import com.netease.vcloud.qa.service.tag.ArgsOperate;
import com.netease.vcloud.qa.service.tag.ArgsType;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/17 11:58
 */
public class ArgsConditionBO {

    private  Long id  ;

    private  Long tagId  ;
    private String args ;

    /***以下三个值为封装参数****/
    private ArgsType type ;

    private ArgsOperate operate;

    private Object value ;

    private String operator ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public ArgsType getType() {
        return type;
    }

    public void setType(ArgsType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public ArgsOperate getOperate() {
        return operate;
    }

    public void setOperate(ArgsOperate operate) {
        this.operate = operate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
