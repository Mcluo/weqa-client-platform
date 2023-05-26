package com.netease.vcloud.qa.service.perf.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.perf.data.AutoPerfBaseReportResultDataInterface;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 16:09
 */
public class PerfBaseLineDetailVO {
    /**
     *
     */
    private Long id ;

    /**
     *创建时间
     */
    private Long time ;

    /**
     * 基线
     */
    private String name ;
    /**
     * 基线类型
     */
    private String type ;
    /**
     * 归属人
     */
    private UserInfoVO owner ;
    /**
     * 结果
     */
    private AutoPerfBaseReportResultDataInterface baseLineData ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

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

    public UserInfoVO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoVO owner) {
        this.owner = owner;
    }

    public AutoPerfBaseReportResultDataInterface getBaseLineData() {
        return baseLineData;
    }

    public void setBaseLineData(AutoPerfBaseReportResultDataInterface baseLineData) {
        this.baseLineData = baseLineData;
    }
}
