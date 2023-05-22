package com.netease.vcloud.qa.service.perf.view;

import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.result.view.UserInfoVO;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 15:19
 */
public class PerfBaseReportVO {
    /**
     * id
     */
    private Long id ;

    /**
     *创建时间
     */
    private Long createTime ;

    /**
     * 报告名
     */
    private String reportName ;
    /**
     * 类型
     */
    private String reportType ;
    /**
     * 归属人
     */
    private UserInfoVO operator ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public UserInfoVO getOperator() {
        return operator;
    }

    public void setOperator(UserInfoVO operator) {
        this.operator = operator;
    }
}
