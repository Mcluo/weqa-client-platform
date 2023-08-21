package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTagBaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:02
 */
@Mapper
public interface ClientAutoTagBaseInfoDAO {

    int insertNewAutoTag(@Param("info") ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO)  ;

    int updateAutoTag(@Param("info") ClientAutoTagBaseInfoDO clientAutoTagBaseInfoDO) ;

    List<ClientAutoTagBaseInfoDO> queryAutoTagByKey(@Param("key") String key) ;

    List<ClientAutoTagBaseInfoDO> getAutoTagByType(@Param("type") String type) ;
    ClientAutoTagBaseInfoDO getAutoTagByID(@Param("id") Long id) ;

    ClientAutoTagBaseInfoDO getAutoTagByName(@Param("name") String name) ;

    List<ClientAutoTagBaseInfoDO> getAllAutoTag() ;

    int deleteAutoTagById(@Param("id") long id) ;

    List<ClientAutoTagBaseInfoDO> getAutoTagByIDSet(@Param("idSet") Set<Long> idSet) ;


}
