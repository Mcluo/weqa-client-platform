package com.netease.vcloud.qa.service.perf.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/23 11:03
 */
public class FirstFrameReportDetailData {

    /**
     * 任务ID
     */
    private Long taskId ;

    /**
     * 设备信息
     */
    private String deviceInfo ;

    /**
     * 设备数据信息
     */
    private PerfTestReportData data ;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public PerfTestReportData getData() {
        return data;
    }

    public void setData(PerfTestReportData data) {
        this.data = data;
    }
}
