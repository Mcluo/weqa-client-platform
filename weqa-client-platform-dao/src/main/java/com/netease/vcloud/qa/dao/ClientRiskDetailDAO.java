package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:22
 */
@Mapper
public interface ClientRiskDetailDAO {

    List<ClientRiskDetailDO> getRiskListByRangeId(@Param("rangeType") byte rangeType , @Param("rangeId") long rangeId) ;

    ClientRiskDetailDO getRiskByID(@Param("id") long riskId) ;

    int patchInsertClientRiskDetailInfo(@Param("riskSet") Collection<ClientRiskDetailDO> detailSet) ;

    int deleteRiskByRangeAndRule(@Param("rangeType") byte rangeType , @Param("rangeId") long rangeId , @Param("ruleSet") Collection<Long> ruleId) ;

    int updateRiskDetailInfo(@Param("risk") ClientRiskDetailDO clientRiskDetailDO) ;
}
