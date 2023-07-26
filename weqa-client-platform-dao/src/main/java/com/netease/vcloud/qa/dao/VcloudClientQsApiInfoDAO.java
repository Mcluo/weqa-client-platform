package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientQsApiInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VcloudClientQsApiInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientQsApiInfoDO record);

    int insertSelective(VcloudClientQsApiInfoDO record);

    VcloudClientQsApiInfoDO selectByPrimaryKey(Long id);

    List<VcloudClientQsApiInfoDO> selectByCid(@Param("cid")String cid);


    int updateByPrimaryKeySelective(VcloudClientQsApiInfoDO record);

    int updateByPrimaryKey(VcloudClientQsApiInfoDO record);
}