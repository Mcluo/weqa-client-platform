package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientTestCaseExecDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:17
 */
@Mapper
public interface ClientTestCaseExecDAO {

    int insertOrUpdateTestExec(@Param("execInfo") ClientTestCaseExecDO clientTestCaseExecDO) ;

    ClientTestCaseExecDO getClientTestCaseExecDO(@Param("tvID") long tvId) ;

}
