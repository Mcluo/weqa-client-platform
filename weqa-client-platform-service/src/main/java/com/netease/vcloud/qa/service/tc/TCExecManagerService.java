package com.netease.vcloud.qa.service.tc;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.service.tc.data.ClientExecData;
import com.netease.vcloud.qa.service.tc.data.ClientExecResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/13 17:48
 */
@Service
public class TCExecManagerService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String TC_TOKEN = TCCommonData.TC_TOKEN ;

    private static final String TOKEN_ARGS = TCCommonData.TOKEN_ARGS ;

    private static final String EXEC_DETAIL_URL = "https://tc.hz.netease.com/api/sa/execution_cases/detail" ;

    private static final String TV_ID_ARGS = "tvId" ;

    public List<ClientExecResultData> getClientExecDataList(Long tvId){
        if (tvId == null){
            return null ;
        }
        JSONObject jsonObject  = this.getTCExecutionCaseDetail(tvId) ;
        if (jsonObject == null){
            return null ;
        }
        ClientExecData clientExecData = jsonObject.toJavaObject(ClientExecData.class) ;
        if (clientExecData == null){
            return null ;
        }
        List<ClientExecResultData> clientExecResultDataList = clientExecData.getResult() ;
        return clientExecResultDataList ;
    }


    private JSONObject getTCExecutionCaseDetail(Long tvId){
        if (tvId == null){
            TC_LOGGER.error("[TCExecManagerService.getTCExecutionCaseDetail] tvId is null");
            return null ;
        }
        StringBuilder baseUrlStr = new StringBuilder(EXEC_DETAIL_URL) ;
        baseUrlStr.append("?").append(TV_ID_ARGS).append("=").append(tvId) ;
        Map<String,String> headerMap = new HashMap<String, String>() ;
        headerMap.put(TOKEN_ARGS,TC_TOKEN) ;
        JSONObject result = HttpUtils.getInstance().get(baseUrlStr.toString(),headerMap) ;
        if (result == null){
            TC_LOGGER.error("[TCManagerService.requestProjectTcData] project tc data result is null");
        }
        return result ;
    }

}
