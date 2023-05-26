package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientPerfBaseLineDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:39
 */
@Mapper
public interface ClientPerfBaseLineDAO {

    List<ClientPerfBaseLineDO> queryClientPerfBaseLineList(@Param("type") Byte type, @Param("start") int page, @Param("size") int size) ;

    int countClientPerfBaseLineDOList(@Param("type") Byte type) ;

    ClientPerfBaseLineDO getClientPerfBaseLineDOById(@Param("id")long id) ;

    int insertClientPerfBaseLineDOList(@Param("baseLine")ClientPerfBaseLineDO clientPerfBaseLineDO) ;
}
