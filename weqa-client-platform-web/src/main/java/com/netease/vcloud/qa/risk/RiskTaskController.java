package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.view.RiskDetailInfoVO;
import com.netease.vcloud.qa.service.risk.manager.view.RiskTaskRiskDetailVO;
import com.netease.vcloud.qa.service.risk.process.RiskTaskService;
import com.netease.vcloud.qa.service.risk.process.dto.RiskTaskDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskBaseVO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:01
 */
@RestController
@RequestMapping("/risk/task")
public class RiskTaskController {


    @Autowired
    private RiskTaskService riskTaskService ;

    /**
     *  http://127.0.0.1:8788/g2-client/risk/task/detail?task=4
     * @param taskId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResultVO getTaskDetailInfo(@RequestParam("task") long taskId){
        ResultVO resultVO = null ;
        try {
            RiskTaskDetailVO riskTaskDetailVO = riskTaskService.getTaskDetailByTaskId(taskId);
            if (riskTaskDetailVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(riskTaskDetailVO) ;
            }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     *  http://127.0.0.1:8788/g2-client/risk/task/risk?task=4
     * @param taskId
     * @return
     */
    @RequestMapping("/risk")
    @ResponseBody
    public  ResultVO getTaskRiskList(@RequestParam("task") long taskId){
        ResultVO resultVO = null ;
        try {
            RiskTaskRiskDetailVO riskTaskRiskDetailVO = riskTaskService.getTaskRiskDetailInfo(taskId);
            if (riskTaskRiskDetailVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else{
                resultVO = ResultUtils.buildSuccess(riskTaskRiskDetailVO) ;
            }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage());
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/risk/task/status/update?task=4&status=test
     * @param taskId
     * @param status
     * @return
     */
    @RequestMapping("/status/update")
    @ResponseBody
    public ResultVO updateTaskStatus(@RequestParam("task") long taskId , @RequestParam("status")String status){
        ResultVO resultVO = null ;
        RiskTaskStatus riskTaskStatus = RiskTaskStatus.getRiskTaskStatusByStatus(status) ;
        try{
            boolean flag = riskTaskService.updateRiskTaskStatus(taskId,riskTaskStatus) ;
            if (flag){
                resultVO = ResultUtils.buildSuccess() ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     *http://127.0.0.1:8788/g2-client/risk/task/risk/check?task=4
     * @param taskId
     * @return
     */
    @RequestMapping("/risk/check")
    @ResponseBody
    public ResultVO checkTaskRiskStatus(@RequestParam("task") long taskId){
        ResultVO resultVO = null ;
        try{
            riskTaskService.startCheckTaskRiskInfoAndData(taskId);
            resultVO = ResultUtils.buildSuccess() ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/task/data/sync?task=4
     * @param taskId
     * @return
     */
    @RequestMapping("/data/sync")
    @ResponseBody
    public ResultVO syncTaskRiskData(@RequestParam("task") long taskId){
        ResultVO resultVO = null ;
        try{
            riskTaskService.startSyncTaskRiskData(taskId);
            resultVO = ResultUtils.buildSuccess() ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }



}
