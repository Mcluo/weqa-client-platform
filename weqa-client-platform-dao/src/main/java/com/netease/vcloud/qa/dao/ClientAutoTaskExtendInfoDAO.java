package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTaskExtendInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/1 11:52
 */
@Mapper
public interface ClientAutoTaskExtendInfoDAO {

    ClientAutoTaskExtendInfoDO getClientAutoTaskExtendInfByTaskId(@Param("taskId") long taskId) ;

    int insertClientAutoTask(@Param("info") ClientAutoTaskExtendInfoDO clientAutoTaskExtendInfoDO) ;

}
