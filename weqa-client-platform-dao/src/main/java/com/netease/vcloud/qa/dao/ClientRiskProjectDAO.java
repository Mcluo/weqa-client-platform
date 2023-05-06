package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskProjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:08
 */
@Mapper
public interface ClientRiskProjectDAO {

    int createProject(@Param("info") ClientRiskProjectDO clientRiskProjectDO) ;

    ClientRiskProjectDO getRiskProjectById(@Param("id") long id) ;

    int updateProjectStatus(@Param("id") long id , @Param("status")byte status, @Param("finish") Date finishTime) ;

    int logicDeleteProject(@Param("id") long id) ;

    List<ClientRiskProjectDO> queryRiskProject(@Param("start")int start , @Param("size") int size) ;

    List<ClientRiskProjectDO> searchRiskProject(@Param("key")String key ,@Param("size")int  size ) ;

    int countRiskProject() ;

}
