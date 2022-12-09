package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.dao.ClientAutoTestSuitBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTestSuitRelationDAO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitRelationDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/8 13:55
 */
@Service
public class AutoTestTestSuitService {
    @Autowired
    private ClientAutoTestSuitBaseInfoDAO clientAutoTestSuitBaseInfoDAO ;

    @Autowired
    private ClientAutoTestSuitRelationDAO clientAutoTestSuitRelationDAO ;

    public Long addNewTestSuit(String suitName,String creator) throws  AutoTestRunException{
        if (StringUtils.isBlank(suitName)){
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        List<ClientAutoTestSuitBaseInfoDO> clientAutoTestSuitBaseInfoDOS = clientAutoTestSuitBaseInfoDAO.queryAutoTestSuitByName(suitName) ;
        if (!CollectionUtils.isEmpty(clientAutoTestSuitBaseInfoDOS)) {
            //已经存在
            return clientAutoTestSuitBaseInfoDOS.get(0).getId() ;
        }
        ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO = new ClientAutoTestSuitBaseInfoDO() ;
        clientAutoTestSuitBaseInfoDO.setSuitOwner(creator);
        clientAutoTestSuitBaseInfoDO.setSuitName(suitName);
        int count = clientAutoTestSuitBaseInfoDAO.addNewAutoTestSuit(clientAutoTestSuitBaseInfoDO) ;
        if (count > 0){
            return  clientAutoTestSuitBaseInfoDO.getId() ;
        }else {
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION) ;
        }
    }

    public boolean addOrUpdateTestAndSuitRelation(Long suitId , List<Long> scriptIdList) throws AutoTestRunException{
        if (suitId == null){
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        clientAutoTestSuitRelationDAO.deleteClientTestRelationBySuit(suitId) ;
        if (CollectionUtils.isEmpty(scriptIdList)){
            return true ;
        }
        List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList = new ArrayList<ClientAutoTestSuitRelationDO>() ;
        for (Long scriptId :scriptIdList){
            ClientAutoTestSuitRelationDO clientAutoTestSuitRelationDO = new ClientAutoTestSuitRelationDO() ;
            clientAutoTestSuitRelationDO.setSuitId(suitId);
            clientAutoTestSuitRelationDO.setScriptId(scriptId);
            clientAutoTestSuitRelationDOList.add(clientAutoTestSuitRelationDO) ;
        }
        int count = clientAutoTestSuitRelationDAO.patchInsertClientTestRelation(clientAutoTestSuitRelationDOList) ;
        if (count>=clientAutoTestSuitRelationDOList.size()){
            return true ;
        }else {
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION) ;
        }
    }

}
