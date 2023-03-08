package com.netease.vcloud.qa.service.perf.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 21:29
 */
public class PerfTaskIOSDetailListVO {
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
    private Float instantamperage;

    /**
     * 电量
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
}
