package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientAutoTestSuitBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTestSuitRelationDAO;
import com.netease.vcloud.qa.dao.ClientScriptTcInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitRelationDO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.auto.data.AutoTCSuitInfoDTO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.auto.view.TestSuitBaseInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private UserInfoService userInfoService ;
    @Autowired
    private ClientScriptTcInfoDAO clientScriptTcInfoDAO ;
    @Autowired
    private AutoTcScriptService autoTcScriptService ;

    public Long addNewTestSuit(String suitName,String creator) throws  AutoTestRunException{
        if (StringUtils.isBlank(suitName)){
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        List<ClientAutoTestSuitBaseInfoDO> clientAutoTestSuitBaseInfoDOS = clientAutoTestSuitBaseInfoDAO.getAutoTestSuitByName(suitName) ;
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

    public boolean deleteTestSuitById(long id) {
        int count = clientAutoTestSuitBaseInfoDAO.deleteAUtoTestSuit(id) ;
        if (count>0){
            return true ;
        }else{
            return false ;
        }
    }

    public boolean addOrUpdateTestAndSuitRelation(Long suitId , List<Long> scriptIdList) throws AutoTestRunException{
        if (suitId == null){
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO = clientAutoTestSuitBaseInfoDAO.getAutoTestSuitById(suitId);
        if (clientAutoTestSuitBaseInfoDO == null) {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SUIT_IS_NOT_EXIST);
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

    public boolean addTestAndSuitRelation(Long suitId ,Long scriptId) throws AutoTestRunException {
        if (suitId == null || scriptId == null) {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION);
        }
        ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO = clientAutoTestSuitBaseInfoDAO.getAutoTestSuitById(suitId);
        if (clientAutoTestSuitBaseInfoDO == null) {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SUIT_IS_NOT_EXIST);
        }
        List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList = new ArrayList<ClientAutoTestSuitRelationDO>();
        ClientAutoTestSuitRelationDO clientAutoTestSuitRelationDO = new ClientAutoTestSuitRelationDO();
        clientAutoTestSuitRelationDO.setSuitId(suitId);
        clientAutoTestSuitRelationDO.setScriptId(scriptId);
        clientAutoTestSuitRelationDOList.add(clientAutoTestSuitRelationDO);
        int count = clientAutoTestSuitRelationDAO.patchInsertClientTestRelation(clientAutoTestSuitRelationDOList);
        if (count > 0) {
            return true;
        } else {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION);
        }
    }


    public boolean deleteTestAndSuitRelation(Long suitId ,Long scriptId) throws AutoTestRunException{
        if (suitId == null || scriptId == null) {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION);
        }
        ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO = clientAutoTestSuitBaseInfoDAO.getAutoTestSuitById(suitId);
        if (clientAutoTestSuitBaseInfoDO == null) {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SUIT_IS_NOT_EXIST);
        }
         int count = clientAutoTestSuitRelationDAO.deleteClientTestRelationBySuitAndScript(suitId,scriptId) ;
        if (count > 0){
            return true ;
        }else {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION);
        }
    }


    public List<TestSuitBaseInfoVO> getTestSuitBaseInfo(String queryKey){
        List<ClientAutoTestSuitBaseInfoDO> clientAutoTestSuitBaseInfoDOList = clientAutoTestSuitBaseInfoDAO.queryAutoTestSuitByName(queryKey) ;
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = new ArrayList<TestSuitBaseInfoVO>() ;
        if (!CollectionUtils.isEmpty(clientAutoTestSuitBaseInfoDOList)){
            Set<String> userSet = new HashSet<>() ;
            for (ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO : clientAutoTestSuitBaseInfoDOList){
                if (clientAutoTestSuitBaseInfoDO == null || StringUtils.isBlank(clientAutoTestSuitBaseInfoDO.getSuitOwner())){
                    continue;
                }
                userSet.add(clientAutoTestSuitBaseInfoDO.getSuitOwner()) ;
            }
            Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(userSet) ;
            for (ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO : clientAutoTestSuitBaseInfoDOList){
                if (clientAutoTestSuitBaseInfoDO == null){
                    continue;
                }
                TestSuitBaseInfoVO testSuitBaseInfoVO = new TestSuitBaseInfoVO() ;
                testSuitBaseInfoVO.setId(clientAutoTestSuitBaseInfoDO.getId());
                testSuitBaseInfoVO.setName(clientAutoTestSuitBaseInfoDO.getSuitName());
                UserInfoBO userInfoBO = userInfoBOMap.get(clientAutoTestSuitBaseInfoDO.getSuitOwner()) ;
                UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO) ;
                testSuitBaseInfoVO.setOwner(userInfoVO);
                testSuitBaseInfoVOList.add(testSuitBaseInfoVO) ;
            }
        }
        return testSuitBaseInfoVOList ;
    }

    public List<AutoScriptInfoVO> getTestSuitScriptInfo(Long suitId) throws  AutoTestRunException{
        if (suitId == null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO = clientAutoTestSuitBaseInfoDAO.getAutoTestSuitById(suitId) ;
        if (clientAutoTestSuitBaseInfoDO==null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SUIT_IS_NOT_EXIST) ;
        }
        List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList = clientAutoTestSuitRelationDAO.getAutoTestSuitRelationListBySuit(suitId) ;
        List<AutoScriptInfoVO> autoScriptInfoVOList = new ArrayList<AutoScriptInfoVO>() ;
        if (CollectionUtils.isEmpty(clientAutoTestSuitRelationDOList)){
            return autoScriptInfoVOList ;
        }
        Set<Long> clientAutoTestScriptSet = new HashSet<Long>() ;
        for (ClientAutoTestSuitRelationDO clientAutoTestSuitRelationDO : clientAutoTestSuitRelationDOList){
           clientAutoTestScriptSet.add(clientAutoTestSuitRelationDO.getScriptId()) ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScriptSet(clientAutoTestScriptSet) ;
        autoScriptInfoVOList = autoTcScriptService.buildAutoScriptInfoVOByDOList(clientScriptTcInfoDOList) ;
        return autoScriptInfoVOList ;
    }

    public boolean initTestSuitScriptInfo(AutoTCSuitInfoDTO autoTCSuitInfoDTO) throws AutoTestRunException{
        if (autoTCSuitInfoDTO == null){
            return false ;
        }
        boolean result = false ;
        Long suitId = this.addNewTestSuit(autoTCSuitInfoDTO.getSuitName(),"system") ;
        List<Long> scriptIdList = autoTcScriptService.addScriptInfo(autoTCSuitInfoDTO.getScriptList()) ;
        if (suitId !=null && scriptIdList!=null){
            result = this.addOrUpdateTestAndSuitRelation(suitId,scriptIdList) ;
        }
        return result ;
    }

}
