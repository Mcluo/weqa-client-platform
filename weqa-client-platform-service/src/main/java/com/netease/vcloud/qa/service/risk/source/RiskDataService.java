package com.netease.vcloud.qa.service.risk.source;

import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.source.manager.AutoTestCheckManageService;
import com.netease.vcloud.qa.service.risk.source.manager.RiskTestCheckManageInterface;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 12:01
 */
@Service
public class RiskDataService {
    /**
     * 自动化测试
     */
    private static final String AUTO_TEST = "auto_test" ;
    /**
     * 开发自测
     */
    private static final String DEVELOP_TEST = "develop_test" ;
    /**
     * 冒烟测试
     */
    private static final String SMOKE_TEST = "smoke_test" ;
    /**
     * 缺陷状态
     */
    private static final String BUG_STATUS = "bug_status" ;

    private Map<String , RiskTestCheckManageInterface> riskTestCheckManageMap ;

    @Autowired
    private AutoTestCheckManageService autoTestCheckManageService ;

    public void init(){
        riskTestCheckManageMap = new HashMap<String, RiskTestCheckManageInterface>() ;
        riskTestCheckManageMap.put(AUTO_TEST,autoTestCheckManageService) ;
    }


    public void asyncDate(String dataType,RiskCheckRange rangeType , Long rangeId) throws RiskCheckException{
        RiskTestCheckManageInterface riskTestCheckService = riskTestCheckManageMap.get(dataType) ;
        if (riskTestCheckService == null ){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NOT_SUPPORT_EXCEPTION) ;
        }
        riskTestCheckService.asyncDate(rangeType ,rangeId);
    }

    public String getCurrentDate(String dataType, RiskCheckRange rangeType , Long rangeId) throws RiskCheckException {
        RiskTestCheckManageInterface riskTestCheckService = riskTestCheckManageMap.get(dataType) ;
        if (riskTestCheckService == null ){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NOT_SUPPORT_EXCEPTION) ;
        }
       return  riskTestCheckService.getCurrentData(rangeType,rangeId) ;
    }

    public boolean hasRisk(String dataType, String checkInfoStructStr, String currentDate) throws RiskCheckException{
        RiskTestCheckManageInterface riskTestCheckService = riskTestCheckManageMap.get(dataType) ;
        if (riskTestCheckService == null ){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NOT_SUPPORT_EXCEPTION) ;
        }
        CheckInfoStructInterface checkInfoStruct = riskTestCheckService.buildCheckInfo(checkInfoStructStr) ;
        return  riskTestCheckService.hasRisk(checkInfoStruct,currentDate) ;
    }



}
