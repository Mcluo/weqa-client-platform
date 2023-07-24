package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientConfigVersionCheckWriteListDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/14 16:50
 */
@Mapper
public interface ClientConfigVersionCheckWriteListDAO {

    int patchInsertWriteList(@Param("list") List<ClientConfigVersionCheckWriteListDO> clientConfigVersionCheckWriteListDOList);

    List<ClientConfigVersionCheckWriteListDO> getClientConfigVersionCheckWriteListByType(@Param("type") String configType);

}
