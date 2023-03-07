package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VcloudClientAutoPerfTaskDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(VcloudClientAutoPerfTaskDO record);

    int insertSelective(VcloudClientAutoPerfTaskDO record);

    VcloudClientAutoPerfTaskDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VcloudClientAutoPerfTaskDO record);

    int updateByExample(@Param("record") VcloudClientAutoPerfTaskDO record);

    int updateByPrimaryKeySelective(VcloudClientAutoPerfTaskDO record);

    int updateByPrimaryKey(VcloudClientAutoPerfTaskDO record);

    List<VcloudClientAutoPerfTaskDO> queryAutoPerfTaskList(@Param("start") int start , @Param("size") int size);

    int countAUtoPerfTask() ;
}