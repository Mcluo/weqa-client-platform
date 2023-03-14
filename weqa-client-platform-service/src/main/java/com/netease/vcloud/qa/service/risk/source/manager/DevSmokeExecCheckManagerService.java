package com.netease.vcloud.qa.service.risk.source.manager;

import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:52
 */
@Service
public class DevSmokeExecCheckManagerService implements RiskTestCheckManageInterface{
    @Override
    public void asyncDate(RiskCheckRange rangeType, Long rangeId) {

    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType, Long rangeId) {
        return null;
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        return null;
    }

    @Override
    public boolean hasRisk(CheckInfoStructInterface checkInfoStructInterface, String currentData) {
        return false;
    }

    @Override
    public String buildPassStandard(CheckInfoStructInterface checkInfoStructInterface) {
        return null;
    }

    @Override
    public CheckDataVOInterface getCheckData(RiskCheckRange rangeType, Long rangeId) {
        return null;
    }
}
