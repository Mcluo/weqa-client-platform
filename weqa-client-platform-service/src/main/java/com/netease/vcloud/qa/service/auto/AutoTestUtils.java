package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.auto.ScriptRunStatus;
import com.netease.vcloud.qa.auto.ScriptType;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoBO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 15:34
 */
public class AutoTestUtils {

    public static final String DEFAULT_AUTO_TEST_GIT= "ssh://git@g.hz.netease.com:22222/ClientTest/fusion/GeneralTester.git" ;
    public static final String DEFAULT_GIT_BRANCH= "master" ;

    public static ClientAutoTaskInfoDO buildTaskInfoDOByBO(AutoTestTaskInfoBO autoTestTaskInfoBO){
        if (autoTestTaskInfoBO == null){
            return null ;
        }
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = new ClientAutoTaskInfoDO() ;
        if (autoTestTaskInfoBO.getId()!=null) {
            clientAutoTaskInfoDO.setId(autoTestTaskInfoBO.getId());
        }
        clientAutoTaskInfoDO.setTaskName(autoTestTaskInfoBO.getTaskName());
        if (StringUtils.isNotBlank(autoTestTaskInfoBO.getTaskType())) {
            clientAutoTaskInfoDO.setTaskType(autoTestTaskInfoBO.getTaskType());
        }else {
            //默认为Python任务
            clientAutoTaskInfoDO.setTaskType(ScriptType.PYTHON_SCRIPT);
        }
        clientAutoTaskInfoDO.setTaskName(autoTestTaskInfoBO.getTaskName());
        clientAutoTaskInfoDO.setTestSuitId(autoTestTaskInfoBO.getTestSuitId()) ;
        clientAutoTaskInfoDO.setOperator(autoTestTaskInfoBO.getOperator()) ;
        clientAutoTaskInfoDO.setDeviceInfo(autoTestTaskInfoBO.getDeviceInfo());
        clientAutoTaskInfoDO.setDeviceType(autoTestTaskInfoBO.getDeviceType());
        if (StringUtils.isNotBlank(autoTestTaskInfoBO.getGitInfo())) {
            clientAutoTaskInfoDO.setGitInfo(autoTestTaskInfoBO.getGitInfo());
        }else {
            clientAutoTaskInfoDO.setGitInfo(DEFAULT_AUTO_TEST_GIT);
        }
        if (StringUtils.isNotBlank(autoTestTaskInfoBO.getGitBranch())){
            clientAutoTaskInfoDO.setGitBranch(autoTestTaskInfoBO.getGitBranch());
        }else {
            clientAutoTaskInfoDO.setGitBranch(DEFAULT_GIT_BRANCH);
        }
        if (autoTestTaskInfoBO.getTaskStatus() == null) {
            clientAutoTaskInfoDO.setTaskStatus(TaskRunStatus.INIT.getCode());
        }else {
            clientAutoTaskInfoDO.setTaskStatus(autoTestTaskInfoBO.getTaskStatus().getCode());
        }
        return clientAutoTaskInfoDO ;
    }

    public static ClientAutoScriptRunInfoDO buildScriptInfoDOByBO(TaskScriptRunInfoBO taskScriptRunInfoBO){
        if (taskScriptRunInfoBO == null){
            return null ;
        }
        ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = new ClientAutoScriptRunInfoDO() ;
        if (taskScriptRunInfoBO.getScriptId()!=null) {
            clientAutoScriptRunInfoDO.setScriptTcId(taskScriptRunInfoBO.getScriptId());
        }
        if (taskScriptRunInfoBO.getTaskId()!=null){
            clientAutoScriptRunInfoDO.setTaskId(taskScriptRunInfoBO.getTaskId());
        }
        clientAutoScriptRunInfoDO.setScriptName(taskScriptRunInfoBO.getScriptName());
        clientAutoScriptRunInfoDO.setScriptDetail(taskScriptRunInfoBO.getScriptDetail());
        clientAutoScriptRunInfoDO.setExecClass(taskScriptRunInfoBO.getExecClass());
        clientAutoScriptRunInfoDO.setExecMethod(taskScriptRunInfoBO.getExecMethod());
        clientAutoScriptRunInfoDO.setExecParam(taskScriptRunInfoBO.getExecParam());
        if(taskScriptRunInfoBO.getExecStatus() != null) {
            clientAutoScriptRunInfoDO.setExecParam(taskScriptRunInfoBO.getExecStatus().getStatus());
        }else {
            clientAutoScriptRunInfoDO.setExecStatus(ScriptRunStatus.INIT.getCode());
        }
        return clientAutoScriptRunInfoDO ;
    }

    public static TaskScriptRunInfoBO buildTaskScriptBOByScriptTCDO(ClientScriptTcInfoDO clientScriptTcInfoDO){
        if (clientScriptTcInfoDO == null){
            return null ;
        }
        TaskScriptRunInfoBO taskScriptRunInfoBO = new TaskScriptRunInfoBO() ;
        taskScriptRunInfoBO.setScriptId(clientScriptTcInfoDO.getId());
        taskScriptRunInfoBO.setScriptName(clientScriptTcInfoDO.getScriptName());
        taskScriptRunInfoBO.setScriptDetail(clientScriptTcInfoDO.getScriptDetail());
        taskScriptRunInfoBO.setExecClass(clientScriptTcInfoDO.getExecClass());
        taskScriptRunInfoBO.setExecMethod(clientScriptTcInfoDO.getExecMethod());
        taskScriptRunInfoBO.setExecParam(clientScriptTcInfoDO.getExecParam());
        return taskScriptRunInfoBO ;
    }

    public static ClientScriptTcInfoDO buildClientScriptTcInfoDOByScriptTcDTO(AutoTCScriptInfoDTO autoTCScriptInfoDTO){
        if (autoTCScriptInfoDTO == null){
            return null ;
        }
        ClientScriptTcInfoDO clientScriptTcInfoDO = new ClientScriptTcInfoDO() ;
        clientScriptTcInfoDO.setScriptName(autoTCScriptInfoDTO.getScriptName());
        clientScriptTcInfoDO.setScriptDetail(autoTCScriptInfoDTO.getScriptDetail());
        clientScriptTcInfoDO.setExecClass(autoTCScriptInfoDTO.getExecClass());
        clientScriptTcInfoDO.setExecMethod(autoTCScriptInfoDTO.getExecMethod());
        clientScriptTcInfoDO.setExecParam(autoTCScriptInfoDTO.getExecParam());
        clientScriptTcInfoDO.setScriptOwner(autoTCScriptInfoDTO.getOwner());
        clientScriptTcInfoDO.setTcId(autoTCScriptInfoDTO.getTcId());
        return clientScriptTcInfoDO ;
    }

}
