package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/16 16:56
 */
@Mapper
public interface ClientAutoDeviceInfoDAO {

    int insertNewDeviceInfo(@Param("device") ClientAutoDeviceInfoDO clientAutoDeviceInfoDO) ;

    List<ClientAutoDeviceInfoDO> getAllClientAutoDevice() ;
    List<ClientAutoDeviceInfoDO> getClientAutoDeviceByOwner(@Param("owner") String owner) ;

    List<ClientAutoDeviceInfoDO> getClientAutoDeviceByIds(@Param("list")List<Long> deviceIdList) ;

    int updateDeviceInfo(@Param("device") ClientAutoDeviceInfoDO clientAutoDeviceInfoDO) ;
    int updateDeviceAlive(@Param("device") ClientAutoDeviceInfoDO clientAutoDeviceInfoDO) ;

}
