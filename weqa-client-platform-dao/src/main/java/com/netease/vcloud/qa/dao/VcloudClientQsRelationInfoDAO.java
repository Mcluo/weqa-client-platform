package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientQsRelationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VcloudClientQsRelationInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientQsRelationDO record);

    int insertSelective(VcloudClientQsRelationDO record);

    VcloudClientQsRelationDO selectByPrimaryKey(Long id);

    List<VcloudClientQsRelationDO> selectByQsId(Long id);
    int updateByPrimaryKeySelective(VcloudClientQsRelationDO record);

    int updateByPrimaryKey(VcloudClientQsRelationDO record);
}