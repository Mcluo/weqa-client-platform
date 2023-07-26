package com.netease.vcloud.qa.data.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 15:08
 */
public class ApiCallData {

    private String platform;

    private Long taskCaseId;

    private Long userId;

    private String channelName;

    private List<ApiCallUnitData> data ;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getTaskCaseId() {
        return taskCaseId;
    }

    public void setTaskCaseId(Long taskCaseId) {
        this.taskCaseId = taskCaseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<ApiCallUnitData> getData() {
        return data;
    }

    public void setData(List<ApiCallUnitData> data) {
        this.data = data;
    }
}
