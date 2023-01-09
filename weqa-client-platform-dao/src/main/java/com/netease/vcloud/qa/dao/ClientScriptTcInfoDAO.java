package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/16 16:49
 */
@Mapper
public interface ClientScriptTcInfoDAO {

    int patchInsertClientScript(@Param("list") List<ClientScriptTcInfoDO> clientScriptTcInfoDOList) ;

    List<ClientScriptTcInfoDO> getClientScriptSet(@Param("idSet") Collection<Long> scriptIdSet) ;

    List<ClientScriptTcInfoDO> getClientScript(@Param("start") Integer start, @Param("size") Integer size) ;

    int getClientScriptCount() ;

    int updateClientScript(@Param("script") ClientScriptTcInfoDO clientScriptTcInfoDO) ;

    int deleteClientScript(@Param("id") long id ) ;

    List<ClientScriptTcInfoDO> queryClientScript(@Param("key")String key, @Param("start") Integer start, @Param("size") Integer size) ;

    int queryClientScriptCount(@Param("key") String key) ;

}
