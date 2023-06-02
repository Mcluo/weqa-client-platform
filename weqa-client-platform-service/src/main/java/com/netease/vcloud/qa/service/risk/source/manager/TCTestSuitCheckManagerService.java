package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.model.ClientRiskTCTestSuitCheckDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.AutoTestCheckData;
import com.netease.vcloud.qa.service.risk.source.manager.data.TCTestSuitCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import com.netease.vcloud.qa.service.tc.TCAutoCoverManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/1 17:40
 */
@Service
public class TCTestSuitCheckManagerService implements RiskTestCheckManageInterface{

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private TCAutoCoverManagerService tcAutoCoverManagerService ;

    @Autowired
    private ClientRiskTCTestSuitCheckDO clientRiskTCTestSuitCheckDO ;

    @Override
    public void asyncDate(RiskCheckRange rangeType, Long rangeId) {
        //需要同步执行集相关数据(tc可能会变动)
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.asyncDate] some param is null");
            return;
        }
    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.asyncDate] some param is null");
            return null;
        }
        return null;
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        TCTestSuitCheckData tcTestCheckData = JSONObject.parseObject(checkInfoStructStr,TCTestSuitCheckData.class) ;
        return tcTestCheckData;
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
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.getCheckData] some param is null");
            return null;
        }
        return null;
    }
}
