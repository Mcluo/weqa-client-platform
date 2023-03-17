package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.CommonData;
import com.netease.vcloud.qa.dao.ClientRiskSmokeExecDAO;
import com.netease.vcloud.qa.dao.ClientRiskSmokeRateDAO;
import com.netease.vcloud.qa.model.ClientRiskSmokeRateDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.DevSmokeRateCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.DevSmokeRateCheckDataInfoVO;
import com.netease.vcloud.qa.service.tc.TCExecManagerService;
import com.netease.vcloud.qa.service.tc.data.ClientExecDataBO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:55
 */
@Service
public class DevSmokeRateCheckManagerService implements RiskTestCheckManageInterface{

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private TCExecManagerService tcExecManagerService ;

    @Autowired
    private ClientRiskSmokeRateDAO clientRiskSmokeRateDAO;

    @Override
    public void asyncDate(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.asyncDate] some param is null");
            return;
        }
        ClientRiskSmokeRateDO clientRiskSmokeRateDO = clientRiskSmokeRateDAO.getClientRiskSmokeRate(rangeType.getCode(),rangeId) ;
        if (clientRiskSmokeRateDO == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.asyncDate] clientRiskSmokeRateDO is null");
            return;
        }
        Long developTVId = clientRiskSmokeRateDO.getDevelopTVId() ;
        Long qaTVId = clientRiskSmokeRateDO.getQaTVId() ;
//        ClientExecDataBO developClientExecDataBO = tcExecManagerService.getTVDetailInfo(developTVId) ;
//        ClientExecDataBO tcClientExecDataBO = tcExecManagerService.getTVDetailInfo(qaTVId) ;
        tcExecManagerService.addOrUpdateTVDetailInfo(developTVId) ;
        tcExecManagerService.addOrUpdateTVDetailInfo(qaTVId) ;
    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.asyncDate] some param is null");
            return null;
        }
        ClientRiskSmokeRateDO clientRiskSmokeRateDO = clientRiskSmokeRateDAO.getClientRiskSmokeRate(rangeType.getCode(),rangeId) ;
        if (clientRiskSmokeRateDO == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.asyncDate] clientRiskSmokeRateDO is null");
            return null;
        }
        Long developTVId = clientRiskSmokeRateDO.getDevelopTVId() ;
        Long qaTVId = clientRiskSmokeRateDO.getQaTVId() ;
        if (developTVId == null || qaTVId == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.asyncDate] developTVId or qaTVId is null");
            return null ;
        }
        ClientExecDataBO developClientExecDataBO = tcExecManagerService.getTVDetailInfo(developTVId) ;
        ClientExecDataBO qaTcClientExecDataBO = tcExecManagerService.getTVDetailInfo(qaTVId) ;
        if (developClientExecDataBO == null || qaTcClientExecDataBO == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.asyncDate] developTVBO or qaTVBO is null");
            return null ;
        }
        int devTestTotal = developClientExecDataBO.getTotal() ;
        int qaTestTotal =  qaTcClientExecDataBO.getTotal() ;
        if (qaTestTotal == 0){
            return "0" ;
        }else {
            double devTCRate = (double)(devTestTotal * 100) / (double) qaTestTotal ;
//            return String.format("%.2f"+devTCRate)+"%" ;
            return CommonData.NUMBER_FORMAT.format(devTCRate) + "%";
        }
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        if (StringUtils.isBlank(checkInfoStructStr)){
            return null ;
        }
        DevSmokeRateCheckData devSmokeRateCheckData = JSONObject.parseObject(checkInfoStructStr,DevSmokeRateCheckData.class) ;
        return devSmokeRateCheckData;
    }

    @Override
    public boolean hasRisk(CheckInfoStructInterface checkInfoStructInterface, String currentData) {
        if (StringUtils.isBlank(currentData)){
            return true ;
        }
        DevSmokeRateCheckData devSmokeRateCheckData = null ;
        if (checkInfoStructInterface !=null && checkInfoStructInterface instanceof DevSmokeRateCheckData){
            devSmokeRateCheckData = (DevSmokeRateCheckData) checkInfoStructInterface ;
        }
        if (devSmokeRateCheckData==null){
            return false ;
        }
        Double passPercent = devSmokeRateCheckData.getPassPercent() ;
        currentData = currentData.split("%")[0] ;
        Double currentPercent = Double.parseDouble(currentData) ;
        if (passPercent == null){
            return false ;
        }
        if (currentPercent == null){
            return true ;
        }
        boolean flag = currentPercent >= passPercent ?false:true ;
        return flag;
    }

    @Override
    public String buildPassStandard(CheckInfoStructInterface checkInfoStructInterface) {
        DevSmokeRateCheckData devSmokeRateCheckData = null ;
        if (checkInfoStructInterface !=null && checkInfoStructInterface instanceof DevSmokeRateCheckData){
            devSmokeRateCheckData = (DevSmokeRateCheckData) checkInfoStructInterface ;
        }
        if (devSmokeRateCheckData==null){
            return null ;
        }
        Double passPercent = devSmokeRateCheckData.getPassPercent() ;
        String passStandard = "冒烟测试占比需高于"+passPercent+"%" ;
        return passStandard ;
    }

    @Override
    public CheckDataVOInterface getCheckData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.getCheckData] some param is null");
            return null;
        }
        ClientRiskSmokeRateDO clientRiskSmokeRateDO = clientRiskSmokeRateDAO.getClientRiskSmokeRate(rangeType.getCode(),rangeId) ;
        if (clientRiskSmokeRateDO == null){
            RISK_LOGGER.error("[DevSmokeRateCheckManagerService.getCheckData] clientRiskSmokeRateDO is null");
            return null;
        }
        Long developTVId = clientRiskSmokeRateDO.getDevelopTVId() ;
        Long qaTVId = clientRiskSmokeRateDO.getQaTVId() ;
        ClientExecDataBO devClientExecDataBO =tcExecManagerService.getTVDetailInfo(developTVId) ;
        ClientExecDataBO qaClientExecDataBO =tcExecManagerService.getTVDetailInfo(qaTVId) ;

        DevSmokeRateCheckDataInfoVO devSmokeRateCheckDataInfoVO = new DevSmokeRateCheckDataInfoVO() ;
        devSmokeRateCheckDataInfoVO.setDevTvId(developTVId);
        devSmokeRateCheckDataInfoVO.setTestTvId(qaTVId);
        if (devClientExecDataBO != null) {
            devSmokeRateCheckDataInfoVO.setDevTvCount(devClientExecDataBO.getTotal());
        }
        if (qaClientExecDataBO != null){
            devSmokeRateCheckDataInfoVO.setTestTvCount(qaClientExecDataBO.getTotal());
        }
        if (devClientExecDataBO != null && qaClientExecDataBO != null){
            if (qaClientExecDataBO.getTotal() > 0) {
                int qaTestTotal = qaClientExecDataBO.getTotal() ;
                int devTestTotal = devClientExecDataBO.getTotal() ;
                Double devTCRate = (double)(devTestTotal * 100) / (double) qaTestTotal ;
                devSmokeRateCheckDataInfoVO.setCurrentTvRate(devTCRate);
            }else {
                devSmokeRateCheckDataInfoVO.setCurrentTvRate(0);
            }
        }
        return devSmokeRateCheckDataInfoVO ;
    }

    public boolean bindRiskTaskAndTVs(long riskTask,long devTvID,long testTvID){
        int count = clientRiskSmokeRateDAO.insertOrUpdateClientRiskSmokeRate(RiskCheckRange.TASK.getCode(), riskTask,devTvID,testTvID) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }
}
