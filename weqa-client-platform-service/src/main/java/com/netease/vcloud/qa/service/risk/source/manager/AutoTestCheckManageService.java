package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.AutoTestCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/17 17:46
 */
@Service
public class AutoTestCheckManageService  implements  RiskTestCheckManageInterface{

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Override
    public void asyncDate(RiskCheckRange rangeType , Long rangeId) {
        return;
    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType , Long rangeId) {
        return "89.2";
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        AutoTestCheckData autoTestCheckData = JSONObject.parseObject(checkInfoStructStr,AutoTestCheckData.class) ;
        return autoTestCheckData;
    }

    @Override
    public boolean hasRisk(CheckInfoStructInterface checkInfoStructInterface, String currentData) {
        if (checkInfoStructInterface instanceof AutoTestCheckData){
            AutoTestCheckData autoTestCheckData = (AutoTestCheckData) checkInfoStructInterface ;
            Double passPercent = autoTestCheckData.getPassPercent() ;
            boolean hasRisk = false ;
            if (passPercent==null){
                return false ;
            }else {
                try {
                    Double current = Double.parseDouble(currentData) ;
                    if (current > passPercent){
                        hasRisk = false ;
                    }else {
                        hasRisk = true ;
                    }
                }catch (NumberFormatException e){
                    RISK_LOGGER.error("[AutoTestCheckManageService.hasRisk] parse current data fail",e);
                    return false ;
                }
            }
            return hasRisk ;
        }else {
            return false;
        }
    }
}
