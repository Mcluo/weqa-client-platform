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

    List<ClientPerfFirstFrameTaskDO> queryClientPerfFirstFrameTask(@Param("start") int start , @Param("size") int size) ;

    int getClientPerfFirstFrameTaskCount() ;

}
