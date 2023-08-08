package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTagBaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:02
 */
@Mapper
public interface ClientAutoTagBaseInfoDAO {

    int insertNewAutoTag(@Param("info") ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO)  ;

    int updateAutoTag(@Param("info") ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO) ;

    List<ClientAutoTagBaseInfoDO> getAutoTagByKey(@Param("key") String key) ;

    List<ClientAutoTagBaseInfoDO> getAutoTagByType(@Param("type") String type) ;

    ClientAutoTagBaseInfoDO getAutoTagByName(@Param("name") String name) ;

    int deleteAutoTagById(@Param("id") long id) ;

}
