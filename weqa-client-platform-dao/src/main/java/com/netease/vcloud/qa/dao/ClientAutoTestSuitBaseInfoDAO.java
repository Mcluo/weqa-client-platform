package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/8 11:47
 */
@Mapper
public interface ClientAutoTestSuitBaseInfoDAO {

    int addNewAutoTestSuit(@Param("info") ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO) ;


    ClientAutoTestSuitBaseInfoDO getAutoTestSuitById(@Param("id") Long id) ;


    List<ClientAutoTestSuitBaseInfoDO> queryAutoTestSuitByName(@Param("name")String name) ;
}
