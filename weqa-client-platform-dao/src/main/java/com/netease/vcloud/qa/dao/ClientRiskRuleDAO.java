package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskRuleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:24
 */
@Mapper
public interface ClientRiskRuleDAO {

    List<ClientRiskRuleDO> getClientRiskRule(@Param("range")byte checkRange , @Param("stage") byte checkStage) ;

    List<ClientRiskRuleDO> getClientRiskRuleByIdSet(@Param("ids")Collection<Long> idSet) ;

}
