package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:19
 */
public class ClientPerfReportDO {
    /**
     * id
     */
    private Long id ;

    /**
     *创建时间
     */
    private Date gmtCreate ;

    /**
     *最后修改时间
     */
    private Date gmtUpdate ;

    /**
     * 报告名
     */
    private String reportName ;
    /**
     * 类型
     */
    private byte reportType ;
    /**
     * 归属人
     */
    private String owner ;
    /**
     * 关联任务，以“|”划分
     */
    private String relationTask ;
    /**
     * 关联基线ID
     */
    private long relationBase ;
    /**
     * 结果JSON
     */
    private String resultData ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public byte getReportType() {
        return reportType;
    }

    public void setReportType(byte reportType) {
        this.reportType = reportType;
    }

    public String getRelationTask() {
        return relationTask;
    }

    public void setRelationTask(String relationTask) {
        this.relationTask = relationTask;
    }

    public long getRelationBase() {
        return relationBase;
    }

    public void setRelationBase(long relationBase) {
        this.relationBase = relationBase;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
