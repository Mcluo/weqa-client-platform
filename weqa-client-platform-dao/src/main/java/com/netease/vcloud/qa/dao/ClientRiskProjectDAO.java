package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskProjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:08
 */
@Mapper
public interface ClientRiskProjectDAO {

    int createProject(@Param("info") ClientRiskProjectDO clientRiskProjectDO) ;

    ClientRiskProjectDO getRiskProjectById(@Param("id") long id) ;

    int updateProjectStatus(@Param("id") long id , @Param("status")byte status) ;

    int logicDeleteProject(@Param("id") long id) ;

    List<ClientRiskProjectDO> queryRiskProject(@Param("start")int start , @Param("limit") int limit) ;

    int countRiskProject() ;

}
