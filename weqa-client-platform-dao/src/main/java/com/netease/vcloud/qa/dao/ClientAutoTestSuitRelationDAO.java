package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTestSuitRelationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/8 11:47
 */
@Mapper
public interface ClientAutoTestSuitRelationDAO {

    int deleteClientTestRelationBySuit(@Param("suit") long suitId) ;

    int patchInsertClientTestRelation(@Param("relationList") List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList) ;

    List<ClientAutoTestSuitRelationDO> getAutoTestSuitRelationListBySuit(@Param("suit") long suitId) ;

    int deleteClientTestRelationBySuitAndScript(@Param("suit") long suitId,@Param("script") long ScriptId) ;

}
