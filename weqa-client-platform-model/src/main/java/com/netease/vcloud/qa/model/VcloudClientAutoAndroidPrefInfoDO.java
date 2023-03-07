package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientAutoAndroidPrefInfoDO implements Serializable {
    /**
     * 唯一ID
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 内存
     *
     * @mbg.generated
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
    private Float instantamperage;

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

    /**
     * 绑定taskId
     *
     * @mbg.generated
     */
    private Integer taskid;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memory=").append(memory);
        sb.append(", voltage=").append(voltage);
        sb.append(", temperature=").append(temperature);
        sb.append(", instantamperage=").append(instantamperage);
        sb.append(", power=").append(power);
        sb.append(", level=").append(level);
        sb.append(", times=").append(times);
        sb.append(", taskid=").append(taskid);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}