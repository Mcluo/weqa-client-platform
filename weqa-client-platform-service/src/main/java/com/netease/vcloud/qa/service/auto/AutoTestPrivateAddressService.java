package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.dao.ClientAutoPrivateAddressInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskExtendInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoPrivateAddressInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskExtendInfoDO;
import com.netease.vcloud.qa.service.auto.view.PrivateAddressVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/1 19:38
 */
@Service
public class AutoTestPrivateAddressService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    @Autowired
    private ClientAutoPrivateAddressInfoDAO clientAutoPrivateAddressInfoDAO ;

    @Autowired
    private ClientAutoTaskExtendInfoDAO clientAutoTaskExtendInfoDAO ;

    /**********操作部分***********/
    public boolean insertNewClientPrivateAddressInfo(String  name ,String config)  throws AutoTestRunException{
        if (StringUtils.isBlank(name) || StringUtils.isBlank(config)){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientAutoPrivateAddressInfoDO clientAutoPrivateAddressInfoDO = new ClientAutoPrivateAddressInfoDO() ;
        clientAutoPrivateAddressInfoDO.setName(name);
        clientAutoPrivateAddressInfoDO.setConfig(config);
        int count = clientAutoPrivateAddressInfoDAO.insertPrivateAddress(clientAutoPrivateAddressInfoDO) ;
        if (count > 0 ){
            return true ;
        }else {
            return false ;
        }
    }

    public boolean deleteClientPrivateAddressById(long id){
        int count = clientAutoPrivateAddressInfoDAO.deletePrivateAddressById(id) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }

    public List<PrivateAddressVO> queryPrivateAddressList(){
        List<ClientAutoPrivateAddressInfoDO> clientAutoPrivateAddressInfoDOList = clientAutoPrivateAddressInfoDAO.queryPrivateAddress(0,100) ;
        List<PrivateAddressVO> privateAddressVOList = new ArrayList<PrivateAddressVO>() ;
        if (CollectionUtils.isEmpty(clientAutoPrivateAddressInfoDOList)){
            return privateAddressVOList ;
        }
        for (ClientAutoPrivateAddressInfoDO clientAutoPrivateAddressInfoDO : clientAutoPrivateAddressInfoDOList){
            if (clientAutoPrivateAddressInfoDO == null){
                continue;
            }
            PrivateAddressVO privateAddressVO = new PrivateAddressVO() ;
            privateAddressVO.setId(clientAutoPrivateAddressInfoDO.getId());
            privateAddressVO.setName(clientAutoPrivateAddressInfoDO.getName());
            privateAddressVO.setConfig(clientAutoPrivateAddressInfoDO.getConfig());
            privateAddressVOList.add(privateAddressVO) ;
        }
        return privateAddressVOList ;
    }


    /***********任务部分************/
    public boolean runTaskWithPrivateAddress(long taskId , Long privateId) throws AutoTestRunException{
        if (privateId == null){
            AUTO_LOGGER.info("[AutoTestPrivateAddressService.runTaskWithPrivateAddress] privateAddress is null");
            return true ;
        }
        ClientAutoPrivateAddressInfoDO clientAutoPrivateAddressInfoDO = clientAutoPrivateAddressInfoDAO.getPrivateAddressById(privateId) ;
        if (clientAutoPrivateAddressInfoDO == null){
            throw new AutoTestRunException(AutoTestRunException.PRIVATE_ADDRESS_IS_NOT_EXIST) ;
        }
        ClientAutoTaskExtendInfoDO clientAutoTaskExtendInfoDO = new ClientAutoTaskExtendInfoDO() ;
        clientAutoTaskExtendInfoDO.setTaskId(taskId);
        clientAutoTaskExtendInfoDO.setPrivateAddress(clientAutoPrivateAddressInfoDO.getConfig()) ;
        //插入扩展信息列
        int count = clientAutoTaskExtendInfoDAO.insertClientAutoTask(clientAutoTaskExtendInfoDO) ;
        if (count > 0){
            return true ;
        }else {
            return false ;
        }
    }
}
