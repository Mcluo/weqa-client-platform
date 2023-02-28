package com.netease.vcloud.qa.service.risk.source.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.auto.ScriptRunStatus;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.dao.ClientRiskAutoTaskDAO;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.model.ClientRiskAutoTaskDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.risk.source.manager.data.AutoTestCheckData;
import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import com.netease.vcloud.qa.service.risk.source.struct.view.AutoTestCheckDataInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/17 17:46
 */
@Service
public class AutoTestCheckManageService  implements  RiskTestCheckManageInterface{

    private static final String DEFAULT_CURRENT_VALUE = "" ;

    @Autowired
    private ClientRiskAutoTaskDAO clientRiskAutoTaskDAO ;

    @Autowired
    private ClientAutoScriptRunInfoDAO clientAutoScriptRunInfoDAO ;

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Override
    public void asyncDate(RiskCheckRange rangeType , Long rangeId) {
        //自动化相关不需要同步数据
        return;
    }

    @Override
    public String getCurrentData(RiskCheckRange rangeType , Long rangeId) {
        if (rangeType == null || rangeId == null ){
            return DEFAULT_CURRENT_VALUE ;
        }
        ClientRiskAutoTaskDO clientRiskAutoTaskDO = clientRiskAutoTaskDAO.getRiskAndAutoTaskByRisk(rangeType.getCode(), rangeId) ;
        if (clientRiskAutoTaskDO == null){
            return DEFAULT_CURRENT_VALUE ;
        }
        Long autoTaskId = clientRiskAutoTaskDO.getAutoTaskId() ;
        AutoTestCheckDataInfoVO taskStatisticInfoVO = this.getAutoTestCheckStatistic(autoTaskId) ;
        if (taskStatisticInfoVO == null){
            return DEFAULT_CURRENT_VALUE ;
        }else {
            return taskStatisticInfoVO.getSuccessRate() ;
        }
    }

    @Override
    public CheckInfoStructInterface buildCheckInfo(String checkInfoStructStr) {
        AutoTestCheckData autoTestCheckData = JSONObject.parseObject(checkInfoStructStr,AutoTestCheckData.class) ;
        return autoTestCheckData;
    }

    @Override
    public boolean hasRisk(CheckInfoStructInterface checkInfoStructInterface, String currentData) {
        if(StringUtils.equals(DEFAULT_CURRENT_VALUE,currentData)) {
            return true ;
        }
        if (checkInfoStructInterface instanceof AutoTestCheckData){
            AutoTestCheckData autoTestCheckData = (AutoTestCheckData) checkInfoStructInterface ;
            Double passPercent = autoTestCheckData.getPassPercent() ;
            boolean hasRisk = false ;
            if (passPercent==null){
                return false ;
            }else {
                try {
                    currentData = currentData.split("%")[0] ;
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


    @Override
    public String buildPassStandard(CheckInfoStructInterface checkInfoStructInterface) {
        if (checkInfoStructInterface instanceof AutoTestCheckData) {
            AutoTestCheckData autoTestCheckData = (AutoTestCheckData) checkInfoStructInterface;
            Double passPercent = autoTestCheckData.getPassPercent();
            String passStandard = "自动化通过率大于"+passPercent+"%" ;
            return passStandard ;
        }else {
            return null;
        }
    }


    @Override
    public CheckDataVOInterface getCheckData(RiskCheckRange rangeType, Long rangeId) {
        if (rangeType == null || rangeId == null ){
            return null ;
        }
        ClientRiskAutoTaskDO clientRiskAutoTaskDO = clientRiskAutoTaskDAO.getRiskAndAutoTaskByRisk(rangeType.getCode(), rangeId) ;
        if (clientRiskAutoTaskDO == null){
            return null ;
        }
        Long autoTaskId = clientRiskAutoTaskDO.getAutoTaskId() ;
        return this.getAutoTestCheckStatistic(autoTaskId);
    }

    public boolean bindRiskTaskAndAutoTask(long riskTask,long autoTask){
        int count = clientRiskAutoTaskDAO.insertRiskAndAutoTask(RiskCheckRange.TASK.getCode(), riskTask,autoTask) ;
        if (count > 0) {
            return true ;
        }else{
            return false ;
        }
    }


    private AutoTestCheckDataInfoVO getAutoTestCheckStatistic(Long taskId){
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(taskId) ;
        if (clientAutoTaskInfoDO == null){
            return null ;
        }
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = clientAutoScriptRunInfoDAO.getClientAutoScriptRunInfoByTaskId(taskId) ;
        Map<ScriptRunStatus,Integer> statusStatisticMap = new HashMap<ScriptRunStatus,Integer>() ;
        if (!CollectionUtils.isEmpty(clientAutoScriptRunInfoDOList)) {
            //处理脚本计数
            for (ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO : clientAutoScriptRunInfoDOList) {
                ScriptRunStatus scriptRunStatus = ScriptRunStatus.getStatusByCode(clientAutoScriptRunInfoDO.getExecStatus()) ;
                if (scriptRunStatus!=null) {
                    Integer scriptCount = statusStatisticMap.get(scriptRunStatus) ;
                    if (scriptCount == null){
                        scriptCount = 1 ;
                    }else {
                        scriptCount = scriptCount + 1 ;
                    }
                    statusStatisticMap.put(scriptRunStatus , scriptCount) ;
                }
            }
            //处理统计结果
            AutoTestCheckDataInfoVO taskStatisticInfoVO = new AutoTestCheckDataInfoVO() ;
            taskStatisticInfoVO.setAutoTaskId(taskId);
            taskStatisticInfoVO.setAutoTaskName(clientAutoTaskInfoDO.getTaskName());
            taskStatisticInfoVO.setWaitingNumber(statusStatisticMap.get(ScriptRunStatus.INIT)==null?0:statusStatisticMap.get(ScriptRunStatus.INIT));
            taskStatisticInfoVO.setRunningNumber(statusStatisticMap.get(ScriptRunStatus.RUNNING)==null?0:statusStatisticMap.get(ScriptRunStatus.RUNNING));
            taskStatisticInfoVO.setSuccessNumber(statusStatisticMap.get(ScriptRunStatus.SUCCESS)==null?0:statusStatisticMap.get(ScriptRunStatus.SUCCESS));
            taskStatisticInfoVO.setCancelNumber(statusStatisticMap.get(ScriptRunStatus.CANCEL)==null?0:statusStatisticMap.get(ScriptRunStatus.CANCEL));
            int failCount = statusStatisticMap.get(ScriptRunStatus.FAIL)==null?0:statusStatisticMap.get(ScriptRunStatus.FAIL);
            int exceptionCount = statusStatisticMap.get(ScriptRunStatus.WARNING)==null?0:statusStatisticMap.get(ScriptRunStatus.WARNING) ;
            taskStatisticInfoVO.setFailNumber(failCount + exceptionCount);
            taskStatisticInfoVO.setTotal(clientAutoScriptRunInfoDOList.size());
            if (taskStatisticInfoVO.getWaitingNumber()>0){
                taskStatisticInfoVO.setSuccessRate("-");
            }else {
                if (taskStatisticInfoVO.getTotal() == 0){
                    taskStatisticInfoVO.setSuccessRate(0 + "%");
                }else {
                    double successRate = taskStatisticInfoVO.getSuccessNumber() * 100 / taskStatisticInfoVO.getTotal();
                    taskStatisticInfoVO.setSuccessRate(successRate + "%");
                }
            }
            return taskStatisticInfoVO;
        }else {
            return null ;
        }
    }
}
