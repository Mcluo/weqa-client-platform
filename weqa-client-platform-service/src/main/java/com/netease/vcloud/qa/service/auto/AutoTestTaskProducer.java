package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoBO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化测试生产者
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 11:33
 */
@Service
public class AutoTestTaskProducer {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;

    @Autowired
    private ClientAutoScriptRunInfoDAO clientAutoScriptRunInfoDAO ;

    public Long productNewAutoTestTask(AutoTestTaskInfoBO autoTestTaskInfoBO){
        if (autoTestTaskInfoBO == null || CollectionUtils.isEmpty(autoTestTaskInfoBO.getScriptList())) {
            AUTO_LOGGER.error("[AutoTestTaskProducer.productNewAutoTestTask] some param is null");
            return null ;
        }
        //创建任务
        boolean addTaskResult = this.addNewAutoTestTaskProduce(autoTestTaskInfoBO) ;
        if (!addTaskResult || autoTestTaskInfoBO.getId() == null){
            AUTO_LOGGER.error("[AutoTestTaskProducer.productNewAutoTestTask] add a new autotest task fail");
            return null ;
        }
        //创建任务下面的脚本
        List<TaskScriptRunInfoBO> taskScriptRunInfoBOList = autoTestTaskInfoBO.getScriptList() ;
        for (TaskScriptRunInfoBO taskScriptRunInfoBO : taskScriptRunInfoBOList){
            taskScriptRunInfoBO.setTaskId(autoTestTaskInfoBO.getId());
        }
        boolean addTaskScriptFlag = this.addTaskScript(taskScriptRunInfoBOList) ;
        if (!addTaskScriptFlag){
            AUTO_LOGGER.error("[AutoTestTaskProducer.productNewAutoTestTask] add task script list fail");
            return null ;
        }
        //任务状态修改为ready
//        int count = clientAutoTaskInfoDAO.updateClientAutoTaskStatus( autoTestTaskInfoBO.getId(), TaskRunStatus.READY.getCode()) ;
//        if (count<1){
//            AUTO_LOGGER.error("[AutoTestTaskProducer.productNewAutoTestTask] update task status exception");
//            return null ;
//        }
        return autoTestTaskInfoBO.getId();
    }

    public void setTaskReady(long taskId){
        int count = clientAutoTaskInfoDAO.updateClientAutoTaskStatus( taskId, TaskRunStatus.READY.getCode()) ;
        if (count<1){
            AUTO_LOGGER.error("[AutoTestTaskProducer.productNewAutoTestTask] update task status exception");
        }
    }

    public void setTaskInitError(long taskId){
        int count = clientAutoTaskInfoDAO.updateClientAutoTaskStatus( taskId, TaskRunStatus.PREPARE_ERROR.getCode()) ;
        if (count<1){
            AUTO_LOGGER.error("[AutoTestTaskProducer.productNewAutoTestTask] update task status exception");
        }
    }


    /**
     * 新增一个新的自动化任务
     * @return
     */
    private boolean addNewAutoTestTaskProduce(AutoTestTaskInfoBO autoTestTaskInfoBO){
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = AutoTestUtils.buildTaskInfoDOByBO(autoTestTaskInfoBO) ;
        if (clientAutoTaskInfoDO == null){
            AUTO_LOGGER.error("[AutoTestTaskProducer.addNewAutoTestTaskProduce] buildTaskInfoDOByBO is null");
            return false ;
        }
        int count = clientAutoTaskInfoDAO.insertNewClientAutoTask(clientAutoTaskInfoDO) ;
        if (count < 1){
            AUTO_LOGGER.error("[AutoTestTaskProducer.addNewAutoTestTaskProduce] insert new AutoTask fail");
            return false ;
        }else {
            autoTestTaskInfoBO.setId(clientAutoTaskInfoDO.getId());
            return true ;
        }
    }

    /**
     * 新增任务下面的一批测试用例
     * @param taskScriptRunInfoBOList
     * @return
     */
    private boolean addTaskScript(List<TaskScriptRunInfoBO> taskScriptRunInfoBOList){
        if (CollectionUtils.isEmpty(taskScriptRunInfoBOList)){
            AUTO_LOGGER.info("[AutoTestTaskProducer.addTaskScript] script list is empty");
            return true ;
        }
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = new ArrayList<ClientAutoScriptRunInfoDO>() ;
        for (TaskScriptRunInfoBO taskScriptRunInfoBO : taskScriptRunInfoBOList){
            ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = AutoTestUtils.buildScriptInfoDOByBO(taskScriptRunInfoBO);
            if (clientAutoScriptRunInfoDO != null){
                clientAutoScriptRunInfoDOList.add(clientAutoScriptRunInfoDO) ;
            }
        }
        int count = clientAutoScriptRunInfoDAO.patchInsertAutoScript(clientAutoScriptRunInfoDOList) ;
        if (count < clientAutoScriptRunInfoDOList.size()){
            AUTO_LOGGER.info("[AutoTestTaskProducer.addTaskScript] script list is empty");
            return false ;
        }else {
            return true ;
        }
    }
}
