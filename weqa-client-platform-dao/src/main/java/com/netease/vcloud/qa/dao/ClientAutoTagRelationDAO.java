package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTagRelationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:05
 */
@Mapper
public interface ClientAutoTagRelationDAO {

    ClientAutoTagRelationDO getAutoTagRelation(@Param("tagId") Long tagId , @Param("type") Byte type, @Param("relationId")Long relationId) ;

    int insertAutoTagRelation(@Param("relation") ClientAutoTagRelationDO clientAutoTagRelationDO) ;

    List<ClientAutoTagRelationDO> queryAutoTagRelationBySet(@Param("tagSet") Set<Long> tagSet) ;


    List<ClientAutoTagRelationDO> queryAutoTagRelationByRelationIds( @Param("type") byte type, @Param("idSet") Set<Long> idSet) ;
}
