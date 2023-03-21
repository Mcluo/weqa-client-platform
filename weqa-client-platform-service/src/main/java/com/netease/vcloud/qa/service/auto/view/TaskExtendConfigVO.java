package com.netease.vcloud.qa.service.auto.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/21 14:49
 */
public class TaskExtendConfigVO {
    private String type ;

    private Long exId ;

    private String exConfig ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExId() {
        return exId;
    }

    public void setExId(Long exId) {
        this.exId = exId;
    }

    public String getExConfig() {
        return exConfig;
    }

    public void setExConfig(String exConfig) {
        this.exConfig = exConfig;
    }
}
