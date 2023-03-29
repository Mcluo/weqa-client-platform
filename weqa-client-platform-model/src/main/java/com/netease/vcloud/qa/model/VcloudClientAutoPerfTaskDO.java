package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientAutoPerfTaskDO implements Serializable {
    /**
     * 唯一ID
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 任务名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 测试用户名称
     *
     * @mbg.generated
     */
    private String user;

    /**
     * 设备平台
     *
     * @mbg.generated
     */
    private String devicesplatform;

    /**
     * 设备型号
     *
     * @mbg.generated
     */
    private String devicesmodel;

    /**
     * 设备版本
     *
     * @mbg.generated
     */
    private String devicesversion;

    /**
     * cpu信息
     *
     * @mbg.generated
     */
    private String cpuinfo;

    /**
     * sdk信息
     *
     * @mbg.generated
     */
    private String sdkinfo;

    /**
     * sdk版本
     *
     * @mbg.generated
     */
    private String sdkversion;

    private Date createTime;

    private Long autoTaskId ;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDevicesplatform() {
        return devicesplatform;
    }

    public void setDevicesplatform(String devicesplatform) {
        this.devicesplatform = devicesplatform;
    }

    public String getDevicesmodel() {
        return devicesmodel;
    }

    public void setDevicesmodel(String devicesmodel) {
        this.devicesmodel = devicesmodel;
    }

    public String getDevicesversion() {
        return devicesversion;
    }

    public void setDevicesversion(String devicesversion) {
        this.devicesversion = devicesversion;
    }

    public String getCpuinfo() {
        return cpuinfo;
    }

    public void setCpuinfo(String cpuinfo) {
        this.cpuinfo = cpuinfo;
    }

    public String getSdkinfo() {
        return sdkinfo;
    }

    public void setSdkinfo(String sdkinfo) {
        this.sdkinfo = sdkinfo;
    }

    public String getSdkversion() {
        return sdkversion;
    }

    public void setSdkversion(String sdkversion) {
        this.sdkversion = sdkversion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getAutoTaskId() {
        return autoTaskId;
    }

    public void setAutoTaskId(Long autoTaskId) {
        this.autoTaskId = autoTaskId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", user=").append(user);
        sb.append(", devicesplatform=").append(devicesplatform);
        sb.append(", devicesmodel=").append(devicesmodel);
        sb.append(", devicesversion=").append(devicesversion);
        sb.append(", cpuinfo=").append(cpuinfo);
        sb.append(", sdkinfo=").append(sdkinfo);
        sb.append(", sdkversion=").append(sdkversion);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}