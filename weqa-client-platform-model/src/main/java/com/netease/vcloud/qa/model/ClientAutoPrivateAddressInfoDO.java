package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/1 17:57
 */
public class ClientAutoPrivateAddressInfoDO {
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
    /**
     * 私有地址配置名
     */
    private String name ;
    /**
     * 具体配置信息
     */
    private String config;


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
