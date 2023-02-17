package com.netease.vcloud.qa.service.risk.source.manager;

import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/17 17:46
 */
@Service
public class AutoTestCheckManageService <AutoTestCheckData>implements  RiskTestCheckManageInterface{
    @Override
    public void asyncDate() {

    }

    @Override
    public String getCurrentData() {
        return null;
    }

    @Override
    public boolean hasRisk(RiskTestCheckManageInterface riskTestCheckManageInterface) {
        return false;
    }
}
