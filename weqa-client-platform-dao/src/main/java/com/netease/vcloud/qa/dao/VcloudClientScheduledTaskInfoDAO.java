package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.VcloudClientScheduledTaskInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface VcloudClientScheduledTaskInfoDAO {

    int deleteByPrimaryKey(Long id);

    int insert(VcloudClientScheduledTaskInfoDO record);

    int insertSelective(VcloudClientScheduledTaskInfoDO record);

    List<VcloudClientScheduledTaskInfoDO> queryAutoTaskInfo(@Param("owner") String owner, @Param("start") int start , @Param("size") int size);

    VcloudClientScheduledTaskInfoDO selectByPrimaryKey(Long id);

    List<VcloudClientScheduledTaskInfoDO> queryAutoTaskRunInfo( @Param("status") byte status);


    int updateByPrimaryKeySelective(VcloudClientScheduledTaskInfoDO record);

    int updateByPrimaryKeyWithBLOBs(VcloudClientScheduledTaskInfoDO record);

    int updateByPrimaryKey(VcloudClientScheduledTaskInfoDO record);
}