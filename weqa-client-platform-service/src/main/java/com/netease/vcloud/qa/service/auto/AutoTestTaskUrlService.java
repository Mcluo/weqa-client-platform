package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.dao.ClientAutoDeviceInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoTaskUrlDAO;
import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoTaskUrlDO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.view.TaskUrlInfoVO;
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
public class AutoTestTaskUrlService {

    @Autowired
    private VcloudClientAutoTaskUrlDAO clientAutoTaskUrlDAO ;

    public boolean addTaskUrl(String platform , Long taskId , String url) throws AutoTestRunException{
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByName(platform) ;
        if (devicePlatform == null){
            throw new AutoTestRunException(AutoTestRunException.DEVICE_PARAM_EXCEPTION) ;
        }
        VcloudClientAutoTaskUrlDO clientAutoTaskUrlDO = new VcloudClientAutoTaskUrlDO();
        clientAutoTaskUrlDO.setTaskId(taskId);
        clientAutoTaskUrlDO.setUrl(url);
        clientAutoTaskUrlDO.setPlatform(devicePlatform.getCode());
        int count = clientAutoTaskUrlDAO.insert(clientAutoTaskUrlDO);
        if (count>0){
            return true ;
        }else {
            return false ;
        }
    }

    public List<TaskUrlInfoVO> getTaskUrlInfoList(Long taskId){
        List<VcloudClientAutoTaskUrlDO> taskUrlDOList = clientAutoTaskUrlDAO.getTaskUrlDOByTaskID(taskId) ;
        List<TaskUrlInfoVO> taskUrlInfoVOList = new ArrayList<TaskUrlInfoVO>() ;
        if (CollectionUtils.isEmpty(taskUrlDOList)){
            return taskUrlInfoVOList ;
        }
        for (VcloudClientAutoTaskUrlDO vcloudClientAutoTaskUrlDO : taskUrlDOList){
            if (vcloudClientAutoTaskUrlDO != null){
                TaskUrlInfoVO taskUrlInfoVO = new TaskUrlInfoVO() ;
                DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(vcloudClientAutoTaskUrlDO.getPlatform()) ;
                taskUrlInfoVO.setPlatform(devicePlatform.getPlatform());
                taskUrlInfoVO.setUrl(vcloudClientAutoTaskUrlDO.getUrl());
                taskUrlInfoVOList.add(taskUrlInfoVO);
            }
        }
        return taskUrlInfoVOList ;
    }
}
