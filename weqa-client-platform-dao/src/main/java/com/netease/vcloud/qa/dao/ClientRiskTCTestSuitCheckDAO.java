package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskTCTestSuitCheckDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/2 16:36
 */
@Mapper
public interface ClientRiskTCTestSuitCheckDAO {

    int insertRiskTCTestSuit(@Param("rangeType")byte rangeType, @Param("rangeId") long rangeId , @Param("tvId")long tvId,@Param("projectId") Long projectId) ;

    ClientRiskTCTestSuitCheckDO getRiskTCTestSuit(@Param("rangeType")byte rangeType, @Param("rangeId") long rangeId ) ;

}
