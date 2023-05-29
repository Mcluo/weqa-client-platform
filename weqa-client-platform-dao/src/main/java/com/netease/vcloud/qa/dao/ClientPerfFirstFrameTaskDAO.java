package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientPerfFirstFrameTaskDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 首帧
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/9 16:43
 */
@Mapper
public interface ClientPerfFirstFrameTaskDAO {

    int insertFirstFrameTask(@Param("task") ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO) ;

    int updateFirstFrameTaskInfo(@Param("task") ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO) ;

    List<ClientPerfFirstFrameTaskDO> queryClientPerfFirstFrameTask(@Param("owner") String owner ,@Param("start") int start , @Param("size") int size) ;
    List<ClientPerfFirstFrameTaskDO> queryClientPerfFirstFrameTaskByKey(@Param("key") String key ,@Param("start") int start , @Param("size") int size) ;

    int getClientPerfFirstFrameTaskCount(@Param("owner")String owner) ;

    ClientPerfFirstFrameTaskDO getClientPerfFirstFrameTaskById(@Param("id")Long id) ;

    ClientPerfFirstFrameTaskDO getClientPerfFirstFrameTaskByAuto(@Param("auto")Long auto) ;

}
