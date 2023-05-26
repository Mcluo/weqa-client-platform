package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/27 14:36
 */
public class AutoPerfTaskDTO {
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
    private String devicesPlatform;

    /**
     * 设备型号
     *
     * @mbg.generated
     */
    private String devicesModel;

    /**
     * 设备版本
     *
     * @mbg.generated
     */
    private String devicesVersion;

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

    /**
     * 执行集ID
     * (根据性能测试执行集，触发对应自动化测试)
     */
    private Long suitId ;

    private List<Long> deviceList;

    private String gitInfo ;

    private String gitBranch ;

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

    public String getDevicesPlatform() {
        return devicesPlatform;
    }

    public void setDevicesPlatform(String devicesPlatform) {
        this.devicesPlatform = devicesPlatform;
    }

    public String getDevicesModel() {
        return devicesModel;
    }

    public void setDevicesModel(String devicesModel) {
        this.devicesModel = devicesModel;
    }

    public String getDevicesVersion() {
        return devicesVersion;
    }

    public void setDevicesVersion(String devicesVersion) {
        this.devicesVersion = devicesVersion;
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

    public Long getSuitId() {
        return suitId;
    }

    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }

    public List<Long> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Long> deviceList) {
        this.deviceList = deviceList;
    }

    public String getGitInfo() {
        return gitInfo;
    }

    public void setGitInfo(String gitInfo) {
        this.gitInfo = gitInfo;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }
}
