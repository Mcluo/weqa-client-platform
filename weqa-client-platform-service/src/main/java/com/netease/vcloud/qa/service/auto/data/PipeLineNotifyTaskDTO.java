package com.netease.vcloud.qa.service.auto.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/19 11:06
 */
public class PipeLineNotifyTaskDTO {
    private int platform ;

    private String taskurl ;

    private int casesNum ;

    private int succNum ;

    private int failNum ;

    private  int cancelNum ;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getTaskurl() {
        return taskurl;
    }

    public void setTaskurl(String taskurl) {
        this.taskurl = taskurl;
    }

    public int getCasesNum() {
        return casesNum;
    }

    public void setCasesNum(int casesNum) {
        this.casesNum = casesNum;
    }

    public int getSuccNum() {
        return succNum;
    }

    public void setSuccNum(int succNum) {
        this.succNum = succNum;
    }

    public int getFailNum() {
        return failNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }

    public int getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(int cancelNum) {
        this.cancelNum = cancelNum;
    }
}
