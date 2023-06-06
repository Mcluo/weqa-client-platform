package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestStatisticRunInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/9 22:24
 */
@Mapper
public interface ClientAutoScriptRunInfoDAO {

    /**
     * 根据脚本ID 更新脚本状态
     * @param scriptId
     * @param statusCode
     * @return
     */
    int updateStatus(@Param("scriptId") Long scriptId,@Param("result") byte statusCode);

    /*****************以下生产者使用*****************/

    /**
     * 批量插入脚本信息
     * @param clientAutoScriptRunInfoDOList
     * @return
     */
    int patchInsertAutoScript(@Param("scriptList") List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList) ;

    /**
     * 根据ID自动化脚本信息
     * @param taskId
     * @return
     */
    List<ClientAutoScriptRunInfoDO> getClientAutoScriptRunInfoByTaskId(@Param("taskId") long taskId) ;

    /**
     * 批量变更状态（主要用于任务取消）
     * @param taskId
     * @param originStatusCode
     * @param newStatusCode
     * @return
     */
    int updateStatusByTaskAndStatus(@Param("taskId") long taskId,@Param("origin") byte originStatusCode ,@Param("newStatus") byte newStatusCode) ;

	ClientAutoScriptRunInfoDO getClientAutoScriptRunInfoById(@Param("id") long id) ;

}
