package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskTaskDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:09
 */
@Mapper
public interface ClientRiskTaskDAO {

    List<ClientRiskTaskDO> getClientRiskTaskListByProjectId(@Param("project") long projectId) ;

    int insertClientRiskTask(@Param("task") ClientRiskTaskDO clientRiskTaskDO) ;

    int logicDeleteClientRiskTask(@Param("project") Long projectId,@Param("id") long taskId) ;

}
