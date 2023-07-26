package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientQsSceneDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
@Mapper
public interface VcloudClientQsSceneInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientQsSceneDO record);

    int insertSelective(VcloudClientQsSceneDO record);

    VcloudClientQsSceneDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VcloudClientQsSceneDO record);

    int updateByPrimaryKey(VcloudClientQsSceneDO record);

    List<VcloudClientQsSceneDO> queryAutoQsSceneInfo(@Param("appId") Long appId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<VcloudClientQsSceneDO> queryAutoRandQsSceneInfo(@Param("appId") Long appId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("randNum") int randNum);

    int queryAutoQsSceneCount(@Param("qsId") Long qsId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}