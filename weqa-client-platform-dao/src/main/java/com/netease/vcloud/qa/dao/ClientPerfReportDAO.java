package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientPerfReportDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:38
 */
@Mapper
public interface ClientPerfReportDAO {

    List<ClientPerfReportDO> queryClientPerfReportList(@Param("type") Byte type, @Param("start") int page, @Param("size") int size) ;

    int countClientPerfReportList(@Param("type") Byte type) ;

    int insertClientPerfReport(@Param("report") ClientPerfReportDO clientPerfReportDO) ;

    ClientPerfReportDO getClientPerfReportDOById(@Param("id") Long id) ;
}
