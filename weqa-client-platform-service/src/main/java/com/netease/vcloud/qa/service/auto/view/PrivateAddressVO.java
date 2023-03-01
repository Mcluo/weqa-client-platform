package com.netease.vcloud.qa.service.auto.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/1 20:07
 */
public class PrivateAddressVO {
    private  Long  id ;

    private String name ;

    private String config ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
