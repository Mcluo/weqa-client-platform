package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.dao.ClientAutoDeviceInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/23 13:54
 */
@Service
public class AutoTestDeviceService {

    private static final byte LOCAL_DEVICE_TYPE = 0 ;

    private static final byte REMOTE_DEVICE_TYPE = 1 ;

    private static final String REMOTE_DEVICE_OWNER = "system" ;

    @Autowired
    private ClientAutoDeviceInfoDAO clientAutoDeviceInfoDAO ;

    @Autowired
    private UserInfoService userInfoService ;
    /**
     * 获取可以选择的设备
     * @return
     */
    public List<DeviceInfoVO> getDeviceList(String userInfo , byte deviceType) throws AutoTestRunException{
//        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getAllClientAutoDevice() ;
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = null;
        if (deviceType == REMOTE_DEVICE_TYPE){
            clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByOwner(REMOTE_DEVICE_OWNER) ;
        } else if (deviceType == LOCAL_DEVICE_TYPE && StringUtils.isNotBlank(userInfo)) {
            clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByOwner(userInfo) ;
        }else {
            clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getAllClientAutoDevice() ;
        }
        List<DeviceInfoVO> deviceInfoVOList =  new ArrayList<>() ;
        if (CollectionUtils.isEmpty(clientAutoDeviceInfoDOList)){
            throw new AutoTestRunException(AutoTestRunException.DEVICE_IS_OFFLINE) ;
//            return deviceInfoVOList ;
        }
        Set<String> userIdSet = new HashSet<>() ;
        for (ClientAutoDeviceInfoDO clientAutoDeviceInfoDO : clientAutoDeviceInfoDOList){
            if (clientAutoDeviceInfoDO!=null && StringUtils.isNotBlank(clientAutoDeviceInfoDO.getOwner())){
                userIdSet.add(clientAutoDeviceInfoDO.getOwner()) ;
            }
        }
        Map<String , UserInfoBO> userInfoMap = userInfoService.queryUserInfoBOMap(userIdSet) ;
        for (ClientAutoDeviceInfoDO clientAutoDeviceInfoDO : clientAutoDeviceInfoDOList){
            DeviceInfoVO deviceInfoVO = this.buildDeviceInfoVOByDO(clientAutoDeviceInfoDO,userInfoMap) ;
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

    public boolean addNewDeviceInfo(String ip , Integer port , String platform , String userId , String cpu, String owner) throws AutoTestRunException{
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
        clientAutoDeviceInfoDO.setOwner(owner);
        clientAutoDeviceInfoDO.setRun((byte)0);
        clientAutoDeviceInfoDO.setAlive((byte)1);
        int count = clientAutoDeviceInfoDAO.insertNewDeviceInfo(clientAutoDeviceInfoDO) ;
        return count > 0;
    }

    public List<DeviceInfoVO> getDeviceInfoList(List<Long> deviceIdList){
        List<DeviceInfoVO> deviceInfoVOList = new ArrayList<>() ;
        if (CollectionUtils.isEmpty(deviceIdList)){
            return deviceInfoVOList ;
        }
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByIds(deviceIdList) ;
        Set<String> userIdSet = new HashSet<>() ;
        for (ClientAutoDeviceInfoDO clientAutoDeviceInfoDO : clientAutoDeviceInfoDOList){
            if (clientAutoDeviceInfoDO!=null && StringUtils.isNotBlank(clientAutoDeviceInfoDO.getOwner())){
                userIdSet.add(clientAutoDeviceInfoDO.getOwner()) ;
            }
        }
        Map<String , UserInfoBO> userInfoMap = userInfoService.queryUserInfoBOMap(userIdSet) ;
        if (clientAutoDeviceInfoDOList != null) {
            for (ClientAutoDeviceInfoDO clientAutoDeviceInfoDO : clientAutoDeviceInfoDOList) {
                DeviceInfoVO deviceInfoVO = this.buildDeviceInfoVOByDO(clientAutoDeviceInfoDO,userInfoMap) ;
                if (deviceInfoVO != null){
                    deviceInfoVOList.add(deviceInfoVO) ;
                }
            }
        }
        return deviceInfoVOList ;
    }


    private DeviceInfoVO buildDeviceInfoVOByDO(ClientAutoDeviceInfoDO clientAutoDeviceInfoDO,Map<String , UserInfoBO> userInfoMap){
        if (clientAutoDeviceInfoDO == null){
            return null ;
        }
        DeviceInfoVO deviceInfoVO = new DeviceInfoVO() ;
        deviceInfoVO.setIp(clientAutoDeviceInfoDO.getDeviceIp());
        deviceInfoVO.setPort(clientAutoDeviceInfoDO.getDevicePort());
        deviceInfoVO.setUserId(clientAutoDeviceInfoDO.getUserId());
        deviceInfoVO.setCpu(clientAutoDeviceInfoDO.getCpuInfo());
        deviceInfoVO.setId(clientAutoDeviceInfoDO.getId());
        deviceInfoVO.setDeviceId(clientAutoDeviceInfoDO.getDeviceId());
        UserInfoBO userInfoBO = userInfoMap.get(clientAutoDeviceInfoDO.getOwner()) ;
        if (userInfoBO!=null) {
            deviceInfoVO.setOperator(CommonUtils.buildUserInfoVOByBO(userInfoBO));
        }
        deviceInfoVO.setRun((clientAutoDeviceInfoDO.getRun()!=null && clientAutoDeviceInfoDO.getRun()==(byte)1) ? true:false);
        deviceInfoVO.setAlive((clientAutoDeviceInfoDO.getAlive()==null||clientAutoDeviceInfoDO.getAlive()==(byte)1)?true:false);
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(clientAutoDeviceInfoDO.getPlatform()) ;
        if (devicePlatform!=null) {
            deviceInfoVO.setPlatform(devicePlatform.getPlatform());
        }
        return deviceInfoVO ;
    }

    public boolean updateDeviceInfo(Long id , String ip , Integer port , String platform , String userId , String cpu, String owner) throws AutoTestRunException{
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByName(platform) ;

        if (id == null ||StringUtils.isBlank(ip) || port == null || devicePlatform == null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientAutoDeviceInfoDO clientAutoDeviceInfoDO = new ClientAutoDeviceInfoDO() ;
        clientAutoDeviceInfoDO.setId(id);
        clientAutoDeviceInfoDO.setDeviceIp(ip);
        clientAutoDeviceInfoDO.setDevicePort(port);
        clientAutoDeviceInfoDO.setCpuInfo(cpu);
        clientAutoDeviceInfoDO.setUserId(userId);
        clientAutoDeviceInfoDO.setPlatform(devicePlatform.getCode());
        clientAutoDeviceInfoDO.setCpuInfo(cpu);
        clientAutoDeviceInfoDO.setOwner(owner);
//        clientAutoDeviceInfoDO.setRun(run);
//        clientAutoDeviceInfoDO.setAlive(live);
        int count = clientAutoDeviceInfoDAO.updateDeviceInfo(clientAutoDeviceInfoDO) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }
}
