package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/11 16:43
 */
public class FirstFrameDetailInfoVO {

    private FirstFrameBaseInfoVO baseInfo ;

    private List<FirstFrameDataInfoVO> dataInfo ;
    //自动化相关信息
    private Long autoId ;
    /**
     * 自动任务状态
     */
    private String autoStatus ;

    public FirstFrameBaseInfoVO getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(FirstFrameBaseInfoVO baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<FirstFrameDataInfoVO> getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(List<FirstFrameDataInfoVO> dataInfo) {
        this.dataInfo = dataInfo;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public String getAutoStatus() {
        return autoStatus;
    }

    public void setAutoStatus(String autoStatus) {
        this.autoStatus = autoStatus;
    }
}
