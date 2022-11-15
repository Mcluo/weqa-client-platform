package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
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
     * 根据ID自动化脚本信息
     * @param taskId
     * @param statusSet
     * @return
     */
    List<ClientAutoScriptRunInfoDO> getClientAutoScriptRunInfoByTaskIdAndStatus(@Param("taskId") long taskId, @Param("statusSet") Set<Byte> statusSet) ;

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

}
