package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.ScriptRunStatus;
import com.netease.vcloud.qa.auto.ScriptType;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskExtendInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.nos.NosService;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoBO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskUrlDTO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import com.netease.vcloud.qa.service.auto.view.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 18:00
 */
@Service
public class AutoTestTaskManagerService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    private static final String INSTALL_URL = "http://10.219.24.15:5000/api_install";

    private ThreadPoolExecutor executor;
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

    @Autowired
    private AutoTestTaskUrlService autoTestTaskUrlService ;

    @Autowired
    private AutoTestPrivateAddressService autoTestPrivateAddressService ;

    @Autowired
    private NosService nosService ;

    @PostConstruct
    public void init(){
        executor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(100));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

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

    public void setTaskReadySuccess(Long taskId , boolean isSuccess){
        if (isSuccess) {
            autoTestTaskProducer.setTaskReady(taskId);
        }else {
            autoTestTaskProducer.setTaskInitError(taskId);
        }
    }

    /**
     * 触发安装逻辑，但这个操作需移到执行器操作
     * @param deviceList
     * @param array
     * @param taskId
     */
    @Deprecated
    public void installApi(List<Long> deviceList, List<AutoTestTaskUrlDTO> array, long taskId){
        Thread thread = new Thread(() -> {
            List<DeviceInfoVO> deviceInfoVOList = autoTestDeviceService.getDeviceInfoList(deviceList) ;
            //触发下载安装 获取权限
            boolean success = true;
            for(DeviceInfoVO deviceInfoVO : deviceInfoVOList){
                if (deviceInfoVO == null || deviceInfoVO.getOperator()!=null){
                    continue;
                }
                if(deviceInfoVO.getOperator().getName().equals("system")){
                    for(AutoTestTaskUrlDTO dto: array){
                        if (dto == null || StringUtils.isBlank(deviceInfoVO.getPlatform())){
                            continue;
                        }
                        if(deviceInfoVO.getPlatform().equals(dto.getPlatform())){
//                                String url1 = "http://10.219.24.15:5000/api_install";
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("platform", dto.getPlatform());
                            jsonObject.put("download_url", dto.getUrl());
                            JSONObject result = HttpUtils.getInstance().jsonPost(INSTALL_URL,jsonObject.toJSONString());
                            if (result.getInteger("code") != 200){
                                success = false;
                            }
                        }
                    }
                }
            }
            if (success){
                autoTestTaskProducer.setTaskReady(taskId);
            }else {
                autoTestTaskProducer.setTaskInitError(taskId);
            }
        });
        executor.execute(thread);
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
        autoTestTaskInfoBO.setDeviceType(autoTestTaskInfoDTO.getDeviceType());
        List<DeviceInfoVO> deviceInfoVOList = autoTestDeviceService.getDeviceInfoList(autoTestTaskInfoDTO.getDeviceList()) ;
        if (deviceInfoVOList!=null) {
            //设备从DB读取后，需要重新排序
            Map<Long,DeviceInfoVO> deviceMap = new HashMap<Long,DeviceInfoVO>();
            List<DeviceInfoVO> saveDeviceInfoVOList = new ArrayList<DeviceInfoVO>() ;
            for (DeviceInfoVO deviceInfoVO:deviceInfoVOList){
                deviceMap.put(deviceInfoVO.getId(),deviceInfoVO) ;
            }
            for (Long deviceId : autoTestTaskInfoDTO.getDeviceList()){
                DeviceInfoVO deviceInfo = deviceMap.get(deviceId) ;
                if (deviceInfo!=null) {
                    saveDeviceInfoVOList.add(deviceInfo);
                }
            }
            autoTestTaskInfoBO.setDeviceInfo(JSONArray.toJSONString(saveDeviceInfoVOList));
        }
        autoTestTaskInfoBO.setPrivateAddressId(autoTestTaskInfoDTO.getPrivateAddressId());
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

    public TaskInfoListVO queryTaskInfoList(String owner,int pageSize , int pageNo)  throws AutoTestRunException{
        TaskInfoListVO taskInfoListVO = new TaskInfoListVO() ;
        int start = (pageNo-1) * pageSize ;
        if (StringUtils.isBlank(owner)){
            owner = null ;
        }
        List<ClientAutoTaskInfoDO> clientAutoTaskInfoDOList = clientAutoTaskInfoDAO.queryAutoTaskInfo(owner,start,pageSize) ;
        int count = clientAutoTaskInfoDAO.queryAutoTaskInfoCount(owner) ;
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
        //基础信息
        TaskDetailInfoVO taskDetailInfoVO = new TaskDetailInfoVO() ;
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(taskId) ;
        List<TaskUrlInfoVO> taskUrlInfoVOList = autoTestTaskUrlService.getTaskUrlInfoList(taskId) ;
        taskDetailInfoVO.setPackageInfo(taskUrlInfoVOList);
        UserInfoBO userInfoBO = userInfoService.getUserInfoByEmail(clientAutoTaskInfoDO.getOperator()) ;
        TaskBaseInfoVO taskBaseInfoVO = this.buildTaskBaseInfoVOByDO(clientAutoTaskInfoDO,userInfoBO) ;
        taskDetailInfoVO.setBaseInfo(taskBaseInfoVO) ;
        //扩展信息
        List<TaskExtendConfigVO> taskExtendConfigVOList = new ArrayList<>() ;
        taskDetailInfoVO.setExtendList(taskExtendConfigVOList);
        ClientAutoTaskExtendInfoDO clientAutoTaskExtendInfoDO = autoTestPrivateAddressService.getTaskPrivateAddress(taskId) ;
        if (clientAutoTaskExtendInfoDO!=null){
            //私有地址
            TaskExtendConfigVO taskExtendConfigVO = new TaskExtendConfigVO() ;
            taskExtendConfigVO.setType("privateAddress");
            taskExtendConfigVO.setExId(clientAutoTaskExtendInfoDO.getPrivateAddressId());
            taskExtendConfigVO.setExConfig(clientAutoTaskExtendInfoDO.getPrivateAddress());
            taskExtendConfigVOList.add(taskExtendConfigVO) ;
        }
        //脚本以及运行统计
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = clientAutoScriptRunInfoDAO.getClientAutoScriptRunInfoByTaskId(taskId) ;
        Map<ScriptRunStatus,Integer> statusStatisticMap = new HashMap<ScriptRunStatus,Integer>() ;
        if (!CollectionUtils.isEmpty(clientAutoScriptRunInfoDOList)) {
            //处理脚本信息
            List<TaskRunScriptInfoVO> scriptList = new ArrayList<TaskRunScriptInfoVO>() ;
            for (ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO : clientAutoScriptRunInfoDOList) {
                TaskRunScriptInfoVO taskRunScriptInfoVO = new TaskRunScriptInfoVO() ;
                taskRunScriptInfoVO.setTcId(clientAutoScriptRunInfoDO.getScriptTcId());
                taskRunScriptInfoVO.setTaskId(clientAutoScriptRunInfoDO.getTaskId());
                taskRunScriptInfoVO.setName(clientAutoScriptRunInfoDO.getScriptName());
                taskRunScriptInfoVO.setDetail(clientAutoScriptRunInfoDO.getScriptDetail());
                taskRunScriptInfoVO.setExecClass(clientAutoScriptRunInfoDO.getExecClass());
                taskRunScriptInfoVO.setExecMethod(clientAutoScriptRunInfoDO.getExecMethod());
                taskRunScriptInfoVO.setExecParam(clientAutoScriptRunInfoDO.getExecParam());
                taskRunScriptInfoVO.setSpendTime(clientAutoScriptRunInfoDO.getRunTime());
                taskRunScriptInfoVO.setErrorInfo(clientAutoScriptRunInfoDO.getErrorInfo());
                taskRunScriptInfoVO.setRunScriptId(clientAutoScriptRunInfoDO.getId());
                ScriptRunStatus scriptRunStatus = ScriptRunStatus.getStatusByCode(clientAutoScriptRunInfoDO.getExecStatus()) ;
                if (scriptRunStatus!=null) {
                    taskRunScriptInfoVO.setStatus(scriptRunStatus.getStatus());
                    Integer scriptCount = statusStatisticMap.get(scriptRunStatus) ;
                    if (scriptCount == null){
                        scriptCount = 1 ;
                    }else {
                        scriptCount = scriptCount + 1 ;
                    }
                    statusStatisticMap.put(scriptRunStatus , scriptCount) ;
                }
                if (StringUtils.isNotBlank(clientAutoScriptRunInfoDO.getLogInfo())){
                    String nosUrl = nosService.getDownFileUrl(clientAutoScriptRunInfoDO.getLogInfo()) ;
                    taskRunScriptInfoVO.setLogUrl(nosUrl);
                }
                scriptList.add(taskRunScriptInfoVO) ;
            }
            taskDetailInfoVO.setScriptList(scriptList);
            //处理统计结果
            TaskStatisticInfoVO taskStatisticInfoVO = new TaskStatisticInfoVO() ;
            taskStatisticInfoVO.setWaitingNumber(statusStatisticMap.get(ScriptRunStatus.INIT)==null?0:statusStatisticMap.get(ScriptRunStatus.INIT));
            taskStatisticInfoVO.setRunningNumber(statusStatisticMap.get(ScriptRunStatus.RUNNING)==null?0:statusStatisticMap.get(ScriptRunStatus.RUNNING));
            taskStatisticInfoVO.setSuccessNumber(statusStatisticMap.get(ScriptRunStatus.SUCCESS)==null?0:statusStatisticMap.get(ScriptRunStatus.SUCCESS));
            taskStatisticInfoVO.setCancelNumber(statusStatisticMap.get(ScriptRunStatus.CANCEL)==null?0:statusStatisticMap.get(ScriptRunStatus.CANCEL));
            int failCount = statusStatisticMap.get(ScriptRunStatus.FAIL)==null?0:statusStatisticMap.get(ScriptRunStatus.FAIL);
            int exceptionCount = statusStatisticMap.get(ScriptRunStatus.WARNING)==null?0:statusStatisticMap.get(ScriptRunStatus.WARNING) ;
            taskStatisticInfoVO.setFailNumber(failCount + exceptionCount);
            taskStatisticInfoVO.setTotal(scriptList.size());
            if (taskStatisticInfoVO.getWaitingNumber()>0){
                taskStatisticInfoVO.setSuccessRate("-");
            }else {
//                double successRate = taskStatisticInfoVO.getSuccessNumber() * 100 / taskStatisticInfoVO.getTotal() ;
                if (taskStatisticInfoVO.getSuccessNumber() == 0 && taskStatisticInfoVO.getFailNumber() == 0){
                    taskStatisticInfoVO.setSuccessRate( "0%");
                }else {
                    double successRate = taskStatisticInfoVO.getSuccessNumber() * 100 / (taskStatisticInfoVO.getSuccessNumber() + taskStatisticInfoVO.getFailNumber());
                    taskStatisticInfoVO.setSuccessRate(successRate + "%");
                }
            }
            taskDetailInfoVO.setStatisticInfo(taskStatisticInfoVO);
        }
        return taskDetailInfoVO ;
    }

    /**
     * @param logScriptId
     * @return
     */
    public ScriptRunLogVO getScriptRunLog(Long logScriptId) {
        ClientAutoScriptRunInfoDO clientAutoScriptRunInfoById = clientAutoScriptRunInfoDAO.getClientAutoScriptRunInfoById(logScriptId) ;
        if (clientAutoScriptRunInfoById == null){
            return null ;
        }
        ScriptRunLogVO scriptRunLogVO = new ScriptRunLogVO() ;
        if (StringUtils.isBlank(clientAutoScriptRunInfoById.getLogInfo())){
            return scriptRunLogVO ;
        }
        InputStream inputStream = nosService.getFile(clientAutoScriptRunInfoById.getLogInfo()) ;
        if (inputStream == null){
            return scriptRunLogVO ;
        }
        try {
//            byte[] bytes = new byte[inputStream.available()];
//            inputStream.read(bytes);
//            String logStr = new String(bytes, Charset.forName("UTF-8").name());
//            scriptRunLogVO.setLog(logStr);

            StringBuilder stringBuilder = new StringBuilder();
            String line ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Charset.forName("UTF-8").name())) ;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n") ;
//                stringBuilder.append(line);
            }
            scriptRunLogVO.setLog(stringBuilder.toString());
        }catch (IOException e){
            AUTO_LOGGER.error("[AutoTestTaskManagerService.getScriptRunLog] read nos file exception",e);
        }finally {
            try {
                inputStream.close();
            }catch (IOException e){
                AUTO_LOGGER.error("[AutoTestTaskManagerService.getScriptRunLog] close inputStream exception",e);
            }
        }
        return scriptRunLogVO ;
    }

	 public boolean cancelAutoTask(Long taskId) throws AutoTestRunException{
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(taskId) ;
        if (clientAutoTaskInfoDO == null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_TASK_IS_NOT_EXIST) ;
        }
        if (TaskRunStatus.isTaskFinish(clientAutoTaskInfoDO.getTaskStatus())){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_TASK_STATUS_EXCEPTION) ;
        }
        int count = clientAutoTaskInfoDAO.updateClientAutoTaskStatus(taskId,TaskRunStatus.CANCEL.getCode()) ;
        if (count<1){
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION);
        }
        clientAutoScriptRunInfoDAO.updateStatusByTaskAndStatus(taskId,ScriptRunStatus.INIT.getCode(),ScriptRunStatus.CANCEL.getCode()) ;
        return true ;
    }

    public List<Long> getDeviceIds(Long taskId) throws AutoTestRunException{
        ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(taskId) ;
        if (clientAutoTaskInfoDO == null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_TASK_IS_NOT_EXIST) ;
        }
        String deviceInfo = clientAutoTaskInfoDO.getDeviceInfo();
        List<DeviceInfoVO> deviceInfoVOList = JSONArray.parseArray(clientAutoTaskInfoDO.getDeviceInfo(),DeviceInfoVO.class) ;
        ArrayList<Long> ids = new ArrayList<>();
        for(DeviceInfoVO deviceInfoVO : deviceInfoVOList){
            ids.add(deviceInfoVO.getId());
        }
        return ids;
    }

}
