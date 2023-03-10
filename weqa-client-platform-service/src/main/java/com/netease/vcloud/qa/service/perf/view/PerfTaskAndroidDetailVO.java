package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 15:50
 */
public class PerfTaskAndroidDetailVO implements PerfTaskDetailInterface{

     private List<PerfTaskAndroidDetailListVO> detailList ;

     private PerfTaskStatisticVO memory ;

     private PerfTaskStatisticVO voltage ;

     private PerfTaskStatisticVO temperature ;

     private PerfTaskStatisticVO instantAmperage ;

     private PerfTaskStatisticVO power ;

     private PerfTaskStatisticVO level ;

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
}
