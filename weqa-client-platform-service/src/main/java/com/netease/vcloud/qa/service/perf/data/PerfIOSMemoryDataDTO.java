package com.netease.vcloud.qa.service.perf.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/29 16:30
 */
public class PerfIOSMemoryDataDTO {
    /**
     * 内存
     *
     */
    private Float memory;

    /**
     * appCpu
     *
     */
    private Float appCpu;

    /**
     * sysCpu
     *
     */
    private Float sysCpu;
    /**
     * 采集时间
     *
     */
    private Long times;

    /**
     * 绑定taskId
     *
     */
    private Integer taskid;


    private Float deviceGPU ;

    private Float tilerGPU ;

    private Float renderGPU ;


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

    public Float getDeviceGPU() {
        return deviceGPU;
    }

    public void setDeviceGPU(Float deviceGPU) {
        this.deviceGPU = deviceGPU;
    }

    public Float getTilerGPU() {
        return tilerGPU;
    }

    public void setTilerGPU(Float tilerGPU) {
        this.tilerGPU = tilerGPU;
    }

    public Float getRenderGPU() {
        return renderGPU;
    }

    public void setRenderGPU(Float renderGPU) {
        this.renderGPU = renderGPU;
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
}
