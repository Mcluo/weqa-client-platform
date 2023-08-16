package com.netease.vcloud.qa.service.tag;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientAutoTagBaseInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoTagBaseInfoDO;
import com.netease.vcloud.qa.service.tag.data.*;
import com.netease.vcloud.qa.tag.TagType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 13:51
 */
@Service
public class TagManagerService {

    private static final Logger logger = LoggerFactory.getLogger("tagLog");

    @Autowired
    private ClientAutoTagBaseInfoDAO clientAutoTagBaseInfoDAO ;

    @Autowired
    private UserInfoService userInfoService ;

    public Long addTag(TagDTO tagDTO) throws AutoTestTagException {
        if (tagDTO == null || StringUtils.isBlank(tagDTO.getName())){
            logger.error("[AutoTestTaskProducer.addTag] tagDTO is null");
            throw  new AutoTestTagException(AutoTestTagException.AUTO_TAG_DEFAULT_EXCEPTION) ;
        }
        ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO = clientAutoTagBaseInfoDAO.getAutoTagByName(tagDTO.getName()) ;
        if (clientAutoTagBaseInfoDO!= null){
            return clientAutoTagBaseInfoDO.getId()  ;
        }
        clientAutoTagBaseInfoDO = new ClientAutoTagBaseInfoDO();
        clientAutoTagBaseInfoDO.setTagName(tagDTO.getName());
        TagType tagType = TagType.getTagTypeByCode(tagDTO.getType()) ;
        if (tagType != null){
            clientAutoTagBaseInfoDO.setTagType(tagType.getCode());
        }
        clientAutoTagBaseInfoDO.setCreator(tagDTO.getCreator());
        int count = clientAutoTagBaseInfoDAO.insertNewAutoTag(clientAutoTagBaseInfoDO) ;
        if (count > 0){
            return clientAutoTagBaseInfoDO.getId() ;
        }else {
            return null  ;
        }
    }

    public boolean updateTag(TagDTO tagDTO) throws AutoTestTagException {
        if (tagDTO == null || tagDTO.getId() == null|| StringUtils.isBlank(tagDTO.getName())){
            logger.error("[AutoTestTaskProducer.addTag] tagDTO is null");
            throw  new AutoTestTagException(AutoTestTagException.AUTO_TAG_PARAM_EXCEPTION) ;
        }
        ClientAutoTagBaseInfoDO  clientAutoTagBaseInfoDO = clientAutoTagBaseInfoDAO.getAutoTagByID(tagDTO.getId()) ;
        if (clientAutoTagBaseInfoDO == null){
            throw new AutoTestTagException(AutoTestTagException.AUTO_TAG_PARAM_EXCEPTION) ;
        }
        clientAutoTagBaseInfoDO.setTagName(tagDTO.getName());
        TagType tagType = TagType.getTagTypeByCode(tagDTO.getType()) ;
        if (tagType != null){
            clientAutoTagBaseInfoDO.setTagType(tagType.getCode());
        }
//        clientAutoTagBaseInfoDO.setCreator(tagDTO.getCreator());
        int count = clientAutoTagBaseInfoDAO.updateAutoTag(clientAutoTagBaseInfoDO) ;
        if (count > 0){
            return true ;
        }else{
            return false ;
        }
    }

    public List<TagVO> queryTagListByKey(String key) throws AutoTestTagException{
        if (StringUtils.isBlank(key)){
            logger.error("[AutoTestTaskProducer.getTagListByKey] query key is null");
            throw new AutoTestTagException(AutoTestTagException.AUTO_TAG_PARAM_EXCEPTION) ;
        }
        List<ClientAutoTagBaseInfoDO>  baseInfoDOList = clientAutoTagBaseInfoDAO.queryAutoTagByKey(key) ;
        List<TagVO> tagVOList = new ArrayList<>() ;
        if (!CollectionUtils.isEmpty(baseInfoDOList)){
            for (ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO : baseInfoDOList){
                TagVO tagVO = new TagVO() ;
                tagVO.setId(clientAutoTagBaseInfoDO.getId());
                tagVO.setName(clientAutoTagBaseInfoDO.getTagName());
                tagVO.setType(clientAutoTagBaseInfoDO.getTagType());
                tagVOList.add(tagVO) ;
            }
        }
        return tagVOList ;
    }

    public List<TagVO> getTagListByType(String type){
        TagType tagType = TagType.getTagTypeByCode(type) ;
        List<ClientAutoTagBaseInfoDO> baseInfoDOList = clientAutoTagBaseInfoDAO.getAutoTagByType(tagType.getCode()) ;
        List<TagVO> tagVOList = new ArrayList<>() ;
        if (!CollectionUtils.isEmpty(baseInfoDOList)){
            for (ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO : baseInfoDOList){
                TagVO tagVO = new TagVO() ;
                tagVO.setId(clientAutoTagBaseInfoDO.getId());
                tagVO.setName(clientAutoTagBaseInfoDO.getTagName());
                tagVO.setType(clientAutoTagBaseInfoDO.getTagType());
                tagVOList.add(tagVO) ;
            }
        }
        return tagVOList ;
    }


    public boolean deleteTag(long id) {
        int count = clientAutoTagBaseInfoDAO.deleteAutoTagById(id) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }

    public List<TagTypeVO> getTypeList(){
        TagType[] tagTypeArray = TagType.values() ;
        List<TagTypeVO> tagTypeVOList = new ArrayList<>() ;
        for (TagType tagType : tagTypeArray){
            TagTypeVO tagTypeVO = new TagTypeVO() ;
            tagTypeVO.setCode(tagType.getCode());
            tagTypeVO.setName(tagType.getName());
            tagTypeVOList.add(tagTypeVO) ;
        }
        return tagTypeVOList ;
    }


    public List<TagSelectVO> getTageSelect(){
        List<TagSelectVO> tagSelectVOList = new ArrayList<>() ;
        TagType[] tagTypeArray = TagType.values() ;
//        List<ClientAutoTagBaseInfoDO> clientAutoTagBaseInfoDOList = clientAutoTagBaseInfoDAO.getAllAutoTag() ;
        //这边简单处理，直接循环内调用DB了，改方法对速度不敏感
        for (TagType tagType : tagTypeArray){
           List<ClientAutoTagBaseInfoDO> clientAutoTagBaseInfoDOList = clientAutoTagBaseInfoDAO.getAutoTagByType(tagType.getCode()) ;
           TagSelectVO parentTagSelectVO = new TagSelectVO() ;
           parentTagSelectVO.setLabel(tagType.getName());
           parentTagSelectVO.setValue(tagType.getCode());
           tagSelectVOList.add(parentTagSelectVO) ;
           List<TagSelectVO> chileTagSelectVOList = new ArrayList<>() ;
           if (CollectionUtils.isNotEmpty(clientAutoTagBaseInfoDOList)){
                for (ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO : clientAutoTagBaseInfoDOList){
                    TagSelectVO tagSelectVO = new TagSelectVO() ;
                    tagSelectVO.setLabel(clientAutoTagBaseInfoDO.getTagName());
                    tagSelectVO.setValue(clientAutoTagBaseInfoDO.getId()+"");
                    chileTagSelectVOList.add(tagSelectVO) ;
                }
           }
           parentTagSelectVO.setChildren(chileTagSelectVOList);
        }
        return tagSelectVOList ;
    }

    public TagDetailVO getTagDetail(long id) throws AutoTestTagException{
        ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO = clientAutoTagBaseInfoDAO.getAutoTagByID(id) ;
        if (clientAutoTagBaseInfoDO == null){
            throw new AutoTestTagException(AutoTestTagException.TAG_NOT_EXIST_EXCEPTION) ;
        }
        TagDetailVO tagDetailVO = new TagDetailVO() ;
        tagDetailVO.setId(clientAutoTagBaseInfoDO.getId());
        tagDetailVO.setName(clientAutoTagBaseInfoDO.getTagName());
        if (clientAutoTagBaseInfoDO.getTagType() !=null) {
            TagType tagType = TagType.getTagTypeByCode(clientAutoTagBaseInfoDO.getTagType()) ;
            if (tagType!= null) {
                tagDetailVO.setType(tagType.getName());
            }
        }
        tagDetailVO.setCreateTime(clientAutoTagBaseInfoDO.getGmtCreate().getTime());
        UserInfoBO userInfoBO = userInfoService.getUserInfoByEmail(clientAutoTagBaseInfoDO.getCreator()) ;
        if (userInfoBO!= null){
            tagDetailVO.setOwner(CommonUtils.buildUserInfoVOByBO(userInfoBO));
        }
        return tagDetailVO ;
    }
}
