package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.source.manager.AutoTestCheckManageService;
import com.netease.vcloud.qa.service.risk.source.manager.DevSmokeExecCheckManagerService;
import com.netease.vcloud.qa.service.risk.source.manager.DevSmokeRateCheckManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/27 19:48
 */
@RestController
@RequestMapping("/risk/detail")
public class RiskDetailController {
    @Autowired
    private AutoTestCheckManageService autoTestCheckManageService ;

    @Autowired
    private DevSmokeExecCheckManagerService devSmokeExecCheckManagerService ;

    @Autowired
    private DevSmokeRateCheckManagerService devSmokeRateCheckManagerService ;

    /**
     * http://127.0.0.1:8788/g2-client/risk/detail/auto/bind?task=10&auto=1
     * 自动化和任务绑定操作
     */
    @RequestMapping("/auto/bind")
    public ResultVO autoTaskTaskBind(@RequestParam("task")long taskId , @RequestParam("auto") long autoId){
        ResultVO resultVO = null ;
        boolean result = autoTestCheckManageService.bindRiskTaskAndAutoTask(taskId, autoId) ;
        if (result){
            resultVO = ResultUtils.buildSuccess();
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    @RequestMapping("/dev/exec/bind")
    public ResultVO devExecTCIdBind(@RequestParam("task")long taskId , @RequestParam("tvId") long tvId){
        ResultVO resultVO = null ;
        boolean result = devSmokeExecCheckManagerService.bindRiskTaskAndTV(taskId, tvId) ;
        if (result){
            resultVO = ResultUtils.buildSuccess() ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    @RequestMapping("/dev/rate/bind")
    public ResultVO devRateTcIdBind(@RequestParam("task")long taskId , @RequestParam("devTvId") long devTvId,@RequestParam("qaTvId") long qaTvId){
        ResultVO resultVO = null ;
        boolean result = devSmokeRateCheckManagerService.bindRiskTaskAndTVs(taskId, devTvId,qaTvId) ;
        if (result){
            resultVO = ResultUtils.buildSuccess() ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

}
