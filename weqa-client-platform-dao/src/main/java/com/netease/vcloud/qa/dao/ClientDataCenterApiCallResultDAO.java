package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientDataCenterApiCallResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 17:11
 */
@Mapper
public interface ClientDataCenterApiCallResultDAO {

     int pitchInsertApiCallResult(@Param("list") List<ClientDataCenterApiCallResultDO> clientDataCenterApiCallResultDOList);

     List<ClientDataCenterApiCallResultDO> queryApiCallResult(@Param("caseId") Long testCaseId ,@Param("userId") Long userId)  ;

}
