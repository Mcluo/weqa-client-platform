package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONArray;
import com.netease.vcloud.qa.model.VcloudClientQsAppDO;
import com.netease.vcloud.qa.model.VcloudClientQsTaskDO;
import com.netease.vcloud.qa.model.VcloudClientScheduledTaskInfoDO;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.*;
import com.netease.vcloud.qa.service.auto.data.AutoTestBugDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskUrlDTO;
import com.netease.vcloud.qa.service.auto.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 13:48
 */
@RestController
@RequestMapping("/auto/task")
public class AutoTestTaskController {


    @Autowired
    private AutoTestTaskManagerService autoTestTaskManagerService;

    @Autowired
    private AutoTestScheduledService scheduledService;

    @Autowired
    private AutoTestTaskUrlService autoTestTaskUrlService;

    @Autowired
    private AutoTestDeviceService autoTestDeviceService;

    @Autowired
    private AutoTestQsService qsService;

    @Autowired
    private AutoTestSupportService autoTestSupportService ;

    /**
     * 创建自动化测试任务
     * @param taskName
     * @param gitInfo
     * @param gitBranch
     * @param operator
     * @param idSet
     * @return
     */
    @RequestMapping("/create")
    public ResultVO createAutoTask(@RequestParam("taskName") String taskName,
                                  @RequestParam(name = "gitInfo" ,required = false) String gitInfo,
                                  @RequestParam("gitBranch") String gitBranch,
                                  @RequestParam("operator") String operator,
                                  @RequestParam(name = "deviceType", required = false, defaultValue = "0") byte deviceType,
                                  @RequestParam("device") List<Long> deviceList,
                                  @RequestParam("ids") List<Long> idSet,
                                   @RequestParam(name = "urls" ,required = false ) String urls,
                                   @RequestParam(name ="private",required = false)Long privateAddressId,
                                   @RequestParam(name = "projectId",required = false)Long projectId,
                                   @RequestParam(name = "buildGroup",required = false)String buildGroupId){
        ResultVO resultVO = null ;
        Long id = null ;
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
        autoTestTaskInfoDTO.setTaskName(taskName);
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setGitInfo(gitInfo);
        autoTestTaskInfoDTO.setGitBranch(gitBranch);
        autoTestTaskInfoDTO.setOperator(operator);
        autoTestTaskInfoDTO.setDeviceType(deviceType);
        autoTestTaskInfoDTO.setDeviceList(deviceList);
        autoTestTaskInfoDTO.setTestCaseScriptId(idSet);
        autoTestTaskInfoDTO.setPrivateAddressId(privateAddressId);
        autoTestTaskInfoDTO.setProjectId(projectId);
        autoTestTaskInfoDTO.setBuildGroupId(buildGroupId);
        try {
            id = autoTestTaskManagerService.addNewTaskInfo(autoTestTaskInfoDTO);
            autoTestDeviceService.updateDeviceRun(deviceList, (byte)1);
            List<AutoTestTaskUrlDTO> deviceArray = JSONArray.parseArray(urls, AutoTestTaskUrlDTO.class);
            if (!CollectionUtils.isEmpty(deviceArray)) {
                for (AutoTestTaskUrlDTO dto : deviceArray) {
                    autoTestTaskUrlService.addTaskUrl(dto.getPlatform(), id, dto.getUrl());
                }
            }
            //准备工作完毕，交由执行器处理
            autoTestTaskManagerService.setTaskReadySuccess(id,true);
//            autoTestTaskManagerService.installApi(deviceList, deviceArray, id);

        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
            return resultVO ;
        }
        if (id!=null){
            resultVO = ResultUtils.buildSuccess(id) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    /**
     *  http://127.0.0.1:8788/g2-client/auto/task/query
     * 查询自动化测试信息列表
     * @param size
     * @param pageNo
     * @return
     */
    @RequestMapping("/query")
    public ResultVO queryAutoTaskList(@RequestParam(name = "owner",required = false) String owner,
                                      @RequestParam(name = "size" , required = false , defaultValue = "20")int size ,
                                      @RequestParam(name = "page" , required = false , defaultValue = "1") int pageNo){
        ResultVO resultVO = null ;
        try {
            TaskInfoListVO taskInfoListVO = autoTestTaskManagerService.queryTaskInfoList(owner,size, pageNo);
            if (taskInfoListVO!=null){
                resultVO = ResultUtils.buildSuccess( taskInfoListVO) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/auto/task/get?id=1
     * @param taskId
     * @return
     */
    @RequestMapping("/get")
    public ResultVO  queryAutoTaskDetail(@RequestParam("id") Long taskId){
        ResultVO resultVO = null ;
        try {
            TaskDetailInfoVO taskDetailInfoVO = autoTestTaskManagerService.getTaskDetailInfo(taskId) ;
            if (taskDetailInfoVO != null){
                resultVO = ResultUtils.buildSuccess(taskDetailInfoVO);
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.build(false,e.getExceptionInfo()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/auto/task/log/get?id=1
     * @param runScriptId
     * @return
     */
    @RequestMapping("/log/get")
    public ResultVO  queryScriptRunLog(@RequestParam("id") Long runScriptId) {
        ResultVO resultVO = null;
        ScriptRunLogVO scriptRunLogVO = autoTestTaskManagerService.getScriptRunLog(runScriptId);
        if (scriptRunLogVO != null) {
            resultVO = ResultUtils.buildSuccess(scriptRunLogVO);
        } else {
            resultVO = ResultUtils.buildFail();
        }
        return resultVO;
    }


	/**
     * http://127.0.0.1:8788/g2-client/auto/task/cancel?id=29
     * @param taskId
     * @return
     */
    @RequestMapping("/cancel")
    public ResultVO cancelAutoTask(@RequestParam("id") Long taskId){
        ResultVO resultVO = null ;
        try {
            boolean flag = autoTestTaskManagerService.cancelAutoTask(taskId) ;
            List<Long> deviceIds = autoTestTaskManagerService.getDeviceIds(taskId);
            autoTestDeviceService.updateDeviceRun(deviceIds, (byte)0);
            resultVO = ResultUtils.build(flag) ;
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.build(false,e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    @RequestMapping("/scheduled/create")
    public ResultVO createScheduledTask(@RequestParam("taskName") String taskName,
                                        @RequestParam("cron") String cron,
                                        @RequestParam(name = "gitInfo", required = false) String gitInfo,
                                        @RequestParam("gitBranch") String gitBranch,
                                        @RequestParam("operator") String operator,
                                        @RequestParam(name = "deviceType", required = false, defaultValue = "0") byte deviceType,
                                        @RequestParam("ids") List<Long> idSet,
                                        @RequestParam(name = "private", required = false) Long privateAddressId) throws ParseException {
        ResultVO resultVO = null;
        Long id = null ;
        VcloudClientScheduledTaskInfoDO taskInfoDO = new VcloudClientScheduledTaskInfoDO() ;
        taskInfoDO.setTaskName(taskName);
        taskInfoDO.setGitInfo(gitInfo);
        taskInfoDO.setGitBranch(gitBranch);
        taskInfoDO.setOperator(operator);
        taskInfoDO.setCron(cron);
        taskInfoDO.setTaskStatus((byte)1);
        taskInfoDO.setScriptIds(idSet.toString());
        taskInfoDO.setPrivateId(String.valueOf(privateAddressId));
        id = scheduledService.createScheduledTask(taskInfoDO);
        if (id!=null){
            resultVO = ResultUtils.buildSuccess(id) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    @RequestMapping("/scheduled/query")
    public ResultVO queryScheduledTask(@RequestParam(name = "owner",required = false) String owner,
                                        @RequestParam(name = "size" , required = false , defaultValue = "20")int size ,
                                        @RequestParam(name = "page" , required = false , defaultValue = "1") int pageNo){
        ResultVO resultVO = null ;
        List<VcloudClientScheduledTaskInfoDO> scheduledServiceList = scheduledService.getList(owner,size, pageNo);
        resultVO = ResultUtils.buildSuccess( scheduledServiceList) ;
        return resultVO ;
    }

    @RequestMapping("/scheduled/get")
    public ResultVO  queryAutoScheduledTaskDetail(@RequestParam("id") Long taskId){
        ResultVO resultVO = null ;
        AutoTestScheduledVO scheduledVO = scheduledService.getId(taskId);
        if (scheduledVO != null){
            resultVO = ResultUtils.buildSuccess(scheduledVO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    @RequestMapping("/scheduled/update/status")
    public ResultVO scheduledUpdateStatus(@RequestParam("id") Long taskId,
                                          @RequestParam("status") int status){
        ResultVO resultVO = null ;
        long id = 0;
        try {
            id = scheduledService.updateScheduledStatus(taskId,status);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (id != 0){
            resultVO = ResultUtils.buildSuccess(id) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    @RequestMapping("/qs/create")
    public ResultVO createQsTask(@RequestParam("taskName") String taskName,
                                 @RequestParam(name = "gitInfo", required = false) String gitInfo,
                                 @RequestParam("gitBranch") String gitBranch,
                                 @RequestParam("operator") String operator,
                                 @RequestParam(name = "deviceType", required = false, defaultValue = "0") byte deviceType,
                                 @RequestParam("device") List<Long> deviceList,
                                 @RequestParam(name = "private", required = false) Long privateAddressId,
                                 @RequestParam(name = "projectId",required = false)Long projectId,
                                 @RequestParam(name = "appId",required = false) Long appId,
                                 @RequestParam(name = "startTime" , required = false )Long startTime ,
                                 @RequestParam(name = "endTime" , required = false ) Long endTime,
                                 @RequestParam(name = "typicalSceneNum" , required = false )Integer typicalSceneNum ,
                                 @RequestParam(name = "sampleNum" , required = false )Integer sampleNum) throws ParseException {
        VcloudClientQsTaskDO qsTaskDO = new VcloudClientQsTaskDO();
        qsTaskDO.setTaskName(taskName);
        qsTaskDO.setGitInfo(gitInfo);
        qsTaskDO.setGitBranch(gitBranch);
        qsTaskDO.setOperator(operator);
        qsTaskDO.setDeviceType(deviceType);
        qsTaskDO.setDeviceInfo("111");
        qsTaskDO.setPrivateId(String.valueOf(privateAddressId));
        qsTaskDO.setProjectId(projectId);
        qsTaskDO.setQsAppId(appId);
        qsTaskDO.setStartTime(new Date(startTime));
        qsTaskDO.setEndTime(new Date(endTime));
        qsTaskDO.setSampleNum(sampleNum);
        qsTaskDO.setTypicalSceneNum(typicalSceneNum);
        int id = qsService.addQsTask(qsTaskDO, deviceList);
        ResultVO resultVO = null;
        if (id > 0){
            resultVO = ResultUtils.buildSuccess(id) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    @RequestMapping("/qs/queryQsSceneCount")
    public ResultVO queryQsSceneCount(@RequestParam(name = "appId",required = false) Long appId,
                                @RequestParam(name = "startTime" , required = false )Long startTime ,
                                @RequestParam(name = "endTime" , required = false ) Long endTime){
        ResultVO resultVO = null;

        int count =qsService.getQsSceneCount(appId,new Date(startTime),new Date(endTime));
        resultVO = ResultUtils.buildSuccess(count) ;
        return resultVO ;
    }

    @RequestMapping("/qs/getAppInfo")
    public ResultVO  queryAutoQsTaskDetail(){
        ResultVO resultVO = null ;
        List<VcloudClientQsAppDO> appDOList = qsService.getAppInfo();
        resultVO = ResultUtils.buildSuccess(appDOList) ;
        return resultVO ;
    }

    @RequestMapping("/qs/query")
    public ResultVO queryQsTaskList(@RequestParam(name = "owner", required = false) String owner,
                                    @RequestParam(name = "size", required = false, defaultValue = "20") int size,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int pageNo) {
        ResultVO resultVO = null;
        QsTaskInfoListVO qsTaskInfoListVO = qsService.getList(owner, size, pageNo);
        resultVO = ResultUtils.buildSuccess(qsTaskInfoListVO);
        return resultVO;
    }

    @RequestMapping("/qs/get")
    public ResultVO  queryAutoQsTaskDetail(@RequestParam("id") Long taskId){
        ResultVO resultVO = null ;
        VcloudClientQsTaskVO qsTaskVO = qsService.getTask(taskId);
        if (qsTaskVO != null){
            resultVO = ResultUtils.buildSuccess(qsTaskVO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/auto/task/bug/create?summary=test&desc=test&reporter=luqiuwei@corp.netease.com&check=luqiuwei@corp.netease.com&handle=luqiuwei@corp.netease.com&version=76343&fixVersion=76343
     * @param summary
     * @param desc
     * @param reporter
     * @param checkUser
     * @param handleUser
     * @param priority
     * @return
     */
    @RequestMapping("/bug/create")
    public ResultVO createAutoTaskScriptBug(@RequestParam(name="task" ,required = false)Long taskId,
                                            @RequestParam(name="script",required = false)Long scriptRunId,
                                            @RequestParam("summary")String summary,
                                            @RequestParam("desc")String desc,
                                            @RequestParam("reporter") String reporter,
                                            @RequestParam("check") String checkUser,
                                            @RequestParam("handle") String handleUser,
                                            @RequestParam(name = "priority" ,required = false,defaultValue = "3") int priority,
                                            @RequestParam("version")String version,
                                            @RequestParam("fixVersion")String fixVersion){
        ResultVO resultVO = null ;
        AutoTestBugDTO autoTestBugDTO = new AutoTestBugDTO();
        autoTestBugDTO.setTaskId(taskId);
        autoTestBugDTO.setScriptRunId(scriptRunId);
        autoTestBugDTO.setSummary(summary);
        autoTestBugDTO.setDesc(desc);
        autoTestBugDTO.setReporter(reporter);
        autoTestBugDTO.setCheckUser(checkUser);
        autoTestBugDTO.setHandleUser(handleUser);
        autoTestBugDTO.setPriority(priority);
        autoTestBugDTO.setVersion(version);
        autoTestBugDTO.setFixVersion(fixVersion);
        try {
            boolean flag = autoTestSupportService.createAutoTestBug(autoTestBugDTO);
            resultVO = ResultUtils.build(flag) ;
        }catch (AutoTestRunException e) {
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

}
