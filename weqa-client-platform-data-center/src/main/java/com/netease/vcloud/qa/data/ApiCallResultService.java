package com.netease.vcloud.qa.data;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.ClientDataCenterApiCallResultDAO;
import com.netease.vcloud.qa.data.data.ApiCallData;
import com.netease.vcloud.qa.data.data.ApiCallResultVO;
import com.netease.vcloud.qa.data.data.ApiCallUnitData;
import com.netease.vcloud.qa.model.ClientDataCenterApiCallResultDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 14:29
 */
@Service
public class ApiCallResultService {

    @Autowired
    private ClientDataCenterApiCallResultDAO clientDataCenterApiCallResultDAO ;


    public boolean pitchUploadApiCallResult(ApiCallData apiCallData) {
//        System.out.print(JSONObject.toJSONString(apiCallData));
        List<ClientDataCenterApiCallResultDO> dataCenterApiCallResultDOList = this.buildApiCallResultList(apiCallData) ;
        if (CollectionUtils.isEmpty(dataCenterApiCallResultDOList)){
            return false ;
        }
        int count = clientDataCenterApiCallResultDAO.pitchInsertApiCallResult(dataCenterApiCallResultDOList) ;
        if (count >= dataCenterApiCallResultDOList.size()) {
            return true;
        }else {
            return false ;
        }
    }


    private List<ClientDataCenterApiCallResultDO> buildApiCallResultList(ApiCallData apiCallData){
        if (apiCallData == null || CollectionUtils.isEmpty(apiCallData.getData())){
            return null ;
        }
        List<ClientDataCenterApiCallResultDO> apiCallResultList = new ArrayList<>();
        List<ApiCallUnitData> apiCallUnitDataList = apiCallData.getData() ;
        for (ApiCallUnitData apiCallUnitData : apiCallUnitDataList){
            ClientDataCenterApiCallResultDO clientDataCenterApiCallResultDO = new ClientDataCenterApiCallResultDO() ;
            clientDataCenterApiCallResultDO.setTaskCaseId(apiCallData.getTaskCaseId());
            clientDataCenterApiCallResultDO.setUserId(apiCallData.getUserId());
            clientDataCenterApiCallResultDO.setPlatform(apiCallData.getPlatform());
            clientDataCenterApiCallResultDO.setChannelName(apiCallData.getChannelName());
            clientDataCenterApiCallResultDO.setMethod(apiCallUnitData.getMethod());
            clientDataCenterApiCallResultDO.setResult(apiCallUnitData.getResult());
            clientDataCenterApiCallResultDO.setParams(apiCallUnitData.getParams());
            if (apiCallUnitData.getTimeStamp()!= null){
                clientDataCenterApiCallResultDO.setCallTime(new Date(apiCallUnitData.getTimeStamp()));
            }
            apiCallResultList.add(clientDataCenterApiCallResultDO);
        }
        return apiCallResultList ;
    }

    public List<ApiCallResultVO> queryApiCallResult(Long testCaseId ,Long userId) {
        if (testCaseId==null){
            return null ;
        }
        List<ApiCallResultVO> apiCallResultVOList = new ArrayList<>();
        List<ClientDataCenterApiCallResultDO> resultDOList = clientDataCenterApiCallResultDAO.queryApiCallResult(testCaseId,userId) ;
        for (ClientDataCenterApiCallResultDO clientDataCenterApiCallResultDO : resultDOList){
            if (clientDataCenterApiCallResultDO == null){
                continue;
            }
            ApiCallResultVO apiCallResultVO = new ApiCallResultVO() ;
            apiCallResultVO.setTaskCaseId(clientDataCenterApiCallResultDO.getTaskCaseId());
            apiCallResultVO.setUserId(clientDataCenterApiCallResultDO.getUserId());
            apiCallResultVO.setPlatform(clientDataCenterApiCallResultDO.getPlatform());
            apiCallResultVO.setChannelName(clientDataCenterApiCallResultDO.getChannelName());
            if (clientDataCenterApiCallResultDO.getCallTime()!= null) {
                apiCallResultVO.setCallTime(clientDataCenterApiCallResultDO.getCallTime().getTime());
            }
            apiCallResultVO.setMethod(clientDataCenterApiCallResultDO.getMethod());
            apiCallResultVO.setResult(clientDataCenterApiCallResultDO.getResult());
            apiCallResultVO.setParams(clientDataCenterApiCallResultDO.getParams());
            apiCallResultVOList.add(apiCallResultVO);
        }
        Collections.sort(apiCallResultVOList);
        return apiCallResultVOList ;
    }
}
