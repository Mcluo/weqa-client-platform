package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoTestScheduledRelationInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VcloudClientAutoTestScheduledRelationInfoDAO {
    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientAutoTestScheduledRelationInfoDO record);

    int insertSelective(VcloudClientAutoTestScheduledRelationInfoDO record);

    VcloudClientAutoTestScheduledRelationInfoDO selectByPrimaryKey(Long id);

    List<VcloudClientAutoTestScheduledRelationInfoDO> selectByTaskId(@Param("taskId") Long taskId);

    int updateByPrimaryKeySelective(VcloudClientAutoTestScheduledRelationInfoDO record);

    int updateByPrimaryKey(VcloudClientAutoTestScheduledRelationInfoDO record);
}