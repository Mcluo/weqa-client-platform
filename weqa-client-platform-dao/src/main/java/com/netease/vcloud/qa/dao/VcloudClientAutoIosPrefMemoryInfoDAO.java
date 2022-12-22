package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefMemoryInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}