package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.auto.ScriptType;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoBO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 18:00
 */
@Service
public class AutoTestManagerService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    @Autowired
    private AutoTestTaskProducer autoTestTaskProducer ;

    public boolean addNewTaskInfoInfo(AutoTestTaskInfoDTO autoTestTaskInfoDTO) throws AutoTestRunException{
        if (autoTestTaskInfoDTO == null){
            AUTO_LOGGER.error("[AutoTestManagerService.addNewTaskInfoInfo] autoTestInfoDTO is null");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        AutoTestTaskInfoBO autoTestTaskInfoBO = this.buildAutoTestTaskInfoBOBaseInfo(autoTestTaskInfoDTO) ;
        List<TaskScriptRunInfoBO>  taskScriptRunInfoBOList = this.buildAutoTestTaskScriptBOInfo(autoTestTaskInfoDTO.getTestSuitId(),autoTestTaskInfoDTO.getTestCaseScriptId()) ;
        if (CollectionUtils.isEmpty(taskScriptRunInfoBOList)){
            AUTO_LOGGER.error("[AutoTestManagerService.addNewTaskInfoInfo] taskScriptRunInfoBOList is empty");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SCRIPT_IS_NULL) ;
        }
        autoTestTaskInfoBO.setScriptList(taskScriptRunInfoBOList);
        boolean flag = autoTestTaskProducer.productNewAutoTestTask(autoTestTaskInfoBO) ;
        return flag ;
    }

    /**
     * 创建一个仅包含基础信息的BO
     * @param autoTestTaskInfoDTO
     * @return
     */
    private AutoTestTaskInfoBO buildAutoTestTaskInfoBOBaseInfo(AutoTestTaskInfoDTO autoTestTaskInfoDTO){
        //内部方法，不做校验
        AutoTestTaskInfoBO autoTestTaskInfoBO = new AutoTestTaskInfoBO() ;
        autoTestTaskInfoBO.setTaskName(autoTestTaskInfoDTO.getTaskName());
        if (autoTestTaskInfoDTO.getTaskType()!=null) {
            autoTestTaskInfoBO.setTaskType(autoTestTaskInfoDTO.getTaskType());
        }else {
            autoTestTaskInfoBO.setTaskType(ScriptType.PYTHON_SCRIPT);
        }
        autoTestTaskInfoBO.setGitInfo(autoTestTaskInfoDTO.getGitInfo());
        autoTestTaskInfoBO.setGitBranch(autoTestTaskInfoDTO.getGitBranch());
        autoTestTaskInfoBO.setOperator(autoTestTaskInfoDTO.getOperator());
        return autoTestTaskInfoBO ;
    }


    private List<TaskScriptRunInfoBO> buildAutoTestTaskScriptBOInfo(Long testSuitId ,List<Long> testScriptList){
        List<TaskScriptRunInfoBO> taskScriptRunInfoBOList = new ArrayList<TaskScriptRunInfoBO>() ;
        //todo 暂时先添加测试数据
        for (Long scriptId : testScriptList) {
            TaskScriptRunInfoBO taskScriptRunInfoBO = new TaskScriptRunInfoBO();
            taskScriptRunInfoBO.setTaskId(scriptId);
            taskScriptRunInfoBO.setDevicesInfo("{}");
            taskScriptRunInfoBO.setScriptName("摄像头采集");
            taskScriptRunInfoBO.setScriptDetail("1080*1920p 7fps");
            taskScriptRunInfoBO.setExecClass("Client_Video_Beauty");
            taskScriptRunInfoBO.setExecMethod("test_start_beauty_with_different_profile_rate_config");
            taskScriptRunInfoBO.setExecParam("4, 7");
            taskScriptRunInfoBOList.add(taskScriptRunInfoBO) ;
        }
        return taskScriptRunInfoBOList ;
    }

}
