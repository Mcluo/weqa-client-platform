package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.ClientRiskSmokeExecDAO;
import com.netease.vcloud.qa.model.ClientRiskSmokeExecDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.DevSmokeExecCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.DevSmokeExecCheckDataInfoVO;
import com.netease.vcloud.qa.service.tc.TCExecManagerService;
import com.netease.vcloud.qa.service.tc.data.ClientExecDataBO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:52
 */
@Service
public class DevSmokeExecCheckManagerService implements RiskTestCheckManageInterface{

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private TCExecManagerService tcExecManagerService ;
    @Autowired
    private ClientRiskSmokeExecDAO clientRiskSmokeExecDAO;

    @Override
    public void asyncDate(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.asyncDate] some param is null");
            return;
        }
        ClientRiskSmokeExecDO clientRiskSmokeExecDO = clientRiskSmokeExecDAO.getClientRiskSmokeExec(rangeType.getCode(),rangeId) ;
        if (clientRiskSmokeExecDO == null){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.asyncDate] clientRiskSmokeExecDO is null");
            return;
        }
        Long developTVId = clientRiskSmokeExecDO.getDevelopTVId() ;
        RISK_LOGGER.info("[DevSmokeExecCheckManagerService.asyncDate] develop TV_id start :"+ developTVId +" async ");
        boolean flag = tcExecManagerService.addOrUpdateTVDetailInfo(developTVId) ;
        if (!flag){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.asyncDate] updateData TV_id fail ");
        }else {
            RISK_LOGGER.info("[DevSmokeExecCheckManagerService.asyncDate] develop TV_id finish :"+ developTVId +" async ");
        }
    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.getCurrentData] some param is null");
            return null;
        }
        ClientRiskSmokeExecDO clientRiskSmokeExecDO = clientRiskSmokeExecDAO.getClientRiskSmokeExec(rangeType.getCode(),rangeId) ;
        if (clientRiskSmokeExecDO == null){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.getCurrentData] clientRiskSmokeExecDO is null");
            return null;
        }
        Long developTVId = clientRiskSmokeExecDO.getDevelopTVId() ;
        ClientExecDataBO clientExecDataBO = tcExecManagerService.getTVDetailInfo(developTVId) ;
        if (clientExecDataBO == null){
            return null ;
        }
        double execRate = (double) (clientExecDataBO.getAccept() * 10000 / clientExecDataBO.getTotal()) / 100 ;
        return execRate+"" ;
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        if (StringUtils.isBlank(checkInfoStructStr)){
            return null ;
        }
        DevSmokeExecCheckData devSmokeExecCheckData = JSONObject.parseObject(checkInfoStructStr,DevSmokeExecCheckData.class) ;
        return devSmokeExecCheckData;
    }

    @Override
    public boolean hasRisk(CheckInfoStructInterface checkInfoStructInterface, String currentData) {
        DevSmokeExecCheckData devSmokeExecCheckData = null ;
        if (checkInfoStructInterface != null && checkInfoStructInterface instanceof DevSmokeExecCheckData){
            devSmokeExecCheckData = (DevSmokeExecCheckData) checkInfoStructInterface ;
        }
        if (devSmokeExecCheckData == null){
            return false ;
        }
        Double passPercent = devSmokeExecCheckData.getPassPercent() ;
        Double currentPercent = Double.parseDouble(currentData) ;
        if (passPercent == null){
            return false ;
        }
        if (currentPercent == null) {
            return true ;
        }
        boolean flag = currentPercent >= passPercent ? false : true ;
        return flag;
    }

    @Override
    public String buildPassStandard(CheckInfoStructInterface checkInfoStructInterface) {
        DevSmokeExecCheckData devSmokeExecCheckData = null ;
        if (checkInfoStructInterface instanceof DevSmokeExecCheckData){
            devSmokeExecCheckData = (DevSmokeExecCheckData) checkInfoStructInterface ;
        }
        if (devSmokeExecCheckData == null){
            return null ;
        }
        Double passPercent = devSmokeExecCheckData.getPassPercent();
        String passStandard = "开发冒烟测试通过率超过"+passPercent+"%" ;
        return passStandard;
    }

    @Override
    public CheckDataVOInterface getCheckData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.getCheckData] some param is null");
            return null;
        }
        ClientRiskSmokeExecDO clientRiskSmokeExecDO = clientRiskSmokeExecDAO.getClientRiskSmokeExec(rangeType.getCode(),rangeId) ;
        if (clientRiskSmokeExecDO == null){
            RISK_LOGGER.error("[DevSmokeExecCheckManagerService.getCheckData] clientRiskSmokeExecDO is null");
            return null;
        }
        Long developTVId = clientRiskSmokeExecDO.getDevelopTVId() ;
        ClientExecDataBO clientExecDataBO = tcExecManagerService.getTVDetailInfo(developTVId) ;
        if (clientExecDataBO == null){
            return null ;
        }
        DevSmokeExecCheckDataInfoVO devSmokeExecCheckDataInfoVO = new DevSmokeExecCheckDataInfoVO() ;
        devSmokeExecCheckDataInfoVO.setTvID(clientExecDataBO.getTvID());
        devSmokeExecCheckDataInfoVO.setTotal(clientExecDataBO.getTotal());
        devSmokeExecCheckDataInfoVO.setAccept(clientExecDataBO.getAccept());
        devSmokeExecCheckDataInfoVO.setFail(clientExecDataBO.getFail());
        devSmokeExecCheckDataInfoVO.setIgnore(clientExecDataBO.getIgnore());
        devSmokeExecCheckDataInfoVO.setUnCarryOut(clientExecDataBO.getUnCarryOut());
        double percent = (double) (clientExecDataBO.getAccept() * 100) / (double) clientExecDataBO.getTotal() ;
        devSmokeExecCheckDataInfoVO.setAccessPercent(percent);
        return devSmokeExecCheckDataInfoVO;
    }

    /**
     * 绑定ID和自动化测试内容
     * @param riskTask
     * @param tvID
     * @return
     */
    public boolean bindRiskTaskAndTV(long riskTask,long tvID){
        int count = clientRiskSmokeExecDAO.insertOrUpdateClientRiskSmokeExec(RiskCheckRange.TASK.getCode(), riskTask,tvID) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }
}
