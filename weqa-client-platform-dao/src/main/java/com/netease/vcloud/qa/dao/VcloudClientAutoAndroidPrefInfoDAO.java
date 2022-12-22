package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoAndroidPrefInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface VcloudClientAutoAndroidPrefInfoDAO {
    int insert(VcloudClientAutoAndroidPrefInfoDO record);

    int insertSelective(VcloudClientAutoAndroidPrefInfoDO record);

    VcloudClientAutoAndroidPrefInfoDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VcloudClientAutoAndroidPrefInfoDO record);

    int updateByExample(@Param("record") VcloudClientAutoAndroidPrefInfoDO record);

    int updateByPrimaryKeySelective(VcloudClientAutoAndroidPrefInfoDO record);

    int updateByPrimaryKey(VcloudClientAutoAndroidPrefInfoDO record);
}