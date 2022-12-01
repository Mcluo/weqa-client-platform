package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.dao.ClientAutoDeviceInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/23 13:54
 */
@Service
public class AutoTestDeviceService {

    @Autowired
    private ClientAutoDeviceInfoDAO clientAutoDeviceInfoDAO ;

    /**
     * 获取可以选择的设备
     * @return
     */
    public List<DeviceInfoVO> getDeviceList() throws AutoTestRunException{
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getAllClientAutoDevice() ;
        List<DeviceInfoVO> deviceInfoVOList =  new ArrayList<>() ;
        if (CollectionUtils.isEmpty(clientAutoDeviceInfoDOList)){
            throw new AutoTestRunException(AutoTestRunException.DEVICE_IS_OFFLINE) ;
//            return deviceInfoVOList ;
        }
        for (ClientAutoDeviceInfoDO clientAutoDeviceInfoDO : clientAutoDeviceInfoDOList){
            DeviceInfoVO deviceInfoVO = this.buildDeviceInfoVOByDO(clientAutoDeviceInfoDO) ;
//            DeviceInfoVO deviceInfoVO = new DeviceInfoVO() ;
//            deviceInfoVO.setIp(clientAutoDeviceInfoDO.getDeviceIp());
//            deviceInfoVO.setPort(clientAutoDeviceInfoDO.getDevicePort());
//            deviceInfoVO.setUserId(clientAutoDeviceInfoDO.getUserId());
//            deviceInfoVO.setCpu(clientAutoDeviceInfoDO.getCpuInfo());
//            deviceInfoVO.setId(clientAutoDeviceInfoDO.getId());
//            DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(clientAutoDeviceInfoDO.getPlatform()) ;
//            if (devicePlatform!=null) {
//                deviceInfoVO.setPlatform(devicePlatform.getPlatform());
//            }
            if (deviceInfoVO!=null) {
                deviceInfoVOList.add(deviceInfoVO);
            }
        }
        return deviceInfoVOList ;
    }

    public boolean addNewDeviceInfo(String ip , Integer port , String platform , String userId , String cpu) throws AutoTestRunException{
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByName(platform) ;
        if (StringUtils.isBlank(ip) || port == null || devicePlatform == null){
            throw new AutoTestRunException(AutoTestRunException.DEVICE_PARAM_EXCEPTION) ;
        }
        ClientAutoDeviceInfoDO clientAutoDeviceInfoDO = new ClientAutoDeviceInfoDO() ;
        clientAutoDeviceInfoDO.setDeviceIp(ip);
        clientAutoDeviceInfoDO.setDevicePort(port);
        clientAutoDeviceInfoDO.setCpuInfo(cpu);
        clientAutoDeviceInfoDO.setUserId(userId);
        clientAutoDeviceInfoDO.setPlatform(devicePlatform.getCode());
        clientAutoDeviceInfoDO.setCpuInfo(cpu);
        int count = clientAutoDeviceInfoDAO.insertNewDeviceInfo(clientAutoDeviceInfoDO) ;
        if (count>0){
            return true ;
        }else {
            return false ;
        }
    }

    public List<DeviceInfoVO> getDeviceInfoList(List<Long> deviceIdList){
        List<DeviceInfoVO> deviceInfoVOList = new ArrayList<>() ;
        if (CollectionUtils.isEmpty(deviceIdList)){
            return deviceInfoVOList ;
        }
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByIds(deviceIdList) ;
        if (clientAutoDeviceInfoDOList != null) {
            for (ClientAutoDeviceInfoDO clientAutoDeviceInfoDO : clientAutoDeviceInfoDOList) {
                DeviceInfoVO deviceInfoVO = this.buildDeviceInfoVOByDO(clientAutoDeviceInfoDO) ;
                if (deviceInfoVO != null){
                    deviceInfoVOList.add(deviceInfoVO) ;
                }
            }
        }
        return deviceInfoVOList ;
    }


    private DeviceInfoVO buildDeviceInfoVOByDO(ClientAutoDeviceInfoDO clientAutoDeviceInfoDO){
        if (clientAutoDeviceInfoDO == null){
            return null ;
        }
        DeviceInfoVO deviceInfoVO = new DeviceInfoVO() ;
        deviceInfoVO.setIp(clientAutoDeviceInfoDO.getDeviceIp());
        deviceInfoVO.setPort(clientAutoDeviceInfoDO.getDevicePort());
        deviceInfoVO.setUserId(clientAutoDeviceInfoDO.getUserId());
        deviceInfoVO.setCpu(clientAutoDeviceInfoDO.getCpuInfo());
        deviceInfoVO.setId(clientAutoDeviceInfoDO.getId());
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(clientAutoDeviceInfoDO.getPlatform()) ;
        if (devicePlatform!=null) {
            deviceInfoVO.setPlatform(devicePlatform.getPlatform());
        }
        return deviceInfoVO ;
    }
}
