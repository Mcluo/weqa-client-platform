package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientTestCaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 16:27
 */
@Mapper
public interface ClientTestCaseInfoDAO {
    /**
     * 批量插入用例信息
     * @param clientTestCaseInfoDOList
     * @return
     */
    int patchInsertIntoTestCaseInfo(@Param("list")List<ClientTestCaseInfoDO> clientTestCaseInfoDOList) ;

    int updateTestCaseCoveredStatus(@Param("tcId") long testCaseId , @Param("isCovered") byte isCovered) ;
}
