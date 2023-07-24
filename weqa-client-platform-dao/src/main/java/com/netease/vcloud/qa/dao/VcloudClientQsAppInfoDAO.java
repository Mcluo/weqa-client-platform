package com.netease.vcloud.qa.dao;

import java.util.List;

import com.netease.vcloud.qa.model.VcloudClientQsAppDO;
import com.netease.vcloud.qa.model.VcloudClientQsRelationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VcloudClientQsAppInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientQsAppDO record);

    int insertSelective(VcloudClientQsAppDO record);
    VcloudClientQsAppDO selectByPrimaryKey(Long id);


    List<VcloudClientQsAppDO> getAll();

    int updateByPrimaryKeySelective(VcloudClientQsAppDO record);

    int updateByPrimaryKey(VcloudClientQsAppDO record);
}