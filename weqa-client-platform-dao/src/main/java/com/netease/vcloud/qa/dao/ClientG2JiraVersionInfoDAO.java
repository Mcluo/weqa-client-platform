package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientG2JiraVersionInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/31 20:40
 */
@Mapper
public interface ClientG2JiraVersionInfoDAO {

    int patchSaveAndUpdateJiraVersionDO(@Param("list") List<ClientG2JiraVersionInfoDO> clientG2JiraVersionInfoDOList);

    List<ClientG2JiraVersionInfoDO> queryJiraVersionInfo(@Param("key") String key);

}
