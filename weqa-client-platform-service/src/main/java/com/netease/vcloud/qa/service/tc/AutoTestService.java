package com.netease.vcloud.qa.service.tc;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.AutoTestResultDAO;
import com.netease.vcloud.qa.model.AutoTestResultDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/31 11:38
 */
@Service
public class AutoTestService {
    private static final String RESULT_ERROR_MESSAGE_ARGS = "errorMessage" ;

    @Autowired
    private AutoTestResultDAO autoTestResultDAO ;

    public boolean saveAutoTestResult(String runInfo, String caseName, String caseDetail, int success, int fail , JSONObject result,Long tcId) {
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
