package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

/**
 * PerfReportResultData 的数据不够完整，需要再次基础上增加超类
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/31 21:53
 */
public class PerfReportResultJSONData implements AutoPerfBaseReportResultDataInterface{

    private PerfReportResultData perfReportResultData ;

    private List<PerfReportEchartsVO> echartsList ;

    public PerfReportResultData getPerfReportResultData() {
        return perfReportResultData;
    }

    public void setPerfReportResultData(PerfReportResultData perfReportResultData) {
        this.perfReportResultData = perfReportResultData;
    }

    public List<PerfReportEchartsVO> getEchartsList() {
        return echartsList;
    }

    public void setEchartsList(List<PerfReportEchartsVO> echartsList) {
        this.echartsList = echartsList;
    }
}
