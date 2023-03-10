package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefMemoryInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VcloudClientAutoIosPrefMemoryInfoDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(VcloudClientAutoIosPrefMemoryInfoDO record);

    int insertSelective(VcloudClientAutoIosPrefMemoryInfoDO record);


    VcloudClientAutoIosPrefMemoryInfoDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VcloudClientAutoIosPrefMemoryInfoDO record);

    int updateByExample(@Param("record") VcloudClientAutoIosPrefMemoryInfoDO record);

    int updateByPrimaryKeySelective(VcloudClientAutoIosPrefMemoryInfoDO record);

    int updateByPrimaryKey(VcloudClientAutoIosPrefMemoryInfoDO record);

    List<VcloudClientAutoIosPrefMemoryInfoDO> queryIOSPrefMemoryInfoDOByTaskId(@Param("taskId") int taskId) ;
}