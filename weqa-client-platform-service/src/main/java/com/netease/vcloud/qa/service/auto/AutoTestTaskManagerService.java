package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSONArray;
import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.ScriptRunStatus;
import com.netease.vcloud.qa.auto.ScriptType;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoBO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import com.netease.vcloud.qa.service.auto.view.TaskBaseInfoVO;
import com.netease.vcloud.qa.service.auto.view.TaskDetailInfoVO;
import com.netease.vcloud.qa.service.auto.view.TaskInfoListVO;
import com.netease.vcloud.qa.service.auto.view.TaskRunScriptInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 18:00
 */
@Service
public class AutoTestTaskManagerService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    @Autowired
    private AutoTestTaskProducer autoTestTaskProducer ;

    @Autowired
    private AutoTcScriptService autoTcScriptService ;

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;

    @Autowired
    private ClientAutoScriptRunInfoDAO clientAutoScriptRunInfoDAO ;

    @Autowired
    private UserInfoService userInfoService ;

    @Autowired
    private AutoTestDeviceService  autoTestDeviceService ;

    public Long addNewTaskInfo(AutoTestTaskInfoDTO autoTestTaskInfoDTO) throws AutoTestRunException{
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
        return autoTestTaskProducer.productNewAutoTestTask(autoTestTaskInfoBO) ;
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
        List<DeviceInfoVO> deviceInfoVOList = autoTestDeviceService.getDeviceInfoList(autoTestTaskInfoDTO.getDeviceList()) ;
        if (deviceInfoVOList!=null) {
            autoTestTaskInfoBO.setDeviceInfo(JSONArray.toJSONString(deviceInfoVOList));
        }
        return autoTestTaskInfoBO ;
    }


    private List<TaskScriptRunInfoBO> buildAutoTestTaskScriptBOInfo(Long testSuitId ,List<Long> testScriptList) throws AutoTestRunException{
        if (testSuitId == null && CollectionUtils.isEmpty(testScriptList)){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SCRIPT_ID_EMPTY) ;
        }
        List<TaskScriptRunInfoBO> taskScriptRunInfoBOList =  autoTcScriptService.getScriptByIdSet(testScriptList);
//        //测试数据
//        for (Long scriptId : testScriptList) {
//            TaskScriptRunInfoBO taskScriptRunInfoBO = new TaskScriptRunInfoBO();
//            taskScriptRunInfoBO.setTaskId(scriptId);
//            taskScriptRunInfoBO.setDevicesInfo("{}");
//            taskScriptRunInfoBO.setScriptName("摄像头采集");
//            taskScriptRunInfoBO.setScriptDetail("1080*1920p 7fps");
//            taskScriptRunInfoBO.setExecClass("Client_Video_Beauty");
//            taskScriptRunInfoBO.setExecMethod("test_start_beauty_with_different_profile_rate_config");
//            taskScriptRunInfoBO.setExecParam("4, 7");
//            taskScriptRunInfoBOList.add(taskScriptRunInfoBO) ;
//        }
        return taskScriptRunInfoBOList ;
    }

    public TaskInfoListVO queryTaskInfoList(int pageSize , int pageNo)  throws AutoTestRunException{
        TaskInfoListVO taskInfoListVO = new TaskInfoListVO() ;
        int start = (pageNo-1) * pageSize ;
        List<ClientAutoTaskInfoDO> clientAutoTaskInfoDOList = clientAutoTaskInfoDAO.queryAutoTaskInfo(start,pageSize) ;
        int count = clientAutoTaskInfoDAO.queryAutoTaskInfoCount() ;
        taskInfoListVO.setCurrent(pageNo);
        taskInfoListVO.setSize(pageSize);
        taskInfoListVO.setTotal(count);
        if (!CollectionUtils.isEmpty(clientAutoTaskInfoDOList)){
            Set<String> emailSet = new HashSet<String>() ;
            for (ClientAutoTaskInfoDO clientAutoTaskInfoDO : clientAutoTaskInfoDOList){
                if (clientAutoTaskInfoDO!=null && StringUtils.isNotBlank(clientAutoTaskInfoDO.getOperator())){
                    emailSet.add(clientAutoTaskInfoDO.getOperator()) ;
                }
            }
            Map<String,UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(emailSet) ;
            List<TaskBaseInfoVO> taskBaseInfoVOList = new ArrayList<TaskBaseInfoVO>() ;
            for (ClientAutoTaskInfoDO clientAutoTaskInfoDO : clientAutoTaskInfoDOList){
                UserInfoBO userInfoBO = userInfoBOMap.get(clientAutoTaskInfoDO.getOperator()) ;
                TaskBaseInfoVO taskBaseInfoVO = this.buildTaskBaseInfoVOByDO(clientAutoTaskInfoDO,userInfoBO) ;
                if (taskBaseInfoVO!=null) {
                    taskBaseInfoVOList.add(taskBaseInfoVO);
                }
            }
            taskInfoListVO.setTaskInfoList(taskBaseInfoVOList);
        }
        return taskInfoListVO ;
    }

    private TaskBaseInfoVO buildTaskBaseInfoVOByDO(ClientAutoTaskInfoDO clientAutoTaskInfoDO,UserInfoBO userInfoBO){
        if (clientAutoTaskInfoDO == null){
            return null ;
        }
        TaskBaseInfoVO taskBaseInfoVO = new TaskBaseInfoVO() ;
        taskBaseInfoVO.setId(clientAutoTaskInfoDO.getId());
        taskBaseInfoVO.setTaskName(clientAutoTaskInfoDO.getTaskName());
        taskBaseInfoVO.setTaskType(clientAutoTaskInfoDO.getTaskType());
        taskBaseInfoVO.setGitInfo(clientAutoTaskInfoDO.getGitInfo());
        taskBaseInfoVO.setBranch(clientAutoTaskInfoDO.getGitBranch());
        taskBaseInfoVO.setStartTime(clientAutoTaskInfoDO.getGmtCreate().getTime());
        taskBaseInfoVO.setUserInfo(CommonUtils.buildUserInfoVOByBO(userInfoBO));
        TaskRunStatus taskRunStatus = TaskRunStatus.getTaskRunStatusByCode(clientAutoTaskInfoDO.getTaskStatus()) ;
        if (taskRunStatus!=null) {
            taskBaseInfoVO.setStatus(taskRunStatus.getStatus());
        }
        if (StringUtils.isNotBlank(clientAutoTaskInfoDO.getDeviceInfo())){
            try{
                List<DeviceInfoVO> deviceInfoVOList = JSONArray.parseArray(clientAutoTaskInfoDO.getDeviceInfo(),DeviceInfoVO.class) ;
                if (deviceInfoVOList!=null){
                    taskBaseInfoVO.setDeviceList(deviceInfoVOList);
                }
            }catch (Exception e){
                AUTO_LOGGER.error("[AutoTestTaskManagerService.buildTaskBaseInfoVOByDO] parse device object exception",e);
            }
        }
        return taskBaseInfoVO ;
    }

    public TaskDetailInfoVO getTaskDetailInfo(Long taskId) throws AutoTestRunException{
        TaskDetailInfoVO taskDetailInfoVO = new TaskDetailInfoVO() ;
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(taskId) ;
        UserInfoBO userInfoBO = userInfoService.getUserInfoByEmail(clientAutoTaskInfoDO.getOperator()) ;
        TaskBaseInfoVO taskBaseInfoVO = this.buildTaskBaseInfoVOByDO(clientAutoTaskInfoDO,userInfoBO) ;
        taskDetailInfoVO.setBaseInfo(taskBaseInfoVO) ;
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = clientAutoScriptRunInfoDAO.getClientAutoScriptRunInfoByTaskId(taskId) ;
        if (!CollectionUtils.isEmpty(clientAutoScriptRunInfoDOList)) {
            List<TaskRunScriptInfoVO> scriptList = new ArrayList<TaskRunScriptInfoVO>() ;
            for (ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO : clientAutoScriptRunInfoDOList) {
                TaskRunScriptInfoVO taskRunScriptInfoVO = new TaskRunScriptInfoVO() ;
                taskRunScriptInfoVO.setTaskId(clientAutoScriptRunInfoDO.getTaskId());
                taskRunScriptInfoVO.setName(clientAutoScriptRunInfoDO.getScriptName());
                taskRunScriptInfoVO.setDetail(clientAutoScriptRunInfoDO.getScriptDetail());
                taskRunScriptInfoVO.setExecClass(clientAutoScriptRunInfoDO.getExecClass());
                taskRunScriptInfoVO.setExecMethod(clientAutoScriptRunInfoDO.getExecMethod());
                taskRunScriptInfoVO.setExecParam(clientAutoScriptRunInfoDO.getExecParam());
                taskRunScriptInfoVO.setSpendTime(clientAutoScriptRunInfoDO.getRunTime());
                taskRunScriptInfoVO.setErrorInfo(clientAutoScriptRunInfoDO.getErrorInfo());
                ScriptRunStatus scriptRunStatus = ScriptRunStatus.getStatusByCode(clientAutoScriptRunInfoDO.getExecStatus()) ;
                if (scriptRunStatus!=null) {
                    taskRunScriptInfoVO.setStatus(scriptRunStatus.getStatus());
                }
                scriptList.add(taskRunScriptInfoVO) ;
            }
            taskDetailInfoVO.setScriptList(scriptList);
        }
        return taskDetailInfoVO ;
    }
}
