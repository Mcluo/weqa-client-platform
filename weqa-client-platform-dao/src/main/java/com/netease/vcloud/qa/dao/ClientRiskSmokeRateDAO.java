package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskSmokeRateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/14 15:18
 */
@Mapper
public interface ClientRiskSmokeRateDAO {

    int insertOrUpdateClientRiskSmokeRate(@Param("rangeType")byte rangeType, @Param("rangeId") long rangeId , @Param("devTvId")long devTvId, @Param("qaTvId")long qaTvId) ;

    ClientRiskSmokeRateDO getClientRiskSmokeRate(@Param("rangeType")byte rangeType, @Param("rangeId") long rangeId ) ;

}
