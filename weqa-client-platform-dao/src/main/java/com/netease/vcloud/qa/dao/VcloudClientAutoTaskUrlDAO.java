package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientAutoTaskUrlDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VcloudClientAutoTaskUrlDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(VcloudClientAutoTaskUrlDO record);

    int insertSelective(VcloudClientAutoTaskUrlDO record);

    VcloudClientAutoTaskUrlDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VcloudClientAutoTaskUrlDO record);

    int updateByExample(@Param("record") VcloudClientAutoTaskUrlDO record);

    int updateByPrimaryKeySelective(VcloudClientAutoTaskUrlDO record);

    int updateByPrimaryKey(VcloudClientAutoTaskUrlDO record);

    List<VcloudClientAutoTaskUrlDO> getTaskUrlDOByTaskID(@Param("taskId") Long taskId) ;
}