package com.netease.vcloud.qa.service.perf.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.perf.data.AutoPerfBaseReportResultDataInterface;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 16:07
 */
public class PerfBaseReportDetailVO {

    /**
     * 报告名
     */
    private String name ;
    /**
     * 类型
     */
    private String type ;
    /**
     * 时戳
     */
    private Long time ;
    /**
     * 归属人
     */
    private UserInfoVO owner ;
    /**
     * 结果JSON
     */
    private AutoPerfBaseReportResultDataInterface result ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public UserInfoVO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoVO owner) {
        this.owner = owner;
    }

    public AutoPerfBaseReportResultDataInterface getResult() {
        return result;
    }

    public void setResult(AutoPerfBaseReportResultDataInterface result) {
        this.result = result;
    }
}
