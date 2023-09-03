package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 15:50
 */
public class PerfTaskAndroidDetailVO implements PerfTaskDetailInterface{

     private List<PerfTaskAndroidDetailListVO> detailList ;

     private PerfTaskStatisticVO memory ;

     private PerfTaskDetailInfoVO memoryDetail ;

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

     private PerfTaskStatisticVO appCPU;

     private PerfTaskDetailInfoVO appCPUDetail;


     private PerfTaskStatisticVO cpu ;

     private PerfTaskDetailInfoVO cpuDetail ;

     private PerfTaskStatisticVO gpu ;

     private PerfTaskDetailInfoVO gpuDetail ;
     public List<PerfTaskAndroidDetailListVO> getDetailList() {
          return detailList;
     }

     public void setDetailList(List<PerfTaskAndroidDetailListVO> detailList) {
          this.detailList = detailList;
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

     public PerfTaskDetailInfoVO getMemoryDetail() {
          return memoryDetail;
     }

     public void setMemoryDetail(PerfTaskDetailInfoVO memoryDetail) {
          this.memoryDetail = memoryDetail;
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

     public PerfTaskStatisticVO getAppCPU() {
          return appCPU;
     }

     public void setAppCPU(PerfTaskStatisticVO appCPU) {
          this.appCPU = appCPU;
     }

     public PerfTaskDetailInfoVO getAppCPUDetail() {
          return appCPUDetail;
     }

     public void setAppCPUDetail(PerfTaskDetailInfoVO appCPUDetail) {
          this.appCPUDetail = appCPUDetail;
     }

     public PerfTaskStatisticVO getCpu() {
          return cpu;
     }

     public void setCpu(PerfTaskStatisticVO cpu) {
          this.cpu = cpu;
     }

     public PerfTaskDetailInfoVO getCpuDetail() {
          return cpuDetail;
     }

     public void setCpuDetail(PerfTaskDetailInfoVO cpuDetail) {
          this.cpuDetail = cpuDetail;
     }

     public PerfTaskStatisticVO getGpu() {
          return gpu;
     }

     public void setGpu(PerfTaskStatisticVO gpu) {
          this.gpu = gpu;
     }

     public PerfTaskDetailInfoVO getGpuDetail() {
          return gpuDetail;
     }

     public void setGpuDetail(PerfTaskDetailInfoVO gpuDetail) {
          this.gpuDetail = gpuDetail;
     }

     @Override
     public int getDataSizeLength() {
          return detailList.size();
     }
}
