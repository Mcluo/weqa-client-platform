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

    private PerfTaskDetailInfoVO memoryDetail ;

    private PerfTaskStatisticVO appCPU ;

    private PerfTaskDetailInfoVO appCPUDetail ;
    private PerfTaskStatisticVO sysCPU ;

    private PerfTaskDetailInfoVO sysCPUDetail ;
    private PerfTaskStatisticVO voltage ;

    private PerfTaskDetailInfoVO voltageDetail ;
    private PerfTaskStatisticVO temperature ;

    private PerfTaskDetailInfoVO temperatureDetail ;
    private PerfTaskStatisticVO instantAmperage ;

    private PerfTaskDetailInfoVO instantAmperageDetail ;
    private PerfTaskStatisticVO power ;

    private PerfTaskDetailInfoVO powerDetail ;
    private PerfTaskStatisticVO level ;

    private PerfTaskDetailInfoVO levelDetail ;

    private PerfTaskStatisticVO deviceGPU ;

    private PerfTaskDetailInfoVO deviceGPUDetail ;

    private PerfTaskStatisticVO tilerGPU ;

    private PerfTaskDetailInfoVO tilerGPUDetail ;
    private PerfTaskStatisticVO renderGPU ;

    private PerfTaskDetailInfoVO renderGPUDetail ;


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

    public PerfTaskStatisticVO getAppCPU() {
        return appCPU;
    }

    public void setAppCPU(PerfTaskStatisticVO appCPU) {
        this.appCPU = appCPU;
    }

    public PerfTaskStatisticVO getSysCPU() {
        return sysCPU;
    }

    public void setSysCPU(PerfTaskStatisticVO sysCPU) {
        this.sysCPU = sysCPU;
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

    public PerfTaskDetailInfoVO getMemoryDetail() {
        return memoryDetail;
    }

    public void setMemoryDetail(PerfTaskDetailInfoVO memoryDetail) {
        this.memoryDetail = memoryDetail;
    }

    public PerfTaskDetailInfoVO getAppCPUDetail() {
        return appCPUDetail;
    }

    public void setAppCPUDetail(PerfTaskDetailInfoVO appCPUDetail) {
        this.appCPUDetail = appCPUDetail;
    }

    public PerfTaskDetailInfoVO getSysCPUDetail() {
        return sysCPUDetail;
    }

    public void setSysCPUDetail(PerfTaskDetailInfoVO sysCPUDetail) {
        this.sysCPUDetail = sysCPUDetail;
    }

    public PerfTaskDetailInfoVO getVoltageDetail() {
        return voltageDetail;
    }

    public void setVoltageDetail(PerfTaskDetailInfoVO voltageDetail) {
        this.voltageDetail = voltageDetail;
    }

    public PerfTaskDetailInfoVO getTemperatureDetail() {
        return temperatureDetail;
    }

    public void setTemperatureDetail(PerfTaskDetailInfoVO temperatureDetail) {
        this.temperatureDetail = temperatureDetail;
    }

    public PerfTaskDetailInfoVO getInstantAmperageDetail() {
        return instantAmperageDetail;
    }

    public void setInstantAmperageDetail(PerfTaskDetailInfoVO instantAmperageDetail) {
        this.instantAmperageDetail = instantAmperageDetail;
    }

    public PerfTaskDetailInfoVO getPowerDetail() {
        return powerDetail;
    }

    public void setPowerDetail(PerfTaskDetailInfoVO powerDetail) {
        this.powerDetail = powerDetail;
    }

    public PerfTaskDetailInfoVO getLevelDetail() {
        return levelDetail;
    }

    public void setLevelDetail(PerfTaskDetailInfoVO levelDetail) {
        this.levelDetail = levelDetail;
    }

    public PerfTaskStatisticVO getDeviceGPU() {
        return deviceGPU;
    }

    public void setDeviceGPU(PerfTaskStatisticVO deviceGPU) {
        this.deviceGPU = deviceGPU;
    }

    public PerfTaskDetailInfoVO getDeviceGPUDetail() {
        return deviceGPUDetail;
    }

    public void setDeviceGPUDetail(PerfTaskDetailInfoVO deviceGPUDetail) {
        this.deviceGPUDetail = deviceGPUDetail;
    }

    public PerfTaskStatisticVO getTilerGPU() {
        return tilerGPU;
    }

    public void setTilerGPU(PerfTaskStatisticVO tilerGPU) {
        this.tilerGPU = tilerGPU;
    }

    public PerfTaskDetailInfoVO getTilerGPUDetail() {
        return tilerGPUDetail;
    }

    public void setTilerGPUDetail(PerfTaskDetailInfoVO tilerGPUDetail) {
        this.tilerGPUDetail = tilerGPUDetail;
    }

    public PerfTaskStatisticVO getRenderGPU() {
        return renderGPU;
    }

    public void setRenderGPU(PerfTaskStatisticVO renderGPU) {
        this.renderGPU = renderGPU;
    }

    public PerfTaskDetailInfoVO getRenderGPUDetail() {
        return renderGPUDetail;
    }

    public void setRenderGPUDetail(PerfTaskDetailInfoVO renderGPUDetail) {
        this.renderGPUDetail = renderGPUDetail;
    }

    @Override
    public int getDataSizeLength() {
        return detailList.size();
    }
}
