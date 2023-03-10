package com.netease.vcloud.qa.service.perf.view;

/**
 * 性能任务详情
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 21:18
 */
public class PerfTaskDetailVO {

    private PerfTaskInfoVO perfTaskInfo ;

    private PerfTaskDetailInterface detailInfo ;

    public PerfTaskInfoVO getPerfTaskInfo() {
        return perfTaskInfo;
    }

    public void setPerfTaskInfo(PerfTaskInfoVO perfTaskInfo) {
        this.perfTaskInfo = perfTaskInfo;
    }

    public PerfTaskDetailInterface getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(PerfTaskDetailInterface detailInfo) {
        this.detailInfo = detailInfo;
    }
}
