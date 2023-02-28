package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskAutoTaskDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:22
 */
@Mapper
public interface ClientRiskAutoTaskDAO {

    int insertRiskAndAutoTask(@Param("type") byte rangeType, @Param("riskRange") long riskRangeId , @Param("autoTask") long autoTaskId) ;

    ClientRiskAutoTaskDO getRiskAndAutoTaskByRisk(@Param("type") byte rangeType, @Param("riskRange") long riskRangeId) ;

}
