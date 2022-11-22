package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.dao.ClientScriptTcInfoDAO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/16 17:01
 */
@Service
public class AutoTcScriptService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    @Autowired
    private ClientScriptTcInfoDAO clientScriptTcInfoDAO ;

    /**
     * 根据ID，获取对应的脚本信息
     * @param scriptIdSet
     * @return
     */
    public List<TaskScriptRunInfoBO> getScriptByIdSet(List<Long> scriptIdSet){
        if (CollectionUtils.isEmpty(scriptIdSet)){
            AUTO_LOGGER.error("[AutoTcScriptService.getScriptByIdSet] scriptIdSet is empty");
            return null ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScriptSet(scriptIdSet) ;
        List<TaskScriptRunInfoBO> taskScriptRunInfoBOList = new ArrayList<TaskScriptRunInfoBO>() ;
        if (CollectionUtils.isEmpty(clientScriptTcInfoDOList)){
            AUTO_LOGGER.warn("[AutoTcScriptService.getScriptByIdSet] clientScriptTcInfoDOList is empty");
            return taskScriptRunInfoBOList ;
        }
        for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
            TaskScriptRunInfoBO taskScriptRunInfoBO = AutoTestUtils.buildTaskScriptBOByScriptTCDO(clientScriptTcInfoDO) ;
            if (taskScriptRunInfoBO != null){
                taskScriptRunInfoBOList.add(taskScriptRunInfoBO) ;
            }
        }
        return taskScriptRunInfoBOList ;
    }

    /**
     * 根据批量设置脚本信息
     * @param autoTCScriptInfoDTOList
     * @return
     */
    public boolean addScriptInfo(List<AutoTCScriptInfoDTO> autoTCScriptInfoDTOList){
        if (CollectionUtils.isEmpty(autoTCScriptInfoDTOList)){
            return false ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = new ArrayList<ClientScriptTcInfoDO>() ;
        for (AutoTCScriptInfoDTO autoTCScriptInfoDTO : autoTCScriptInfoDTOList){
            ClientScriptTcInfoDO clientScriptTcInfoDO = AutoTestUtils.buildClientScriptTcInfoDOByScriptTcDTO(autoTCScriptInfoDTO) ;
            if (clientScriptTcInfoDO != null) {
                clientScriptTcInfoDOList.add(clientScriptTcInfoDO);
            }
        }
        int count = clientScriptTcInfoDAO.patchInsertClientScript(clientScriptTcInfoDOList) ;
        if (count < clientScriptTcInfoDOList.size()){
            AUTO_LOGGER.error("[AutoTcScriptService.setScriptInfo] add data fail");
            return false ;
        }else {
            return true ;
        }
    }

    public AutoScriptListVO getAllScript(Integer pageNo , Integer pageSize) throws AutoTestRunException{
        AutoScriptListVO autoScriptListVO = new AutoScriptListVO() ;
        int start = pageNo == null ? 0 : (pageNo - 1) * pageSize ;
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScript(start,pageSize) ;
        int total = clientScriptTcInfoDAO.getClientScriptCount() ;
        autoScriptListVO.setCurrent(pageNo);
        autoScriptListVO.setSize(pageSize);
        autoScriptListVO.setTotal(total);
        if (!CollectionUtils.isEmpty(clientScriptTcInfoDOList)){
            List<AutoScriptInfoVO> autoScriptInfoVOList = new ArrayList<AutoScriptInfoVO>() ;
            for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
                AutoScriptInfoVO autoScriptInfoVO = this.buildScriptListVOByDO(clientScriptTcInfoDO);
                if (autoScriptInfoVO!=null){
                    autoScriptInfoVOList.add(autoScriptInfoVO) ;
                }
            }
            autoScriptListVO.setAutoScriptInfoVOList(autoScriptInfoVOList);
        }
        return autoScriptListVO ;
    }

    private AutoScriptInfoVO buildScriptListVOByDO(ClientScriptTcInfoDO clientScriptTcInfoDO){
        if (clientScriptTcInfoDO == null){
            return null ;
        }
        AutoScriptInfoVO autoScriptInfoVO = new AutoScriptInfoVO() ;
        autoScriptInfoVO.setId(clientScriptTcInfoDO.getId());
        autoScriptInfoVO.setName(clientScriptTcInfoDO.getScriptName());
        autoScriptInfoVO.setDetail(clientScriptTcInfoDO.getScriptDetail());
        autoScriptInfoVO.setExecClass(clientScriptTcInfoDO.getExecClass());
        autoScriptInfoVO.setExecMethod(clientScriptTcInfoDO.getExecMethod());
        autoScriptInfoVO.setExecParam(clientScriptTcInfoDO.getExecParam());
        //user info 暂时不加
        return autoScriptInfoVO ;
    }

}
