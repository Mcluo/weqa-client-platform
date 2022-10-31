package com.netease.vcloud.qa.service.tc;

import com.netease.vcloud.qa.CommonData;
import com.netease.vcloud.qa.dao.ClientTestCaseInfoDAO;
import com.netease.vcloud.qa.model.ClientTestCaseInfoDO;
import com.netease.vcloud.qa.service.tc.data.ClientTcData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TC用例同步服务
 * 包括用例同步以及执行情况同步
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 17:01
 */
@Service
public class ClientTcAsyncService {
    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");


    @Autowired
    private ClientTestCaseInfoDAO clientTestCaseInfoDAO ;

    @Autowired
    private TCManagerService tcManagerService ;

    private final static long[] PROJECT_ID_ARRAY = {885L};

    public boolean asyncProjectTestCase(Long start , Long finish){
        List<ClientTcData> clientTcDataList = this.getProjectTestCase(start,finish) ;
        //批量入库
        if (CollectionUtils.isEmpty(clientTcDataList)){
            return true ;
        }
        List<ClientTestCaseInfoDO> clientTestCaseInfoDOList = new ArrayList<>() ;
        boolean flag = false ;
        for (ClientTcData clientTcData : clientTcDataList){
            ClientTestCaseInfoDO clientTestCaseInfoDO = TcUtils.buildTestCaseDOByData(clientTcData) ;
            if (clientTestCaseInfoDO!=null){
                clientTestCaseInfoDOList.add(clientTestCaseInfoDO) ;
            }
        }
        if (CollectionUtils.isEmpty(clientTestCaseInfoDOList)){
            //数据转换为空异常
            TC_LOGGER.error("[ClientTcAsyncService.asyncProjectTestCase]clientTestCaseInfoDOList is null");
            return false ;
        }else {
            int count = clientTestCaseInfoDAO.patchInsertIntoTestCaseInfo(clientTestCaseInfoDOList);
            if (count >= clientTestCaseInfoDOList.size()){
                flag = true ;
            }else {
                TC_LOGGER.error("[ClientTcAsyncService.asyncProjectTestCase] async patch save tc exception");
                flag = false ;
            }
        }
        return flag ;
    }

    public List<ClientTcData> getProjectTestCase(Long start , Long finish){
        if (finish == null){
            finish = System.currentTimeMillis() ;
        }
        if (start == null){
            start = finish - CommonData.ONE_DAY ;
        }
        if (start>finish){
            return null ;
        }
        List<ClientTcData> clientTcData = new ArrayList<>();
        for (long projectId : PROJECT_ID_ARRAY){
            List<ClientTcData> projectClientTcData = tcManagerService.getProjectTcData(projectId,start,finish) ;
            if (!CollectionUtils.isEmpty(projectClientTcData)) {
                clientTcData.addAll(projectClientTcData) ;
            }
        }
        return clientTcData ;
    }

    /**
     * 同步覆盖信息
     * @return
     */
    public boolean asyncAutoCoverInfo(Long testCaseId , boolean isCovered){
        if (testCaseId == null){
            return false ;
        }
        byte isCoveredCode = isCovered ? (byte) 1 : (byte) 0 ;
        int count = clientTestCaseInfoDAO.updateTestCaseCoveredStatus(testCaseId,isCoveredCode) ;
        if (count > 0 ){
            return true ;
        }else {
            TC_LOGGER.warn("[ClientTcAsyncService.asyncAutoCoverInfo]update fail");
            return false;
        }
    }
}
