package com.netease.vcloud.qa.service.risk.manager.view;

import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/27 16:14
 */
public class RiskDetailWithDataInfoVO {

    private RiskDetailInfoVO detailInfo ;

    private CheckDataVOInterface  checkData ;

    public RiskDetailInfoVO getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(RiskDetailInfoVO detailInfo) {
        this.detailInfo = detailInfo;
    }

    public CheckDataVOInterface getCheckData() {
        return checkData;
    }

    public void setCheckData(CheckDataVOInterface checkData) {
        this.checkData = checkData;
    }
}
