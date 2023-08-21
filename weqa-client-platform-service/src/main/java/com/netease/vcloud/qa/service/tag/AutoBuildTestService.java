package com.netease.vcloud.qa.service.tag;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.*;
import com.netease.vcloud.qa.model.*;

import com.netease.vcloud.qa.service.auto.AutoTestTestSuitService;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.tag.data.ArgsConditionBO;
import com.netease.vcloud.qa.service.tag.data.TagAutoArgsDTO;
import com.netease.vcloud.qa.service.tag.data.TagAutoArgsRelationVO;
import com.netease.vcloud.qa.service.tag.data.TagSuitInfoVO;
import com.netease.vcloud.qa.tag.TagRelationType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    private ClientAutoBuildTagRelationDAO clientAutoBuildTagRelationDAO ;

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

    //以下处理自动化相关构建
    public List<TagAutoArgsRelationVO> getTagAutoArgsRelationByTagId(long tagId) {
        List<ClientAutoBuildTagRelationDO> clientAutoBuildTagRelationDOList = clientAutoBuildTagRelationDAO.getAutoBuildTagRelationByTag(tagId) ;
        List<TagAutoArgsRelationVO> tagAutoArgsRelationVOList = new ArrayList<TagAutoArgsRelationVO>() ;
        if (CollectionUtils.isEmpty(clientAutoBuildTagRelationDOList)){
            return tagAutoArgsRelationVOList ;
        }
        for (ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO : clientAutoBuildTagRelationDOList){
            TagAutoArgsRelationVO tagAutoArgsRelationVO =  this.buildTagAutoArgsRelationVOByDO(clientAutoBuildTagRelationDO);
            tagAutoArgsRelationVOList.add(tagAutoArgsRelationVO) ;
        }
        return tagAutoArgsRelationVOList ;
    }

    private TagAutoArgsRelationVO buildTagAutoArgsRelationVOByDO(ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO) {
        if (clientAutoBuildTagRelationDO == null){
            return null ;
        }
        TagAutoArgsRelationVO tagAutoArgsRelationVO = new TagAutoArgsRelationVO() ;
        tagAutoArgsRelationVO.setId(clientAutoBuildTagRelationDO.getId());
        tagAutoArgsRelationVO.setCreateTime(clientAutoBuildTagRelationDO.getGmtCreate().getTime());
        tagAutoArgsRelationVO.setBuildArgs(clientAutoBuildTagRelationDO.getBuildArgs());
        tagAutoArgsRelationVO.setArgsCondition(clientAutoBuildTagRelationDO.getArgsCondition());
        tagAutoArgsRelationVO.setTagId(clientAutoBuildTagRelationDO.getTagId());
        return tagAutoArgsRelationVO ;
    }


    public boolean insertTagAutoArgsRelation(TagAutoArgsDTO tagAutoArgsDTO) {
        ArgsConditionBO argsConditionBO = parseArgsCondition(tagAutoArgsDTO) ;
        if (argsConditionBO == null){
            return false ;
        }
        ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO = this.buildClientAutoBuildTagRelationDOByBO(argsConditionBO) ;
        int count = clientAutoBuildTagRelationDAO.insertAutoBuildTagRelation(clientAutoBuildTagRelationDO) ;
        if (count > 0){
            return true ;
        }else {
            return false;
        }
    }

    public boolean updateTagAutoArgsRelation(TagAutoArgsDTO tagAutoArgsDTO) {
        ArgsConditionBO argsConditionBO = parseArgsCondition(tagAutoArgsDTO) ;
        if (argsConditionBO == null){
            return false ;
        }
        ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO = this.buildClientAutoBuildTagRelationDOByBO(argsConditionBO) ;
        int count = clientAutoBuildTagRelationDAO.updateAutoBuildTagRelation(tagAutoArgsDTO.getId(), clientAutoBuildTagRelationDO) ;
        if (count > 0){
            return true ;
        }else {
            return false;
        }
    }

    public boolean deleteTagAutoArgsRelation(long relationId) {
        int count = clientAutoBuildTagRelationDAO.deleteAutoBuildTagRelation(relationId) ;
        if (count > 0) {
            return true ;
        }else{
            return false ;
        }
    }

    private ArgsConditionBO parseArgsCondition(TagAutoArgsDTO tagAutoArgsDTO) {
        if (tagAutoArgsDTO==null){
            return null ;
        }
        ArgsConditionBO argsConditionBO = new ArgsConditionBO();
        if (tagAutoArgsDTO.getId()!=null) {
            argsConditionBO.setId(tagAutoArgsDTO.getId());
        }
        if (tagAutoArgsDTO.getTagId()!=null) {
            argsConditionBO.setTagId(tagAutoArgsDTO.getTagId());
        }
        if (tagAutoArgsDTO.getOperator()!=null){
            argsConditionBO.setOperator(tagAutoArgsDTO.getOperator()) ;
        }
        String args = tagAutoArgsDTO.getBuildArgs() ;
        if (StringUtils.isNotBlank(args)) {
            argsConditionBO.setArgs(args);
        }
        JSONObject jsonObject = tagAutoArgsDTO.getArgsCondition() ;
        if (jsonObject!=null){
            String typeStr = jsonObject.getString("type") ;
            String operatorStr = jsonObject.getString("operate") ;
            String valueStr = jsonObject.getString("value") ;
            ArgsType type = ArgsType.getType(typeStr) ;
            if (type==null){
                //没有类型，无法操作
                return null ;
            }
            argsConditionBO.setType(type);
            ArgsOperate operate = ArgsOperate.getOperate(operatorStr) ;
            if (operate==null){
                return null ;
            }
            argsConditionBO.setOperate(operate);
            if (type == ArgsType.BOOLEAN){
                argsConditionBO.setValue(Boolean.parseBoolean(valueStr));
            }else if(type == ArgsType.NUMBER){
                argsConditionBO.setValue(Integer.parseInt(valueStr));
            }else {
                argsConditionBO.setValue(valueStr);
            }
        }
        return argsConditionBO;
    }

    private ClientAutoBuildTagRelationDO buildClientAutoBuildTagRelationDOByBO(ArgsConditionBO argsConditionBO){
        if (argsConditionBO == null){
            return null ;
        }
        ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO = new ClientAutoBuildTagRelationDO() ;
        if (argsConditionBO.getId()!=null) {
            clientAutoBuildTagRelationDO.setId(argsConditionBO.getId());
        }
        clientAutoBuildTagRelationDO.setOperator(argsConditionBO.getOperator());
        clientAutoBuildTagRelationDO.setTagId(argsConditionBO.getTagId());
        clientAutoBuildTagRelationDO.setBuildArgs(argsConditionBO.getArgs());
        clientAutoBuildTagRelationDO.setArgsCondition(this.buildArgsConditionJson(argsConditionBO));
        return clientAutoBuildTagRelationDO ;
    }

    private String buildArgsConditionJson(ArgsConditionBO argsConditionBO) {
        JSONObject json = new JSONObject();
        json.put("type", argsConditionBO.getType().getType());
        json.put("operate", argsConditionBO.getOperate().getOperate());
        json.put("value", argsConditionBO.getValue());
        return  json.toJSONString();
    }

    public Set<Long> getTagIdsByAutoArgs(String key , Object value) {
        List<ClientAutoBuildTagRelationDO> clientAutoBuildTagRelationDOList = clientAutoBuildTagRelationDAO.getAutoBuildTagRelationByArgs(key) ;
        if (CollectionUtils.isEmpty(clientAutoBuildTagRelationDOList)){
            return null ;
        }
        Set<Long> tagIdSet = new HashSet<>();
        for (ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO : clientAutoBuildTagRelationDOList){
            if(StringUtils.isBlank(clientAutoBuildTagRelationDO.getArgsCondition())){
                continue ;
            }
            JSONObject jsonObject = JSONObject.parseObject(clientAutoBuildTagRelationDO.getArgsCondition());
            if(jsonObject != null){
                Object assertValue = jsonObject.get("value");
                ArgsType argsType  = ArgsType.getType(jsonObject.getString("type")) ;
                ArgsOperate argsOperate = ArgsOperate.getOperate(jsonObject.getString("operate")) ;
                boolean conditionMatchFlag = this.isConditionChecked(argsType , argsOperate ,  assertValue, value) ;
                if (conditionMatchFlag){
                    //匹配 则进行下一步操作
                    tagIdSet.add(clientAutoBuildTagRelationDO.getTagId()) ;
                }

            }
        }
        if (CollectionUtils.isEmpty(tagIdSet)) {
            return null;
        }
        Set<Long> scriptIdSet = new HashSet<>();
        for (Long tagId : tagIdSet){
            List<AutoScriptInfoVO> scriptInfoVOS = this.getAllScriptByTagId(tagId) ;
            if (!CollectionUtils.isEmpty(scriptInfoVOS)){
                for (AutoScriptInfoVO autoScriptInfoVO : scriptInfoVOS) {
                    scriptIdSet.add(autoScriptInfoVO.getId());
                }
            }
        }
        return scriptIdSet ;
    }

    private boolean isConditionChecked(ArgsType argsType , ArgsOperate argsOperate, Object assertValue ,Object value){
        if (argsType == null || argsOperate == null || assertValue == null || value == null){
            return false ;
        }
        String assertValueStr = assertValue.toString() ;
        String valueStr = value.toString() ;
        if (argsType == ArgsType.BOOLEAN){
            return assertValueStr.equalsIgnoreCase(valueStr) ;
        }
        if (argsType == ArgsType.NUMBER){
            if(ArgsOperate.EQUALS == argsOperate){
                return assertValueStr.equals(valueStr) ;
            }
            double assertValueDouble = Double.parseDouble(assertValueStr) ;
            double valueDouble = Double.parseDouble(valueStr) ;
            if(ArgsOperate.GREATER_THAN == argsOperate){
                return valueDouble > assertValueDouble ;
            }
            if(ArgsOperate.LESS_THAN == argsOperate){
                return valueDouble < assertValueDouble ;
            }
        }
        if (argsType == ArgsType.STRING){
            if(ArgsOperate.EQUALS == argsOperate){
                return StringUtils.equals(assertValueStr, valueStr) ;
            }
            int strCompare = valueStr.compareTo(assertValueStr) ;
            if(ArgsOperate.GREATER_THAN == argsOperate){
                return strCompare > 0 ;
            }
            if(ArgsOperate.LESS_THAN == argsOperate){
                return strCompare < 0 ;
            }
        }
        return false ;
    }

}
