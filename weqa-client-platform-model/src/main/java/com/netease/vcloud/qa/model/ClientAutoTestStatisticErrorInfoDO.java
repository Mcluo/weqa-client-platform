package com.netease.vcloud.qa.model;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 17:37
 */
public class ClientAutoTestStatisticErrorInfoDO {

    private String errorInfo;

    private int count ;

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
