package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.AutoTestResultDAO;
import com.netease.vcloud.qa.model.AutoTestResultDO;
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

    public boolean saveAutoTestResult(String runInfo, String caseName, String caseDetail, int success, int fail , JSONObject result,Long tcId) {
        if (StringUtils.isBlank(runInfo)||runInfo.startsWith("auto")){
            return false ;
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

    private String buildErrorInfoByResult(JSONObject result){
        String errorMessage = result.getString(RESULT_ERROR_MESSAGE_ARGS) ;
        if (StringUtils.isBlank(errorMessage)){
            return null ;
        }
        //todo 暂时全量返回，否则特殊处理
        return errorMessage ;
    }

}
