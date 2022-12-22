package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface VcloudClientAutoIosPrefInfoDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(VcloudClientAutoIosPrefInfoDO record);

    int insertSelective(VcloudClientAutoIosPrefInfoDO record);

    VcloudClientAutoIosPrefInfoDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VcloudClientAutoIosPrefInfoDO record);

    int updateByExample(@Param("record") VcloudClientAutoIosPrefInfoDO record);

    int updateByPrimaryKeySelective(VcloudClientAutoIosPrefInfoDO record);

    int updateByPrimaryKey(VcloudClientAutoIosPrefInfoDO record);
}