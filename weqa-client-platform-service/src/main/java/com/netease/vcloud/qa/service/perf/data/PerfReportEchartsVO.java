package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/31 22:07
 */
public class PerfReportEchartsVO {

    private String title ;

    private boolean hasBaseLine = false ;

    private List<String> legendList ;

    private List<String> xAxisList ;

    private List<List<Number>>  dataList ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLegendList() {
        return legendList;
    }

    public void setLegendList(List<String> legendList) {
        this.legendList = legendList;
    }

    public List<String> getxAxisList() {
        return xAxisList;
    }

    public void setxAxisList(List<String> xAxisList) {
        this.xAxisList = xAxisList;
    }

    public List<List<Number>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Number>> dataList) {
        this.dataList = dataList;
    }

    public boolean isHasBaseLine() {
        return hasBaseLine;
    }

    public void setHasBaseLine(boolean hasBaseLine) {
        this.hasBaseLine = hasBaseLine;
    }
}
