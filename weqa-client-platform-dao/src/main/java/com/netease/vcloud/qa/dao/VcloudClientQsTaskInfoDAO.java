package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientQsTaskDO;
import com.netease.vcloud.qa.model.VcloudClientScheduledTaskInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VcloudClientQsTaskInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientQsTaskDO record);

    int insertSelective(VcloudClientQsTaskDO record);

    VcloudClientQsTaskDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VcloudClientQsTaskDO record);

    int updateByPrimaryKey(VcloudClientQsTaskDO record);
    int queryAutoTaskInfoCount(@Param("owner") String owner) ;

    List<VcloudClientQsTaskDO> queryAutoTaskInfo(@Param("owner") String owner, @Param("start") int start , @Param("size") int size);

}