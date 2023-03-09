package com.netease.vcloud.qa.service.risk.manager.view;

import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/15 17:13
 */
public class RiskTaskRiskDetailVO {

    //key 为状态，value为具体的风险列表
    private Map<String, List<RiskDetailInfoVO>> riskDetailInfo ;

    public Map<String, List<RiskDetailInfoVO>> getRiskDetailInfo() {
        return riskDetailInfo;
    }

    public void setRiskDetailInfo(Map<String, List<RiskDetailInfoVO>> riskDetailInfo) {
        this.riskDetailInfo = riskDetailInfo;
    }
}
