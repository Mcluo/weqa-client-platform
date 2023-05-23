package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/23 10:39
 */
public class FirstFrameReportResultData implements AutoPerfBaseReportResultDataInterface{
    //任务具体数据
    private List<FirstFrameReportDetailData> dataList ;

    public List<FirstFrameReportDetailData> getDataList() {
        return dataList;
    }

    public void setDataList(List<FirstFrameReportDetailData> dataList) {
        this.dataList = dataList;
    }
}
