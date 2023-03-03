package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoPrivateAddressInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/1 17:55
 */
@Mapper
public interface ClientAutoPrivateAddressInfoDAO {

    int insertPrivateAddress(@Param("info") ClientAutoPrivateAddressInfoDO clientAutoPrivateAddressInfoDO) ;

    List<ClientAutoPrivateAddressInfoDO> queryPrivateAddress(@Param("start") int start,@Param("limit") int limit) ;

    int countPrivateAddress() ;

    ClientAutoPrivateAddressInfoDO getPrivateAddressById(@Param("id")long id) ;

    int deletePrivateAddressById(@Param("id") long id) ;
}
