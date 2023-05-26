package com.netease.vcloud.qa.service.perf.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/24 10:08
 */
public class PerfReportResultDetailData {
    /**
     * 任务ID
     */
    private Long taskId ;

    /**
     * 系统
     */
    private String platform ;
    /**
     * 设备信息
     */
    private String deviceInfo ;


    /**
     * 内存
     */
    private PerfTestReportData memory;

    /**
     * 电压
     *
     * @mbg.generated
     */
    private PerfTestReportData voltage;

    /**
     * 温度
     *
     * @mbg.generated
     */
    private PerfTestReportData temperature;

    /**
     * 电流
     *
     * @mbg.generated
     */
    private PerfTestReportData instantAmperage;

    /**
     * 功率
     *
     * @mbg.generated
     */
    private PerfTestReportData power;

    /**
     * 当前电量百分比
     *
     * @mbg.generated
     */
    private PerfTestReportData level;

    /**
     * appCpu
     *
     * @mbg.generated
     */
    private PerfTestReportData appCpu;

    /**
     * sysCpu
     *
     * @mbg.generated
     */
    private PerfTestReportData sysCpu;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

//    public PerfTestReportData getData() {
//        return data;
//    }
//
//    public void setData(PerfTestReportData data) {
//        this.data = data;
//    }

    public PerfTestReportData getMemory() {
        return memory;
    }

    public void setMemory(PerfTestReportData memory) {
        this.memory = memory;
    }

    public PerfTestReportData getVoltage() {
        return voltage;
    }

    public void setVoltage(PerfTestReportData voltage) {
        this.voltage = voltage;
    }

    public PerfTestReportData getTemperature() {
        return temperature;
    }

    public void setTemperature(PerfTestReportData temperature) {
        this.temperature = temperature;
    }

    public PerfTestReportData getInstantAmperage() {
        return instantAmperage;
    }

    public void setInstantAmperage(PerfTestReportData instantAmperage) {
        this.instantAmperage = instantAmperage;
    }

    public PerfTestReportData getPower() {
        return power;
    }

    public void setPower(PerfTestReportData power) {
        this.power = power;
    }

    public PerfTestReportData getLevel() {
        return level;
    }

    public void setLevel(PerfTestReportData level) {
        this.level = level;
    }

    public PerfTestReportData getAppCpu() {
        return appCpu;
    }

    public void setAppCpu(PerfTestReportData appCpu) {
        this.appCpu = appCpu;
    }

    public PerfTestReportData getSysCpu() {
        return sysCpu;
    }

    public void setSysCpu(PerfTestReportData sysCpu) {
        this.sysCpu = sysCpu;
    }
}
