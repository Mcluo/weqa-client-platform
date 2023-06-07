package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.CommonData;
import com.netease.vcloud.qa.dao.ClientRiskTCTestSuitCheckDAO;
import com.netease.vcloud.qa.dao.ClientRiskTaskDAO;
import com.netease.vcloud.qa.model.ClientRiskTCTestSuitCheckDO;
import com.netease.vcloud.qa.model.ClientRiskTaskDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.DevSmokeRateCheckData;
import com.netease.vcloud.qa.service.risk.source.manager.data.TCTestSuitCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.TCTestSuitCheckDataInfoVO;
import com.netease.vcloud.qa.service.risk.source.struct.view.TCTestSuitCoveredDetailVO;
import com.netease.vcloud.qa.service.tc.TCAutoCoverManagerService;
import com.netease.vcloud.qa.service.tc.data.ClientTCCoveredData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/1 17:40
 */
@Service
public class TCTestSuitCheckManagerService implements RiskTestCheckManageInterface{

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private ClientRiskTaskDAO clientRiskTaskDAO ;

    @Autowired
    private TCAutoCoverManagerService tcAutoCoverManagerService ;

    @Autowired
    private ClientRiskTCTestSuitCheckDAO clientRiskTCTestSuitCheckDAO ;

    @Override
    public void asyncDate(RiskCheckRange rangeType, Long rangeId) {
        //需要同步执行集相关数据(tc可能会变动)
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.asyncDate] some param is null");
            return;
        }
        ClientRiskTCTestSuitCheckDO clientRiskTCTestSuitCheckDO = clientRiskTCTestSuitCheckDAO.getRiskTCTestSuit(rangeType.getCode(),rangeId) ;
        if (clientRiskTCTestSuitCheckDO == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.asyncDate] clientRiskTCTestSuitCheckDO is null");
            return;
        }
        if (clientRiskTCTestSuitCheckDO.getRangeType() == RiskCheckRange.TASK.getCode()) {
            tcAutoCoverManagerService.addOrUpdateTVInfoDetail(clientRiskTCTestSuitCheckDO.getProjectID(), rangeId, clientRiskTCTestSuitCheckDO.getTvID());
        }else{
            tcAutoCoverManagerService.addOrUpdateTVInfoDetail(clientRiskTCTestSuitCheckDO.getProjectID(),null,clientRiskTCTestSuitCheckDO.getTvID()) ;
        }
    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.getCurrentData] some param is null");
            return null;
        }
        ClientRiskTCTestSuitCheckDO clientRiskTCTestSuitCheckDO = clientRiskTCTestSuitCheckDAO.getRiskTCTestSuit(rangeType.getCode(),rangeId) ;
        if (clientRiskTCTestSuitCheckDO == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.getCurrentData] clientRiskTCTestSuitCheckDO is null");
            return null;
        }
        ClientTCCoveredData clientTCCoveredData = null ;
        //FIXME 这边如果强制更新的话，会造成解析结果偏慢，但如果不更新，就缺少更新的地方，待优化
        if (rangeType == RiskCheckRange.PROJECT) {
            tcAutoCoverManagerService.addOrUpdateTVInfoDetail(rangeId,null,clientRiskTCTestSuitCheckDO.getTvID()) ;
            clientTCCoveredData = tcAutoCoverManagerService.getTaskCurrentValue(rangeId ,null);
        }else if (rangeType == RiskCheckRange.TASK) {
            tcAutoCoverManagerService.addOrUpdateTVInfoDetail(clientRiskTCTestSuitCheckDO.getProjectID(),rangeId,clientRiskTCTestSuitCheckDO.getTvID()) ;
            clientTCCoveredData = tcAutoCoverManagerService.getTaskCurrentValue(null,rangeId);
        }else {
            clientTCCoveredData = null ;
        }
        if (clientTCCoveredData != null  && clientTCCoveredData.getTotal() > 0){
            double passRate = (double) clientTCCoveredData.getPassed() / (double)clientTCCoveredData.getTotal() * 100 ;
            double coverRate = (double) clientTCCoveredData.getCovered() / (double)clientTCCoveredData.getTotal() * 100 ;
            return "测试通过率"+CommonData.NUMBER_FORMAT.format(passRate)+"%,自动化覆盖率："+CommonData.NUMBER_FORMAT.format(coverRate) + "%";
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
        //暂时不做强校验
        return false;
    }

    @Override
    public String buildPassStandard(CheckInfoStructInterface checkInfoStructInterface) {
        TCTestSuitCheckData tcTestSuitCheckData = null ;
        if (checkInfoStructInterface != null && checkInfoStructInterface instanceof TCTestSuitCheckData){
            tcTestSuitCheckData = (TCTestSuitCheckData) checkInfoStructInterface ;
        }
        if (tcTestSuitCheckData==null){
            return null ;
        }
        Double passPercent = tcTestSuitCheckData.getPassPercent() ;
        Double autoPercent = tcTestSuitCheckData.getAutoPercent();
        String passStandard = "用例通过率"+passPercent+"%,"+"自动化运行通过占比"+ autoPercent+"%" ;
        return passStandard ;
    }

    @Override
    public CheckDataVOInterface getCheckData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.getCheckData] some param is null");
            return null;
        }

        ClientRiskTCTestSuitCheckDO clientRiskTCTestSuitCheckDO = clientRiskTCTestSuitCheckDAO.getRiskTCTestSuit(rangeType.getCode(),rangeId);
        if (clientRiskTCTestSuitCheckDO == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.getCheckData] clientRiskTCTestSuitCheckDO is not exist");
            return null ;
        }
        TCTestSuitCheckDataInfoVO testSuitCheckDataInfoVO = new TCTestSuitCheckDataInfoVO() ;
        testSuitCheckDataInfoVO.setTvID(clientRiskTCTestSuitCheckDO.getTvID());
        testSuitCheckDataInfoVO.setTcTotal(0);
        testSuitCheckDataInfoVO.setAutoCovered(0);
        List<TCTestSuitCoveredDetailVO> detailValueList = tcAutoCoverManagerService.getTaskDetailValue(clientRiskTCTestSuitCheckDO.getProjectID(),clientRiskTCTestSuitCheckDO.getRiskRangeId()) ;
        testSuitCheckDataInfoVO.setDetailList(detailValueList);
        if (!CollectionUtils.isEmpty(detailValueList)){
            int countNumber = 0 ;
            int passCount = 0 ;
            for (TCTestSuitCoveredDetailVO  detailVO : detailValueList){
                if (detailVO!=null && detailVO.isCovered()){
                    countNumber ++;
                }
                if (detailVO != null && detailVO.getResult() != null && detailVO.getResult().equals("通过")){
                    passCount ++ ;
                }
            }
            testSuitCheckDataInfoVO.setTcTotal(detailValueList.size());
            testSuitCheckDataInfoVO.setPassCount(passCount);
            testSuitCheckDataInfoVO.setAutoCovered(countNumber);
        }

        return testSuitCheckDataInfoVO;
    }

    /**
     * 版定任务和对应
     * @param projectId
     * @param riskTask
     * @param tvId
     * @return
     */
    public boolean bindRiskTaskAndTVID(Long projectId ,Long riskTask,long tvId){
        if (riskTask == null){
            //暂时不支持挂在项目下面
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.bindRiskTaskAndTVID] riskTask is null");
            return false ;
        }
        ClientRiskTaskDO clientRiskTaskDO = clientRiskTaskDAO.getClientRiskTaskByTaskId(riskTask) ;
        if (clientRiskTaskDO == null){
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.bindRiskTaskAndTVID] riskTask is not exist");
            return false ;
        }
        if (projectId == null){
            projectId = clientRiskTaskDO.getProjectId() ;
        }
        int count = clientRiskTCTestSuitCheckDAO.insertRiskTCTestSuit(RiskCheckRange.TASK.getCode(),riskTask,tvId,projectId) ;
        if (count > 0 ){
            return true ;
        }else{
            RISK_LOGGER.error("[TCTestSuitCheckManagerService.bindRiskTaskAndTVID] save RiskTCTestSuit exception");
            return false ;
        }
    }
}
