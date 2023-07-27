package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientQsSceneDO;
import com.netease.vcloud.qa.model.VcloudClientQsTypicalSceneInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface VcloudClientQsTypicalSceneInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientQsTypicalSceneInfoDO record);

    int insertSelective(VcloudClientQsTypicalSceneInfoDO record);

    VcloudClientQsTypicalSceneInfoDO selectByPrimaryKey(Long id);

    List<VcloudClientQsTypicalSceneInfoDO> queryAutoRandQsSceneInfo(@Param("qsId") Long qsId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("randNum") int randNum, @Param("num") int num);

    List<Map<String, Long>> queryAutoRandQsSceneCount(@Param("qsId") Long qsId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int queryAutoQsSceneCount(@Param("qsId") Long qsId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    int updateByPrimaryKeySelective(VcloudClientQsTypicalSceneInfoDO record);

    int updateByPrimaryKey(VcloudClientQsTypicalSceneInfoDO record);
}