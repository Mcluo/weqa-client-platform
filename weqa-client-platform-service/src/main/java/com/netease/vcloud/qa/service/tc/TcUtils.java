package com.netease.vcloud.qa.service.tc;

import com.netease.vcloud.qa.model.ClientTestCaseInfoDO;
import com.netease.vcloud.qa.service.tc.data.ClientTcData;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 22:11
 */
public class TcUtils {

    /**
     * 已自动化覆盖
     */
    public static final byte IS_AUTO_COVERED = 1 ;
    /**
     * 自动化尚未覆盖
     */
    public static final byte IS_NOT_AUTO_COVERED = 0 ;

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
        //默认不覆盖
        clientTestCaseInfoDO.setIsAutoCovered(IS_NOT_AUTO_COVERED);
        return clientTestCaseInfoDO ;
    }
}
