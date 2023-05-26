package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/23 10:38
 */
public class PerfReportResultData implements AutoPerfBaseReportResultDataInterface{

    private List<PerfReportResultDetailData> dataList ;

    public List<PerfReportResultDetailData> getDataList() {
        return dataList;
    }

    public void setDataList(List<PerfReportResultDetailData> dataList) {
        this.dataList = dataList;
    }
}
