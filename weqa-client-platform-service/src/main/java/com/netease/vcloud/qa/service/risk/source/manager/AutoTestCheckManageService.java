package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.AutoTestCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/17 17:46
 */
@Service
public class AutoTestCheckManageService  implements  RiskTestCheckManageInterface{
    @Override
    public void asyncDate(RiskCheckRange rangeType , Long rangeId) {

    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType , Long rangeId) {
        return null;
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        AutoTestCheckData autoTestCheckData = JSONObject.parseObject(checkInfoStructStr,AutoTestCheckData.class) ;
        return autoTestCheckData;
    }

    @Override
    public boolean hasRisk(CheckInfoStructInterface checkInfoStructInterface, String currentData) {
        return false;
    }
}
