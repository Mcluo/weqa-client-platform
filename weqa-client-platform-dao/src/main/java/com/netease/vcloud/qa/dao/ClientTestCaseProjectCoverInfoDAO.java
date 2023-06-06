package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientTestCaseProjectCoverInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/2 17:49
 */
@Mapper
public interface ClientTestCaseProjectCoverInfoDAO {

//    int insertTestProjectCoverInfo(@Param("info") ClientTestCaseProjectCoverInfoDO clientTestCaseProjectCoverInfoDO);

    int patchInsertTestProjectCoverInfo(@Param("list") List<ClientTestCaseProjectCoverInfoDO> list) ;

    List<ClientTestCaseProjectCoverInfoDO> getTestProjectCoverInfo(@Param("project") Long projectId,@Param("task")Long taskId , @Param("tc") Long tcId);

    int updateTestProjectCoverInfo(@Param("project") Long projectId ,@Param("tc") Long tcId ,@Param("isCover") byte isCover);
}
