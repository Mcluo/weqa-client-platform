package com.netease.vcloud.qa.service.perf.view;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 21:30
 */
public class PerfTaskIOSDetailMemoryListVO {
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
    private Long times;

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

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
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
}
