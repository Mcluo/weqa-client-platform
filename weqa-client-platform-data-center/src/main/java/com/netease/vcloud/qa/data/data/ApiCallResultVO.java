package com.netease.vcloud.qa.data.data;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 20:17
 */
public class ApiCallResultVO implements Comparable<ApiCallResultVO>{

    private String platform;

    private Long taskCaseId;

    private Long userId;

    private String channelName;

    private Long callTime ;

    private String method ;

    private String result ;

    private String params ;


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

    public Long getCallTime() {
        return callTime;
    }

    public void setCallTime(Long callTime) {
        this.callTime = callTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int compareTo(ApiCallResultVO o) {
        return (int)(this.callTime - o.callTime);
    }
}
