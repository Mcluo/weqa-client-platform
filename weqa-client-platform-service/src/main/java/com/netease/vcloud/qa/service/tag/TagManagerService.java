package com.netease.vcloud.qa.service.tag;

import com.netease.vcloud.qa.dao.ClientAutoTagBaseInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoTagBaseInfoDO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.tag.data.TagDTO;
import com.netease.vcloud.qa.service.tag.data.TagVO;
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
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 13:51
 */
@Service
public class TagManagerService {

    private final Logger logger = LoggerFactory.getLogger("tag");

    @Autowired
    private ClientAutoTagBaseInfoDAO clientAutoTagBaseInfoDAO ;


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
        if (tagType == null){
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
            throw  new AutoTestTagException(AutoTestTagException.AUTO_TAG_DEFAULT_EXCEPTION) ;
        }
        ClientAutoTagBaseInfoDO  clientAutoTagBaseInfoDO = new ClientAutoTagBaseInfoDO();
        clientAutoTagBaseInfoDO.setId(tagDTO.getId());
        clientAutoTagBaseInfoDO.setTagName(tagDTO.getName());
        TagType tagType = TagType.getTagTypeByCode(tagDTO.getType()) ;
        if (tagType == null){
            clientAutoTagBaseInfoDO.setTagType(tagType.getCode());
        }
        clientAutoTagBaseInfoDO.setCreator(tagDTO.getCreator());
        int count = clientAutoTagBaseInfoDAO.updateAutoTag(clientAutoTagBaseInfoDO) ;
        if (count > 0){
            return true ;
        }else{
            return false ;
        }
    }

    public List<TagVO> getTagListByKey(String key) throws AutoTestTagException{
        if (StringUtils.isBlank(key)){
            logger.error("[AutoTestTaskProducer.getTagListByKey] query key is null");
            throw new AutoTestTagException(AutoTestTagException.AUTO_TAG_PARAM_EXCEPTION) ;
        }
        List<ClientAutoTagBaseInfoDO>  baseInfoDOList = clientAutoTagBaseInfoDAO.getAutoTagByKey(key) ;
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

}
