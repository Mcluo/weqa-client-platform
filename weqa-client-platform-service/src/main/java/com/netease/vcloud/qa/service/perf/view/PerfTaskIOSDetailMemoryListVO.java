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
}
