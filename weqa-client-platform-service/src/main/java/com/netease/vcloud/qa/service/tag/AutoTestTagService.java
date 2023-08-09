package com.netease.vcloud.qa.service.tag;

import com.netease.vcloud.qa.dao.ClientAutoTagBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTagRelationDAO;
import com.netease.vcloud.qa.model.ClientAutoTagBaseInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTagRelationDO;
import com.netease.vcloud.qa.service.tag.data.TagVO;
import com.netease.vcloud.qa.tag.TagRelationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 标签和自动话相关服务
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 13:52
 */
@Service
public class AutoTestTagService {

    private static final Logger logger = LoggerFactory.getLogger("tagLog");


    @Autowired
    private ClientAutoTagRelationDAO clientAutoTagRelationDAO ;

    @Autowired
    private ClientAutoTagBaseInfoDAO clientAutoTagBaseInfoDAO ;

    public boolean testCaseAddTag(long scriptId , long tagId , String operator) throws AutoTestTagException{
        //检查脚本
        return this.bindTagAndTest(tagId , TagRelationType.TEST_CASE, scriptId,operator);
    }

    public  boolean testSuitAddTag(long suitId , long tagId , String operator) throws AutoTestTagException{
        //检查用例集
        return this.bindTagAndTest(tagId , TagRelationType.TEST_SUITE, suitId , operator) ;
    }


    public boolean bindTagAndTest(long tagId , TagRelationType relationType , long testId, String operator) throws AutoTestTagException{
        //检查标签
        ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO = clientAutoTagBaseInfoDAO.getAutoTagByID(tagId) ;
        if (clientAutoTagBaseInfoDO == null){
            throw  new AutoTestTagException(AutoTestTagException.TAG_NOT_EXIST_EXCEPTION) ;
        }
        ClientAutoTagRelationDO clientAutoTagRelationDO = clientAutoTagRelationDAO.getAutoTagRelation(tagId, relationType.getCode(), testId) ;
        if (clientAutoTagRelationDO!= null){
            //已经绑定完成
            return true ;
        }
        clientAutoTagRelationDO = new ClientAutoTagRelationDO() ;
        clientAutoTagRelationDO.setTagId(tagId);
        clientAutoTagRelationDO.setRelationType(relationType.getCode());
        clientAutoTagRelationDO.setRelationId(testId);
        clientAutoTagRelationDO.setOperator(operator);
        int count = clientAutoTagRelationDAO.insertAutoTagRelation(clientAutoTagRelationDO) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }

    public boolean testCaseDeleteTag(long scriptId , long tagId ) throws AutoTestTagException{
        //检查脚本
        return this.unbindTagAndTest(tagId , TagRelationType.TEST_CASE, scriptId);
    }

    public  boolean testSuitDeleteTag(long suitId , long tagId ) throws AutoTestTagException{
        //检查用例集
        return this.unbindTagAndTest(tagId , TagRelationType.TEST_SUITE, suitId) ;
    }

    public boolean unbindTagAndTest(long tagId , TagRelationType relationType , long testId) throws AutoTestTagException{
        if (relationType == null ){
            throw  new AutoTestTagException(AutoTestTagException.AUTO_TAG_PARAM_EXCEPTION) ;
        }
        int count = clientAutoTagRelationDAO.deleteAutoTagRelation(tagId, relationType.getCode(), testId) ;
        return count > 0 ;
    }


    /**
     * 根据标签的ID集合，查询对应的关联项目，以Map形式返回(map的key为类型，value为具体值的列表)
     * @param tagSet
     * @return
     */
    public Map<TagRelationType, List<Long>> getTagsRelation(Set<Long> tagSet) {
        Map<TagRelationType, List<Long>> relationMap = new HashMap<>();
        if (CollectionUtils.isEmpty(tagSet)){
            return relationMap ;
        }
        List<ClientAutoTagRelationDO> clientAutoTagRelationDOList = clientAutoTagRelationDAO.queryAutoTagRelationBySet(tagSet) ;
        if (CollectionUtils.isEmpty(clientAutoTagRelationDOList)){
            return relationMap ;
        }
        for (ClientAutoTagRelationDO clientAutoTagRelationDO : clientAutoTagRelationDOList){
            if (clientAutoTagRelationDO == null ){
                continue;
            }
            TagRelationType tagRelationType = TagRelationType.getTagRelationTypeByCode(clientAutoTagRelationDO.getRelationType()) ;
            if (tagRelationType == null){
                continue;
            }
            List<Long> relationList = relationMap.get(tagRelationType) ;
            if (relationList == null){
                relationList = new ArrayList<>();
                relationMap.put(tagRelationType, relationList);
            }
            relationList.add(clientAutoTagRelationDO.getRelationId()) ;
        }
        return relationMap ;
    }

    /**
     * 根据一个关联关系的列表，获取对应的标签list
     * @param tagRelationType
     * @param relationIdList
     * @return
     */
    public Map<Long,List<TagVO>> getTagListByRelation(TagRelationType tagRelationType, Set<Long> relationIdList) {
        Map<Long, List<TagVO>> relationMap = new HashMap<>();
        if (tagRelationType == null || CollectionUtils.isEmpty(relationIdList)) {
            return relationMap ;
        }
        List<ClientAutoTagRelationDO> clientAutoTagRelationDOList = clientAutoTagRelationDAO.queryAutoTagRelationByRelationIds(tagRelationType.getCode(), relationIdList) ;
        if (CollectionUtils.isEmpty(clientAutoTagRelationDOList)) {
            return relationMap ;
        }
        Set<Long> idSet = new HashSet<>();
        for (ClientAutoTagRelationDO clientAutoTagRelationDO : clientAutoTagRelationDOList) {
            if (clientAutoTagRelationDO != null){
                idSet.add(clientAutoTagRelationDO.getTagId()) ;
            }
        }
        List<ClientAutoTagBaseInfoDO> clientAutoTagBaseInfoDOList = clientAutoTagBaseInfoDAO.getAutoTagByIDSet(idSet) ;
        if (CollectionUtils.isEmpty(clientAutoTagBaseInfoDOList)) {
            return relationMap ;
        }
        Map<Long,ClientAutoTagBaseInfoDO> clientAutoTagBaseInfoDOMap = new HashMap<>();
        for (ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO : clientAutoTagBaseInfoDOList) {
            clientAutoTagBaseInfoDOMap.put(clientAutoTagBaseInfoDO.getId(),clientAutoTagBaseInfoDO);
        }
        for (ClientAutoTagRelationDO clientAutoTagRelationDO : clientAutoTagRelationDOList) {
            if (clientAutoTagRelationDO == null){
                continue;
            }
            Long relationId = clientAutoTagRelationDO.getRelationId() ;
            List<TagVO> tagVOList = relationMap.get(relationId);
            if (tagVOList == null){
                tagVOList = new ArrayList<>();
                relationMap.put(relationId, tagVOList);
            }
            ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO = clientAutoTagBaseInfoDOMap.get(clientAutoTagRelationDO.getTagId()) ;
            if (clientAutoTagBaseInfoDO ==null){
                continue;
            }
            TagVO tagVO = new TagVO() ;
            tagVO.setId(clientAutoTagBaseInfoDO.getId());
            tagVO.setName(clientAutoTagBaseInfoDO.getTagName());
            tagVO.setType(clientAutoTagBaseInfoDO.getTagType());
            tagVOList.add(tagVO) ;
        }
        return relationMap ;
    }

}
