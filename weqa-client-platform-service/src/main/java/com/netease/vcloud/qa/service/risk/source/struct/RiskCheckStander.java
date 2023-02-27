package com.netease.vcloud.qa.service.risk.source.struct;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:48
 */
public class RiskCheckStander implements Serializable {
    /**
     * 风险类型（用于匹配具体数据结构和前端渲染）
     */
    private String type ;
    /**
     * 具体描述信息（需要对外展示）
     */
    private String checkDesc ;

    /**
     * 具体数据结构（用于具体分析）
     */
    private String checkInfoDetail ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    public String getCheckInfoDetail() {
        return checkInfoDetail;
    }

    public void setCheckInfoDetail(String checkInfoDetail) {
        this.checkInfoDetail = checkInfoDetail;
    }
}
