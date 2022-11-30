package com.netease.vcloud.qa.autotest;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestTaskManagerService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.view.TaskDetailInfoVO;
import com.netease.vcloud.qa.service.auto.view.TaskInfoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                  @RequestParam("device") String deviceList,
                                  @RequestParam("ids") List<Long> idSet){
        ResultVO resultVO = null ;
        Long id = null ;
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
        autoTestTaskInfoDTO.setTaskName(taskName);
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setGitInfo(gitInfo);
        autoTestTaskInfoDTO.setGitBranch(gitBranch);
        autoTestTaskInfoDTO.setOperator(operator);
        autoTestTaskInfoDTO.setDeviceList(deviceList);
        autoTestTaskInfoDTO.setTestCaseScriptId(idSet);
        try {
            id = autoTestTaskManagerService.addNewTaskInfo(autoTestTaskInfoDTO);
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
    public ResultVO queryAutoTaskList(@RequestParam(name = "size" , required = false , defaultValue = "20")int size ,
                                      @RequestParam(name = "page" , required = false , defaultValue = "1") int pageNo){
        ResultVO resultVO = null ;
        try {
            TaskInfoListVO taskInfoListVO = autoTestTaskManagerService.queryTaskInfoList(size, pageNo);
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
                resultVO = ResultUtils.buildSuccess(taskDetailInfoVO) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.build(false,e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

}
