package com.netease.vcloud.qa.service.tag;

import com.netease.vcloud.qa.dao.*;
import com.netease.vcloud.qa.model.ClientAutoTagRelationDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitRelationDO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;

import com.netease.vcloud.qa.service.auto.AutoTestTestSuitService;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.tag.data.TagSuitInfoVO;
import com.netease.vcloud.qa.tag.TagRelationType;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 13:53
 */
@Service
public class AutoBuildTestService {

    private static final Logger logger = LoggerFactory.getLogger("tagLog");

    @Autowired
    private ClientAutoTagRelationDAO clientAutoTagRelationDAO ;


    @Autowired
    private ClientAutoTestSuitBaseInfoDAO clientAutoTestSuitBaseInfoDAO ;

    @Autowired
    private ClientAutoTestSuitRelationDAO clientAutoTestSuitRelationDAO ;

    @Autowired
    private ClientScriptTcInfoDAO clientScriptTcInfoDAO ;

    @Autowired
    private AutoTestTestSuitService autoTestSuitService ;
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
    public List<TagSuitInfoVO> getAutoSuitListByTagId(long tagId) {
        Set<Long> tagIdSet = new HashSet<Long>() ;
        List<TagSuitInfoVO> tagSuitInfoVOList = new ArrayList<>() ;
        tagIdSet.add(tagId) ;
        List<ClientAutoTagRelationDO> clientAutoTagRelationDOList = clientAutoTagRelationDAO.queryAutoTagRelationByTagIds(TagRelationType.TEST_SUITE.getCode(), tagIdSet) ;
        if (CollectionUtils.isEmpty(clientAutoTagRelationDOList)){
            return tagSuitInfoVOList ;
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
            TagSuitInfoVO testSuitInfoVO = new TagSuitInfoVO() ;
            testSuitInfoVO.setId(clientAutoTestSuitBaseInfoDO.getId());
            testSuitInfoVO.setName(clientAutoTestSuitBaseInfoDO.getSuitName());
//            UserInfoBO userInfoBO = userInfoBOMap.get(clientAutoTestSuitBaseInfoDO.getSuitOwner()) ;
//            UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO) ;
//            testSuitBaseInfoVO.setOwner(userInfoVO);
//            List<TagVO> tagVOList = tagListMap.get(clientAutoTestSuitBaseInfoDO.getId());
//            testSuitBaseInfoVO.setTags(tagVOList);
            //for 循环内查询，效率较为低下
            List<AutoScriptInfoVO> autoScriptInfoVOSet = this.getAutoScriptInfoVOListBySuidId(clientAutoTestSuitBaseInfoDO.getId()) ;
            testSuitInfoVO.setScripts(autoScriptInfoVOSet);
            tagSuitInfoVOList.add(testSuitInfoVO) ;
        }
        return tagSuitInfoVOList ;
    }


    private List<AutoScriptInfoVO> getAutoScriptInfoVOListBySuidId(Long suitId){
        List<AutoScriptInfoVO> autoScriptInfoVOList = new ArrayList<>() ;
//        try {
//            autoScriptInfoVOList = autoTestSuitService.getTestSuitScriptInfo(suitId);
//        }catch (AutoTestRunException e){
//            logger.error("[AutoBuildTestService.getAUtoScriptInfoVOListBySuidId] get test suit error", e);
//        }
        List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList = clientAutoTestSuitRelationDAO.getAutoTestSuitRelationListBySuit(suitId) ;
        if (CollectionUtils.isEmpty(clientAutoTestSuitRelationDOList)){
            return autoScriptInfoVOList ;
        }
        Set<Long> clientAutoTestScriptSet = new HashSet<Long>() ;
        for (ClientAutoTestSuitRelationDO clientAutoTestSuitRelationDO : clientAutoTestSuitRelationDOList){
            clientAutoTestScriptSet.add(clientAutoTestSuitRelationDO.getScriptId()) ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScriptSet(clientAutoTestScriptSet) ;
        if (CollectionUtils.isEmpty(clientScriptTcInfoDOList)){
            return autoScriptInfoVOList ;
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
            autoScriptInfoVOList.add(autoScriptInfoVO) ;
        }
        return autoScriptInfoVOList ;
    }

    public List<AutoScriptInfoVO> getAllScriptByTagId(long tagId){
        Set<AutoScriptInfoVO> autoScriptInfoVOSet = this.getAutoScriptInfoVOListByTagId(tagId) ;
        List<TagSuitInfoVO> autoScriptInfoVOList = this.getAutoSuitListByTagId(tagId) ;
        if (CollectionUtils.isEmpty(autoScriptInfoVOList)){
            return new ArrayList<AutoScriptInfoVO> (autoScriptInfoVOSet) ;
        }
        for (TagSuitInfoVO tagSuitInfoVO : autoScriptInfoVOList) {
            if (tagSuitInfoVO == null || CollectionUtils.isEmpty(tagSuitInfoVO.getScripts())){
                continue;
            }
            for (AutoScriptInfoVO autoScriptInfoVO : tagSuitInfoVO.getScripts()) {
                autoScriptInfoVOSet.add(autoScriptInfoVO) ;
            }
        }
        List<AutoScriptInfoVO> autoScriptInfoVOArrayList =  new ArrayList<AutoScriptInfoVO> (autoScriptInfoVOSet) ;
        Collections.sort(autoScriptInfoVOArrayList);
        return autoScriptInfoVOArrayList ;
    }
}
