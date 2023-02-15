package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:22
 */
@Mapper
public interface ClientRiskDetailDAO {

    List<ClientRiskDetailDO> buildRiskListByRangeId(@Param("rangeType") byte rangeType , @Param("rangeId") long rangeId) ;

}
