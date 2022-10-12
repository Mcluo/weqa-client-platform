package com.netease.vcloud.qa.model;

import java.util.Date;

public class AutoTestResultDO {

    /**
     * 主键KEY，可以自动生成
     */
    private long id ;

    /**
     *创建时间
     */
    private Date gmtCreate ;

    /**
     *最后修改时间
     */
    private Date gmtUpdate ;
    /**
     * 运行信息。以json形式保存，当前主要为运行的IP地址+开始时戳
     * 每次运行均需要保证运行信息的唯一性，以便找到运行数据
     */
    private String runInfo ;
    /**
     * 用例名
     */
    private String caseName ;
    /**
     * 详情
     */
    private String caseDetail ;
    /**
     * 成功数（用于统计聚合）
     */
    private int successNumber ;
    /**
     * 失败数（用于统计聚合）
     */
    private int failNumber ;
    /**
     * 其它运行情况，以JSON形式保存（不建议太长，DB会存不下）
     * 预留字段，暂时不存
     * */
    private String runResult ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getRunInfo() {
        return runInfo;
    }

    public void setRunInfo(String runInfo) {
        this.runInfo = runInfo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseDetail() {
        return caseDetail;
    }

    public void setCaseDetail(String caseDetail) {
        this.caseDetail = caseDetail;
    }

    public int getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(int successNumber) {
        this.successNumber = successNumber;
    }

    public int getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(int failNumber) {
        this.failNumber = failNumber;
    }

    public String getRunResult() {
        return runResult;
    }

    public void setRunResult(String runResult) {
        this.runResult = runResult;
    }
}
