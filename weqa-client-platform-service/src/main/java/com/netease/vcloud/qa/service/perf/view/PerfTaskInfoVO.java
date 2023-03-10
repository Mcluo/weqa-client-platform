package com.netease.vcloud.qa.service.perf.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 21:14
 */
public class PerfTaskInfoVO {

    private Long id;

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
    private UserInfoVO user;

    /**
     * 设备平台
     *
     * @mbg.generated
     */
    private String platform;

    /**
     * 设备型号
     *
     * @mbg.generated
     */
    private String model;

    /**
     * 设备版本
     *
     * @mbg.generated
     */
    private String platformVersion;

    /**
     * cpu信息
     *
     * @mbg.generated
     */
    private String cpuInfo;

    /**
     * sdk信息
     *
     * @mbg.generated
     */
    private String sdkInfo;

    /**
     * sdk版本
     *
     * @mbg.generated
     */
    private String sdkVersion;

    private long taskTime ;

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

    public UserInfoVO getUser() {
        return user;
    }

    public void setUser(UserInfoVO user) {
        this.user = user;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(String cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getSdkInfo() {
        return sdkInfo;
    }

    public void setSdkInfo(String sdkInfo) {
        this.sdkInfo = sdkInfo;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public long getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }
}
