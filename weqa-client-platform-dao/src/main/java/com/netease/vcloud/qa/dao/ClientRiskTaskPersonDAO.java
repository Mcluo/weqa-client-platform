package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientRiskTaskPersonDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:21
 */
@Mapper
public interface ClientRiskTaskPersonDAO {

    List<ClientRiskTaskPersonDO> getPersonDOByTaskSet(@Param("taskSet") Set<Long> taskIdSet) ;

    int patchInsertTaskPersonInfo(@Param("personSet")Collection<ClientRiskTaskPersonDO> personSet) ;

}
