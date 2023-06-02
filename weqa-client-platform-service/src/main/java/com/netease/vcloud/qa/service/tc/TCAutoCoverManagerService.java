package com.netease.vcloud.qa.service.tc;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientTestCaseProjectCoverInfoDAO;
import com.netease.vcloud.qa.model.ClientTestCaseProjectCoverInfoDO;
import com.netease.vcloud.qa.service.tc.data.ClientExecData;
import com.netease.vcloud.qa.service.tc.data.ClientExecResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TC的自动化覆盖情况服务服务
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/2 17:06
 */
@Service
public class TCAutoCoverManagerService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String TC_TOKEN = TCCommonData.TC_TOKEN ;

    private static final String TOKEN_ARGS = TCCommonData.TOKEN_ARGS ;

    private static final String EXEC_DETAIL_URL = "https://tc.hz.netease.com/api/sa/execution_cases/detail" ;

    private static final String TV_ID_ARGS = "tvId" ;

    @Autowired
    private ClientTestCaseProjectCoverInfoDAO clientTestCaseProjectCoverInfoDAO ;

    private List<ClientExecResultData> getClientExecDataList(Long tvId){
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
            TC_LOGGER.error("[TCAutoCoverManagerService.requestProjectTcData] project tc data result is null");
        }
        return result ;
    }

    /**
     * 添加/更新一个项目-执行集 id列表
     * @param projectId
     * @param tvId
     * @return
     */
    public boolean addOrUpdateTVInfoDetail(Long projectId,Long taskId,Long tvId) {
        if ( tvId == null){
            TC_LOGGER.error("[TCAutoCoverManagerService.addOrUpdateTVInfoDetail] tvId is null" );
            return false ;
        }
        List<ClientExecResultData> clientExecResultDataList = this.getClientExecDataList(tvId) ;
        if (CollectionUtils.isEmpty(clientExecResultDataList)){
            return true ;
        }
        List<ClientTestCaseProjectCoverInfoDO> clientTestCaseProjectCoverInfoDOList = new ArrayList<ClientTestCaseProjectCoverInfoDO>() ;
        for (ClientExecResultData clientExecResultData : clientExecResultDataList){
            if (clientExecResultData == null){
                continue;
            }
            ClientTestCaseProjectCoverInfoDO clientTestCaseProjectCoverInfoDO = new ClientTestCaseProjectCoverInfoDO() ;
            clientTestCaseProjectCoverInfoDO.setProjectId(projectId) ;
            clientTestCaseProjectCoverInfoDO.setTaskId(taskId);
            clientTestCaseProjectCoverInfoDO.setTVId(tvId);
            clientTestCaseProjectCoverInfoDO.setTestSuitId(clientExecResultData.getId());
            clientTestCaseProjectCoverInfoDO.setTestCaseId(clientExecResultData.getCase_id()) ;
            clientTestCaseProjectCoverInfoDO.setIsCover((byte) 0);
            clientTestCaseProjectCoverInfoDOList.add(clientTestCaseProjectCoverInfoDO) ;
        }
        if (!CollectionUtils.isEmpty(clientTestCaseProjectCoverInfoDOList)){
            clientTestCaseProjectCoverInfoDAO.patchInsertTestProjectCoverInfo(clientTestCaseProjectCoverInfoDOList);
        }
        return false ;
    }

    /**
     * 该接口用于同步自动化执行
     * @param projectId
     * @param tcId
     * @return
     */
    public boolean updateTvTCCoveredInfo (Long projectId , Long tcId){
        if (projectId == null ||tcId == null){
            return false ;
        }
        List<ClientTestCaseProjectCoverInfoDO> clientTestCaseProjectCoverInfoDOList = clientTestCaseProjectCoverInfoDAO.getTestProjectCoverInfo(projectId,null,tcId) ;
        if (CollectionUtils.isEmpty(clientTestCaseProjectCoverInfoDOList)){
            return true ;
        }
        int count = clientTestCaseProjectCoverInfoDAO.updateTestProjectCoverInfo(projectId,tcId,(byte)1) ;
        if (count >= clientTestCaseProjectCoverInfoDOList.size()) {
            return true;
        }else {
            return false ;
        }
    }
}
