package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 15:51
 */
public class PerfTaskIOSDetailVO implements PerfTaskDetailInterface{

    private List<PerfTaskIOSDetailListVO> detailList ;

    private List<PerfTaskIOSDetailMemoryListVO> memoryDetailList ;

    private PerfTaskStatisticVO memory ;

    private PerfTaskStatisticVO voltage ;

    private PerfTaskStatisticVO temperature ;

    private PerfTaskStatisticVO instantAmperage ;

    private PerfTaskStatisticVO power ;

    private PerfTaskStatisticVO level ;

    public List<PerfTaskIOSDetailListVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PerfTaskIOSDetailListVO> detailList) {
        this.detailList = detailList;
    }

    public List<PerfTaskIOSDetailMemoryListVO> getMemoryDetailList() {
        return memoryDetailList;
    }

    public void setMemoryDetailList(List<PerfTaskIOSDetailMemoryListVO> memoryDetailList) {
        this.memoryDetailList = memoryDetailList;
    }

    public PerfTaskStatisticVO getMemory() {
        return memory;
    }

    public void setMemory(PerfTaskStatisticVO memory) {
        this.memory = memory;
    }

    public PerfTaskStatisticVO getVoltage() {
        return voltage;
    }

    public void setVoltage(PerfTaskStatisticVO voltage) {
        this.voltage = voltage;
    }

    public PerfTaskStatisticVO getTemperature() {
        return temperature;
    }

    public void setTemperature(PerfTaskStatisticVO temperature) {
        this.temperature = temperature;
    }

    public PerfTaskStatisticVO getInstantAmperage() {
        return instantAmperage;
    }

    public void setInstantAmperage(PerfTaskStatisticVO instantAmperage) {
        this.instantAmperage = instantAmperage;
    }

    public PerfTaskStatisticVO getPower() {
        return power;
    }

    public void setPower(PerfTaskStatisticVO power) {
        this.power = power;
    }

    public PerfTaskStatisticVO getLevel() {
        return level;
    }

    public void setLevel(PerfTaskStatisticVO level) {
        this.level = level;
    }
}
