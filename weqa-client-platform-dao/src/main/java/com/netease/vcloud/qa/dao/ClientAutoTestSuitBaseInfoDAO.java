package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/8 11:47
 */
@Mapper
public interface ClientAutoTestSuitBaseInfoDAO {

    int addNewAutoTestSuit(@Param("info") ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO) ;


    ClientAutoTestSuitBaseInfoDO getAutoTestSuitById(@Param("id") Long id) ;

    List<ClientAutoTestSuitBaseInfoDO> queryAutoTestSuitByIdSet(@Param("idSet") Set<Long> idSet) ;

    List<ClientAutoTestSuitBaseInfoDO> getAutoTestSuitByName(@Param("name") String name) ;

    List<ClientAutoTestSuitBaseInfoDO> queryAutoTestSuitByName(@Param("owner")String owner,@Param("name")String name) ;

    int deleteAutoTestSuit(@Param("id") long id);

    int updateAutoTestSuit(@Param("info") ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO) ;
}
