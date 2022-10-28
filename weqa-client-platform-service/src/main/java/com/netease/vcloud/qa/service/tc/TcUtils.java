package com.netease.vcloud.qa.service.tc;

import com.netease.vcloud.qa.model.ClientTestCaseInfoDO;
import com.netease.vcloud.qa.service.tc.data.ClientTcData;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 22:11
 */
public class TcUtils {


    public static ClientTestCaseInfoDO buildTestCaseDOByData(ClientTcData clientTcData){
        if (clientTcData == null){
            return null ;
        }
        return  buildTestCaseDOByData(clientTcData.getProjectId(),clientTcData) ;
    }
    public static ClientTestCaseInfoDO buildTestCaseDOByData(Long projectId,ClientTcData clientTcData){
        if (clientTcData == null){
            return null ;
        }
        ClientTestCaseInfoDO clientTestCaseInfoDO = new ClientTestCaseInfoDO() ;
        clientTestCaseInfoDO.setCaseId(clientTcData.getCaseId());
        clientTestCaseInfoDO.setCaseName(clientTcData.getCaseName());
        clientTestCaseInfoDO.setProjectId(projectId);
        clientTestCaseInfoDO.setExecutionSteps(clientTcData.getExecutionSteps());
        clientTestCaseInfoDO.setExpectedResult(clientTcData.getExpectedResult());
        return clientTestCaseInfoDO ;
    }
}
