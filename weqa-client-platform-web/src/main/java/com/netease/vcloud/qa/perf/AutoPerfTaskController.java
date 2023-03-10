package com.netease.vcloud.qa.perf;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.perf.AutoPerfResultService;
import com.netease.vcloud.qa.service.perf.view.PerfTaskDetailVO;
import com.netease.vcloud.qa.service.perf.view.PerfTaskInfoListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 20:59
 */
@RestController
@RequestMapping("/perf/task")
public class AutoPerfTaskController {

    @Autowired
    private AutoPerfResultService autoPerfResultService ;

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
}
