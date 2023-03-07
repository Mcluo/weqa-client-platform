package com.netease.vcloud.qa.service.perf.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 16:40
 */
public class PerfTaskAndroidDetailListVO {
    /**
     * 内存
     */
    private Float memory;

    /**
     * 电压
     *
     * @mbg.generated
     */
    private Float voltage;

    /**
     * 温度
     *
     * @mbg.generated
     */
    private Float temperature;

    /**
     * 电流
     *
     * @mbg.generated
     */
    private Float instantAmperage;

    /**
     * 功率
     *
     * @mbg.generated
     */
    private Float power;

    /**
     * 当前电量百分比
     *
     * @mbg.generated
     */
    private Float level;

    /**
     * 采集时间
     *
     * @mbg.generated
     */
    private Long times;

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

    public Float getInstantAmperage() {
        return instantAmperage;
    }

    public void setInstantAmperage(Float instantAmperage) {
        this.instantAmperage = instantAmperage;
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
}
