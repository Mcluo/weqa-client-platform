package com.netease.vcloud.qa.service.risk.source;

import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.source.manager.*;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
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
     * 开发自测执行情况
     */
    private static final String DEVELOP_TEST = "develop_test" ;
    /**
     * 冒烟测试占比
     */
    private static final String SMOKE_TEST = "smoke_test" ;
    /**
     * 缺陷状态
     */
    private static final String BUG_STATUS = "bug_status" ;

    /**
     * 自动化覆盖情况
     */
    private static final String AUTO_TEST_COVER = "auto_test_cover" ;

    private Map<String , RiskTestCheckManageInterface> riskTestCheckManageMap ;

    @Autowired
    private AutoTestCheckManageService autoTestCheckManageService ;

    @Autowired
    private DevSmokeExecCheckManagerService devSmokeExecCheckManagerService ;

    @Autowired
    private DevSmokeRateCheckManagerService devSmokeRateCheckManagerService ;

    @Autowired
    private TCTestSuitCheckManagerService tcTestSuitCheckManagerService ;

    @PostConstruct
    public void init(){
        riskTestCheckManageMap = new HashMap<String, RiskTestCheckManageInterface>() ;
        riskTestCheckManageMap.put(AUTO_TEST,autoTestCheckManageService) ;
        riskTestCheckManageMap.put(DEVELOP_TEST,devSmokeExecCheckManagerService) ;
        riskTestCheckManageMap.put(SMOKE_TEST,devSmokeRateCheckManagerService) ;
        riskTestCheckManageMap.put(AUTO_TEST_COVER,tcTestSuitCheckManagerService) ;
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
       String current = riskTestCheckService.getCurrentData(rangeType,rangeId) ;
        return current == null ? "": current ;
    }

    public boolean hasRisk(String dataType, String checkInfoStructStr, String currentDate) throws RiskCheckException{
        RiskTestCheckManageInterface riskTestCheckService = riskTestCheckManageMap.get(dataType) ;
        if (riskTestCheckService == null ){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NOT_SUPPORT_EXCEPTION) ;
        }
        CheckInfoStructInterface checkInfoStruct = riskTestCheckService.buildCheckInfo(checkInfoStructStr) ;
        return  riskTestCheckService.hasRisk(checkInfoStruct,currentDate) ;
    }

    public String getPassStandard(String dataType, String checkInfoStructStr)  throws RiskCheckException{
        RiskTestCheckManageInterface riskTestCheckService = riskTestCheckManageMap.get(dataType) ;
        if (riskTestCheckService == null ){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NOT_SUPPORT_EXCEPTION) ;
        }
        CheckInfoStructInterface checkInfoStruct = riskTestCheckService.buildCheckInfo(checkInfoStructStr) ;
        return riskTestCheckService.buildPassStandard(checkInfoStruct) ;
    }

    public CheckDataVOInterface getCheckData(String dataType ,RiskCheckRange rangeType ,Long rangId) throws RiskCheckException{
        RiskTestCheckManageInterface riskTestCheckService = riskTestCheckManageMap.get(dataType) ;
        if (riskTestCheckService == null ){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NOT_SUPPORT_EXCEPTION) ;
        }
        return riskTestCheckService.getCheckData(rangeType,rangId) ;
    }

}
