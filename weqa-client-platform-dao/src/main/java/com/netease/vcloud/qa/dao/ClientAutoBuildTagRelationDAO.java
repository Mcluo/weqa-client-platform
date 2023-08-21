package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoBuildTagRelationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:07
 */
@Mapper
public interface ClientAutoBuildTagRelationDAO {

    int insertAutoBuildTagRelation(@Param("relation") ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO) ;

    int updateAutoBuildTagRelation(@Param("id") long id , @Param("relation") ClientAutoBuildTagRelationDO clientAutoBuildTagRelationDO) ;

    int deleteAutoBuildTagRelation(@Param("id") long id) ;

    List<ClientAutoBuildTagRelationDO> getAutoBuildTagRelationByTag(@Param("tag") long tagId) ;


    List<ClientAutoBuildTagRelationDO> getAutoBuildTagRelationByArgs(@Param("args") String buildArgs) ;

}
