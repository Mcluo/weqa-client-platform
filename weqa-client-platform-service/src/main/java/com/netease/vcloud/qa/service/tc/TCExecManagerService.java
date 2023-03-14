package com.netease.vcloud.qa.service.tc;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientTestCaseExecDAO;
import com.netease.vcloud.qa.model.ClientTestCaseExecDO;
import com.netease.vcloud.qa.service.tc.data.ClientExecData;
import com.netease.vcloud.qa.service.tc.data.ClientExecDataBO;
import com.netease.vcloud.qa.service.tc.data.ClientExecResultData;
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
 * on 2023/3/13 17:48
 */
@Service
public class TCExecManagerService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String TC_TOKEN = TCCommonData.TC_TOKEN ;

    private static final String TOKEN_ARGS = TCCommonData.TOKEN_ARGS ;

    private static final String EXEC_DETAIL_URL = "https://tc.hz.netease.com/api/sa/execution_cases/detail" ;

    private static final String TV_ID_ARGS = "tvId" ;


    public static final int UN_CARRY_OUT = 1 ;

    public static final int ACCEPT = 2 ;

    public static final int FAIL = 3 ;

    public static final int IGNORE = 4 ;

    @Autowired
    private ClientTestCaseExecDAO clientTestCaseExecDAO ;

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

    public boolean addOrUpdateTVDetailInfo(Long tvID){
        if (tvID == null){
            return false ;
        }
        boolean flag = false ;
        List<ClientExecResultData> clientExecResultDataList = this.getClientExecDataList(tvID) ;
        int total = 0 , accept = 0 , fail = 0 , unCarryOut = 0 , ignore = 0 ;
        if (!CollectionUtils.isEmpty(clientExecResultDataList)){
            for (ClientExecResultData clientExecResultData : clientExecResultDataList){
                if (clientExecResultData == null){
                    continue;
                }
                Integer resultId = clientExecResultData.getResult_id() ;
                if (resultId == null){
                    continue;
                }
                total++ ;
                switch (resultId){
                    case UN_CARRY_OUT:
                        unCarryOut++ ;
                        break;
                    case ACCEPT:
                        accept++;
                        break;
                    case FAIL:
                        fail++;
                        break;
                    case IGNORE:
                        ignore++;
                        break;
                }
            }
        }
        ClientTestCaseExecDO clientTestCaseExecDO = new ClientTestCaseExecDO() ;
        clientTestCaseExecDO.setTvID(tvID);
        clientTestCaseExecDO.setTotal(total);
        clientTestCaseExecDO.setAccept(accept);
        clientTestCaseExecDO.setFail(fail);
        clientTestCaseExecDO.setUnCarryOut(unCarryOut);
        clientTestCaseExecDO.setIgnore(ignore);
        int count = clientTestCaseExecDAO.insertOrUpdateTestExec(clientTestCaseExecDO) ;
        if (count > 0 ){
            flag = true ;
        }else {
            flag = false ;
        }
        return flag ;
    }

    public ClientExecDataBO getTVDetailInfo(Long tvID){
        ClientTestCaseExecDO clientTestCaseExecDO = clientTestCaseExecDAO.getClientTestCaseExecDO(tvID) ;
        if (clientTestCaseExecDO == null) {
            return null;
        }
        ClientExecDataBO clientExecDataBO = new ClientExecDataBO() ;
        clientExecDataBO.setTvID(tvID);
        clientExecDataBO.setAccept(clientTestCaseExecDO.getAccept());
        clientExecDataBO.setFail(clientTestCaseExecDO.getFail());
        clientExecDataBO.setUnCarryOut(clientTestCaseExecDO.getUnCarryOut());
        clientExecDataBO.setIgnore(clientExecDataBO.getIgnore());
        clientExecDataBO.setTotal(clientExecDataBO.getTotal());
        return clientExecDataBO ;
    }

}
