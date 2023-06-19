package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.AutoTestResultDAO;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.dao.ClientScriptTcInfoDAO;
import com.netease.vcloud.qa.model.AutoTestResultDO;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;
import com.netease.vcloud.qa.service.tc.TCAutoCoverManagerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/31 11:38
 */
@Service
public class AutoTestService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String RESULT_ERROR_MESSAGE_ARGS = "errorMessage" ;

    @Autowired
    private AutoTestResultDAO autoTestResultDAO ;

    @Autowired
    private AutoCoveredService autoCoveredService ;

    @Autowired
    private ClientAutoScriptRunInfoDAO clientAutoScriptRunInfoDAO ;

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;

    @Autowired
    private ClientScriptTcInfoDAO clientScriptTcInfoDAO ;

    @Autowired
    private TCAutoCoverManagerService tcAutoCoverManagerService ;

    @Deprecated
    public boolean saveAutoTestResult(String runInfo, String caseName, String caseDetail, int success, int fail , JSONObject result,Long tcId) {
        if (StringUtils.isBlank(runInfo)||runInfo.startsWith("auto")){
            TC_LOGGER.warn("[AutoTestService.saveAutoTestResult] run info is invalid");
            return true ;
        }
        AutoTestResultDO autoTestResultDO = new AutoTestResultDO();
        autoTestResultDO.setRunInfo(runInfo);
        autoTestResultDO.setCaseName(caseName);
        autoTestResultDO.setCaseDetail(caseDetail);
        autoTestResultDO.setSuccessNumber(success);
        autoTestResultDO.setFailNumber(fail);
        if (result!=null) {
            autoTestResultDO.setRunResult(JSONObject.toJSONString(result));
            String errorInfo = this.buildErrorInfoByResult(result) ;
            autoTestResultDO.setErrorInfo(errorInfo);
        }
        //新增tc id
        autoTestResultDO.setTcId(tcId);
        int count = autoTestResultDAO.insertIntoAutoTestResult(autoTestResultDO);
        if (count > 0 ){
            if (tcId!=null) {
                boolean flag = autoCoveredService.checkAndMarkTestCase(tcId);
                if (!flag){
                    //更新覆盖情况失败，可能为tcid不存在
                    TC_LOGGER.warn("[AutoTestService.saveAutoTestResult] check testCase markStatus false");
                }
            }
            return true ;
        }else {
            return false ;
        }
    }

    public boolean saveAutoTestResult(Long scriptId, boolean isSuccess, String result) {
        ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = clientAutoScriptRunInfoDAO.getClientAutoScriptRunInfoById(scriptId);
        if (clientAutoScriptRunInfoDO == null) {
            TC_LOGGER.error("[AutoTestService.saveAutoTestResult] clientAutoScriptRunInfoDO is null");
            return false;
        }
        long taskId = clientAutoScriptRunInfoDO.getTaskId();
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(taskId);
        if (clientAutoTaskInfoDO == null) {
            TC_LOGGER.error("[AutoTestService.saveAutoTestResult] taskInfo is null");
            return false;
        }
        String operator = StringUtils.isBlank(clientAutoTaskInfoDO.getOperator()) ? "auto" : clientAutoTaskInfoDO.getOperator().split("@")[0];
        String runInfo = operator + "_weqa_" + clientAutoTaskInfoDO.getGitBranch();
        int success =0 , fail = 0;
        if (isSuccess) {
            success = 1;
        }else {
            fail = 1;
        }
        JSONObject jsonObject = new JSONObject() ;
        jsonObject.put(RESULT_ERROR_MESSAGE_ARGS,result) ;
        boolean flag = this.saveAutoTestResult(runInfo, clientAutoScriptRunInfoDO.getScriptName(), clientAutoScriptRunInfoDO.getScriptDetail(), success, fail, jsonObject, null);
        if (!flag) {
            TC_LOGGER.error("[AutoTestService.saveAutoTestResult] save auto test Result failed");
            return false;
        }
        Long projectId = clientAutoTaskInfoDO.getProjectId() ;
        Long scriptTcId = clientAutoScriptRunInfoDO.getScriptTcId();
        if (projectId != null && scriptTcId != null) {
            ClientScriptTcInfoDO clientScriptTcInfoDO = clientScriptTcInfoDAO.getClientScriptById(scriptTcId) ;
            if (clientScriptTcInfoDO != null && clientScriptTcInfoDO.getTcId()!=null) {
                flag = this.managerProject(projectId, clientScriptTcInfoDO.getTcId());
            }
        }
        return flag;
    }

    private boolean managerProject(Long projectId , Long tcId){
        if (projectId == null || tcId == null){
            return true;
        }
        //1，更新TC的覆盖，
        if (tcId!=null) {
            boolean flag = autoCoveredService.checkAndMarkTestCase(tcId);
            if (!flag){
                //更新覆盖情况失败，可能为tcid不存在
                TC_LOGGER.warn("[AutoTestService.saveAutoTestResult] check testCase markStatus false");
            }
        }
        //2，更新具体项目下面执行集的覆盖情况
        tcAutoCoverManagerService.updateTvTCCoveredInfo(projectId, tcId) ;
        return true;
    }

    private String buildErrorInfoByResult(JSONObject result){
        String errorMessage = result.getString(RESULT_ERROR_MESSAGE_ARGS) ;
        if (StringUtils.isBlank(errorMessage)){
            return null ;
        }
        if (errorMessage.length() > 64) {
            //超长后会写入异常信息
            errorMessage = errorMessage.substring(0,63) ;
        }
        return errorMessage ;
    }

}
