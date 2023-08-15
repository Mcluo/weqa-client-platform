package com.netease.vcloud.qa.service.tag;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.dao.ClientAutoTagBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTagRelationDAO;
import com.netease.vcloud.qa.dao.ClientAutoTestSuitBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientScriptTcInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoTagRelationDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;

import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.auto.view.TestSuitBaseInfoVO;
import com.netease.vcloud.qa.tag.TagRelationType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 13:53
 */
@Service
public class AutoBuildTestService {

    @Autowired
    private ClientAutoTagRelationDAO clientAutoTagRelationDAO ;


    @Autowired
    private ClientAutoTestSuitBaseInfoDAO clientAutoTestSuitBaseInfoDAO ;


    @Autowired
    private ClientScriptTcInfoDAO clientScriptTcInfoDAO ;
    /**
     * 获取全量用例
     * 不含执行集用例
     * @return
     */
    public Set<AutoScriptInfoVO>  getAutoScriptInfoVOListByTagId(long tagId) {
        Set<Long> tagIdSet = new HashSet<>() ;
        tagIdSet.add(tagId) ;
        Set<AutoScriptInfoVO> autoScriptInfoVOSet = new HashSet<>() ;
        List<ClientAutoTagRelationDO> clientAutoTagRelationDOList = clientAutoTagRelationDAO.queryAutoTagRelationByTagIds(TagRelationType.TEST_CASE.getCode(),tagIdSet) ;
        if (CollectionUtils.isEmpty(clientAutoTagRelationDOList)){
            return autoScriptInfoVOSet ;
        }
        Set<Long> scriptIdSet = new HashSet<>() ;
        for (ClientAutoTagRelationDO clientAutoTagRelationDO : clientAutoTagRelationDOList) {
            if (clientAutoTagRelationDO == null){
                continue;
            }
            scriptIdSet.add(clientAutoTagRelationDO.getRelationId()) ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScriptSet(scriptIdSet) ;
        if (CollectionUtils.isEmpty(clientScriptTcInfoDOList)){
            return autoScriptInfoVOSet ;
        }
        for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
            if (clientScriptTcInfoDO == null){
                continue;
            }
            AutoScriptInfoVO autoScriptInfoVO = new AutoScriptInfoVO() ;
            autoScriptInfoVO.setId(clientScriptTcInfoDO.getId());
            autoScriptInfoVO.setName(clientScriptTcInfoDO.getScriptName());
            autoScriptInfoVO.setDetail(clientScriptTcInfoDO.getScriptDetail());
            autoScriptInfoVO.setExecClass(clientScriptTcInfoDO.getExecClass());
            autoScriptInfoVO.setExecMethod(clientScriptTcInfoDO.getExecMethod());
            autoScriptInfoVO.setExecParam(clientScriptTcInfoDO.getExecParam());
            autoScriptInfoVO.setTcId(clientScriptTcInfoDO.getTcId());
            autoScriptInfoVOSet.add(autoScriptInfoVO) ;
        }
        return autoScriptInfoVOSet ;
    }

    /**
     * 获取全量用例集合
     * @return
     */
    public List<TestSuitBaseInfoVO> getAutoSuitListByTagId(long tagId) {
        Set<Long> tagIdSet = new HashSet<Long>() ;
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = new ArrayList<>() ;
        tagIdSet.add(tagId) ;
        List<ClientAutoTagRelationDO> clientAutoTagRelationDOList = clientAutoTagRelationDAO.queryAutoTagRelationByTagIds(TagRelationType.TEST_SUITE.getCode(), tagIdSet) ;
        if (CollectionUtils.isEmpty(clientAutoTagRelationDOList)){
            return testSuitBaseInfoVOList ;
        }
        Set<Long> suitIdList = new HashSet<>() ; ;
        for (ClientAutoTagRelationDO clientAutoTagRelationDO : clientAutoTagRelationDOList) {
            if (clientAutoTagRelationDO == null) {
                continue;
            }
            suitIdList.add(clientAutoTagRelationDO.getRelationId()) ;
        }
        List<ClientAutoTestSuitBaseInfoDO> clientAutoTestSuitBaseInfoDOList = clientAutoTestSuitBaseInfoDAO.queryAutoTestSuitByIdSet(suitIdList) ;
        for (ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO : clientAutoTestSuitBaseInfoDOList){
            if (clientAutoTestSuitBaseInfoDO == null){
                continue;
            }
            TestSuitBaseInfoVO testSuitBaseInfoVO = new TestSuitBaseInfoVO() ;
            testSuitBaseInfoVO.setId(clientAutoTestSuitBaseInfoDO.getId());
            testSuitBaseInfoVO.setName(clientAutoTestSuitBaseInfoDO.getSuitName());
//            UserInfoBO userInfoBO = userInfoBOMap.get(clientAutoTestSuitBaseInfoDO.getSuitOwner()) ;
//            UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO) ;
//            testSuitBaseInfoVO.setOwner(userInfoVO);
//            List<TagVO> tagVOList = tagListMap.get(clientAutoTestSuitBaseInfoDO.getId());
//            testSuitBaseInfoVO.setTags(tagVOList);
            testSuitBaseInfoVOList.add(testSuitBaseInfoVO) ;
        }
        return testSuitBaseInfoVOList ;
    }
}
