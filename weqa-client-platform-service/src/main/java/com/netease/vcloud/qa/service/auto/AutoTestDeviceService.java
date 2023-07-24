package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.dao.ClientAutoDeviceInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
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

    public boolean addNewDeviceInfo(String ip , Integer port , String platform , String userId , String cpu, String owner,String alias) throws AutoTestRunException{
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
        clientAutoDeviceInfoDO.setAlias(alias);
        clientAutoDeviceInfoDO.setRun((byte)0);
        clientAutoDeviceInfoDO.setAlive((byte)1);
        int count = clientAutoDeviceInfoDAO.insertNewDeviceInfo(clientAutoDeviceInfoDO) ;
        return count > 0;
    }

    public boolean addDevice(ClientAutoDeviceInfoDO clientAutoDeviceInfoDO) throws AutoTestRunException{
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
        deviceInfoVO.setAlias(clientAutoDeviceInfoDO.getAlias());
        deviceInfoVO.setId(clientAutoDeviceInfoDO.getId());
        deviceInfoVO.setDeviceId(clientAutoDeviceInfoDO.getDeviceId());
        UserInfoBO userInfoBO = userInfoMap.get(clientAutoDeviceInfoDO.getOwner()) ;
        if (userInfoBO!=null) {
            deviceInfoVO.setOperator(CommonUtils.buildUserInfoVOByBO(userInfoBO));
        }else{
            UserInfoVO userInfoVO = new UserInfoVO() ;
            userInfoVO.setEmail(clientAutoDeviceInfoDO.getOwner());
            userInfoVO.setName(clientAutoDeviceInfoDO.getOwner());
            deviceInfoVO.setOperator(userInfoVO);
        }
        deviceInfoVO.setRun((clientAutoDeviceInfoDO.getRun()!=null && clientAutoDeviceInfoDO.getRun()==(byte)1) ? true:false);
        deviceInfoVO.setAlive((clientAutoDeviceInfoDO.getAlive()==null||clientAutoDeviceInfoDO.getAlive()==(byte)1)?true:false);
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(clientAutoDeviceInfoDO.getPlatform()) ;
        if (devicePlatform!=null) {
            deviceInfoVO.setPlatform(devicePlatform.getPlatform());
        }
        return deviceInfoVO ;
    }

    public boolean updateDeviceInfo(Long id , String ip , Integer port , String platform , String userId , String cpu, String owner,String alias) throws AutoTestRunException{
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
        clientAutoDeviceInfoDO.setAlias(alias);
//        clientAutoDeviceInfoDO.setRun(run);
//        clientAutoDeviceInfoDO.setAlive(live);
        int count = clientAutoDeviceInfoDAO.updateDeviceInfo(clientAutoDeviceInfoDO) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }

    public boolean updateDeviceAlive(ClientAutoDeviceInfoDO clientAutoDeviceInfoDO) throws AutoTestRunException{
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(clientAutoDeviceInfoDO.getId());
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByIds(ids);
        if (clientAutoDeviceInfoDOList.size() > 0 ){
            for(ClientAutoDeviceInfoDO autoDeviceInfoDO : clientAutoDeviceInfoDOList){
                autoDeviceInfoDO.setAlive(clientAutoDeviceInfoDO.getAlive());
                clientAutoDeviceInfoDAO.updateDeviceAlive(autoDeviceInfoDO) ;
            }
            return true;
        }
        return false;
    }

    public boolean updateDeviceRun(List<Long> ids, Byte isRun) throws AutoTestRunException{
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByIds(ids);
        if (clientAutoDeviceInfoDOList.size() > 0 ){
            for(ClientAutoDeviceInfoDO autoDeviceInfoDO : clientAutoDeviceInfoDOList){
                autoDeviceInfoDO.setRun(isRun);
                clientAutoDeviceInfoDAO.updateDeviceRun(autoDeviceInfoDO) ;
            }
            return true;
        }
        return false;
    }
}
