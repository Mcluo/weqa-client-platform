package com.netease.vcloud.qa.data.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 14:36
 */
public class ApiCallUnitData {

    private String method ;

    private String result ;

    private String params ;

    private Long timeStamp ; ;

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

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
