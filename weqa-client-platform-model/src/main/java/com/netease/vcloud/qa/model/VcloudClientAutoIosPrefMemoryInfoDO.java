package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientAutoIosPrefMemoryInfoDO implements Serializable {
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
     * appCpu
     *
     * @mbg.generated
     */
    private Float appCpu;

    /**
     * sysCpu
     *
     * @mbg.generated
     */
    private Float sysCpu;

    /**
     * 采集时间
     *
     * @mbg.generated
     */
    private Integer times;

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

    public Float getAppCpu() {
        return appCpu;
    }

    public void setAppCpu(Float appCpu) {
        this.appCpu = appCpu;
    }

    public Float getSysCpu() {
        return sysCpu;
    }

    public void setSysCpu(Float sysCpu) {
        this.sysCpu = sysCpu;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
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
        sb.append(", appCpu=").append(appCpu);
        sb.append(", sysCpu=").append(sysCpu);
        sb.append(", times=").append(times);
        sb.append(", taskid=").append(taskid);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}