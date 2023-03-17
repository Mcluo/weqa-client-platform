package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskSmokeExecDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:19
 */
@Mapper
public interface ClientRiskSmokeExecDAO {

    int insertOrUpdateClientRiskSmokeExec(@Param("rangeType")byte rangeType, @Param("rangeId") long rangeId , @Param("tvId")long tvId) ;

    ClientRiskSmokeExecDO getClientRiskSmokeExec(@Param("rangeType")byte rangeType, @Param("rangeId") long rangeId ) ;
}
