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

}
