package com.netease.vcloud.qa.data.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 15:08
 */
public class ApiCallData {

    private String platform;

    private Integer taskCaseId;

    private Integer userId;

    private String channelName;

    private List<ApiCallUnitData> data ;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getTaskCaseId() {
        return taskCaseId;
    }

    public void setTaskCaseId(Integer taskCaseId) {
        this.taskCaseId = taskCaseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
