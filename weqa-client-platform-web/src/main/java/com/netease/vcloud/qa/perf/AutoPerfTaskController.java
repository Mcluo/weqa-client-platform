package com.netease.vcloud.qa.perf;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.view.TestSuitBaseInfoVO;
import com.netease.vcloud.qa.service.perf.AutoPerfResultService;
import com.netease.vcloud.qa.service.perf.AutoPerfRunService;
import com.netease.vcloud.qa.service.perf.data.AutoPerfTaskDTO;
import com.netease.vcloud.qa.service.perf.view.PerfTaskDetailVO;
import com.netease.vcloud.qa.service.perf.view.PerfTaskInfoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 20:59
 */
@RestController
@RequestMapping("/perf/task")
public class AutoPerfTaskController {

    @Autowired
    private AutoPerfResultService autoPerfResultService ;

    @Autowired
    private AutoPerfRunService autoPerfRunService ;

    /**
     * http://127.0.0.1:8788/g2-client/perf/task/list
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultVO getPerfTaskList(@RequestParam(name = "user",required = false ,defaultValue = "") String userInfo ,
                                    @RequestParam(name = "page",required = false,defaultValue = "1") int current ,
                                    @RequestParam(name = "size",required = false,defaultValue = "20") int size){
        ResultVO resultVO = null ;
        PerfTaskInfoListVO perfTaskInfoListVO = autoPerfResultService.getPerfTaskInfoList(userInfo,current, size) ;
        if (perfTaskInfoListVO!=null){
            resultVO = ResultUtils.buildSuccess(perfTaskInfoListVO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/perf/task/detail?task=2496
     * @param taskId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResultVO getPerfTaskDetail(@RequestParam("task") long taskId){
        ResultVO resultVO = null ;
        PerfTaskDetailVO perfTaskDetailVO = autoPerfResultService.getPerfTaskDetailById(taskId) ;
        if (perfTaskDetailVO != null){
            resultVO = ResultUtils.buildSuccess(perfTaskDetailVO) ;
        }else {
            resultVO = ResultUtils.buildFail();
        }
        return resultVO ;
    }

    @RequestMapping("/create")
    @ResponseBody
    public ResultVO createReportTask(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "cpuInfo") String cpuInfo,
            @RequestParam(name = "devicesModel") String devicesModel,
            @RequestParam(name = "devicesVersion") String devicesVersion,
            @RequestParam(name = "devicesPlatform") String devicesPlatform,
            @RequestParam(name = "sdkInfo") String sdkInfo,
            @RequestParam(name = "sdkVersion") String sdkVersion,
            @RequestParam(name = "user") String user,
            @RequestParam(name = "suit") Long suitId,
            @RequestParam(name = "gitInfo" ,required = false) String gitInfo,
            @RequestParam("gitBranch") String gitBranch,
            @RequestParam("device") List<Long> deviceList,
            @RequestParam(name = "operator") String operator){
        AutoPerfTaskDTO autoPerfTaskDTO = new AutoPerfTaskDTO()  ;
        autoPerfTaskDTO.setName(name);
        autoPerfTaskDTO.setCpuInfo(cpuInfo);
        autoPerfTaskDTO.setDevicesModel(devicesModel);
        autoPerfTaskDTO.setDevicesVersion(devicesVersion);
        autoPerfTaskDTO.setDevicesPlatform(devicesPlatform);
        autoPerfTaskDTO.setSdkInfo(sdkInfo);
        autoPerfTaskDTO.setSdkVersion(sdkVersion);
        autoPerfTaskDTO.setUser(user);
        autoPerfTaskDTO.setGitInfo(gitInfo);
        autoPerfTaskDTO.setGitBranch(gitBranch);
        autoPerfTaskDTO.setDeviceList(deviceList);
        autoPerfTaskDTO.setSuitId(suitId);
        ResultVO resultVO = null ;
        try{
            Long code = autoPerfRunService.createNewPerfTest(autoPerfTaskDTO,operator) ;
            if (code != null){
                resultVO = ResultUtils.buildSuccess(code) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return  resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/perf/task/suit/query
     * @return
     */
    @RequestMapping("/suit/query")
    @ResponseBody
    public ResultVO queryPerfTestSuit(){
        ResultVO resultVO = null ;
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = autoPerfRunService.queryPerfTestSuitList() ;
        if (testSuitBaseInfoVOList == null){
            resultVO = ResultUtils.buildFail() ;
        }else {
            resultVO = ResultUtils.buildSuccess(testSuitBaseInfoVOList) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/perf/task/suit/mark?suit=1
     * @param suitId
     * @return
     */
    @RequestMapping("/suit/mark")
    @ResponseBody
    public ResultVO markPerfTestSuit(@RequestParam(name = "suit") Long suitId,@RequestParam(name = "enable",required = false,defaultValue = "true")boolean enable){
        ResultVO resultVO = null ;
        boolean flag = autoPerfRunService.markPerfTestSuitById(suitId) ;
        resultVO = ResultUtils.build(flag) ;
        return resultVO ;
    }
}
