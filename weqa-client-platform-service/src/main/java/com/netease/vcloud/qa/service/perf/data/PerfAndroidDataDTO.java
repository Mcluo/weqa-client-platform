package com.netease.vcloud.qa.service.perf.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/29 15:52
 */
public class PerfAndroidDataDTO {

    private Float memory;

    private Float voltage;

    private Float temperature;

    private Float instantamperage;

    private Float power;

    private Float level;

    private Long times;

    private Integer taskid;

    private Float app_cpu;

    private Float cpu;

    private Float gpu ;

    public Float getMemory() {
        return memory;
    }

    public void setMemory(Float memory) {
        this.memory = memory;
    }

    public Float getVoltage() {
        return voltage;
    }

    public void setVoltage(Float voltage) {
        this.voltage = voltage;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getInstantamperage() {
        return instantamperage;
    }

    public void setInstantamperage(Float instantamperage) {
        this.instantamperage = instantamperage;
    }

    public Float getPower() {
        return power;
    }

    public void setPower(Float power) {
        this.power = power;
    }

    public Float getLevel() {
        return level;
    }

    public void setLevel(Float level) {
        this.level = level;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Float getApp_cpu() {
        return app_cpu;
    }

    public void setApp_cpu(Float app_cpu) {
        this.app_cpu = app_cpu;
    }

    public Float getCpu() {
        return cpu;
    }

    public void setCpu(Float cpu) {
        this.cpu = cpu;
    }

    public Float getGpu() {
        return gpu;
    }

    public void setGpu(Float gpu) {
        this.gpu = gpu;
    }
}
