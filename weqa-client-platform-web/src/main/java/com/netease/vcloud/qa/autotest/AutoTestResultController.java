package com.netease.vcloud.qa.autotest;


import com.alibaba.fastjson.JSONObject;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoCoveredService;
import com.netease.vcloud.qa.service.auto.AutoTestResultStatisticService;
import com.netease.vcloud.qa.service.auto.AutoTestService;
import com.netease.vcloud.qa.service.auto.data.TCCoveredInfoDTO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunResultStatisticDetailVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunResultStatisticInfoVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunSummerInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auto")
public class AutoTestResultController {

    private static final Logger CONTROLLER_LOGGER = LoggerFactory.getLogger("controller");

    @Autowired
    private AutoTestService autoTestResult;

    @Autowired
    private AutoCoveredService autoCoveredService;

    @Autowired
    private AutoTestResultStatisticService autoTestResultStatisticService ;

    /**
     *http://127.0.0.1:8080/g2-client/auto/test/result/add?info=test_01_0zz
     * @param runInfo
     * @param caseName
     * @param caseDetail
     * @param success
     * @param fail
     * @param result
     * @return
     */
    @RequestMapping("/test/result/add")
    @ResponseBody
    public ResultVO addResultTest(@RequestParam(name = "info")  String runInfo ,
                                  @RequestParam(name = "case",required = false) String caseName ,
                                  @RequestParam(name = "detail",required = false)String caseDetail,
                                  @RequestParam(name = "success",required = false,defaultValue = "0") int success,
                                  @RequestParam(name = "fail",required = false,defaultValue = "0")int fail,
                                  @RequestParam(name = "result",required = false) String result,
                                  @RequestParam(name = "tc",required = false)Long testCase) {
        JSONObject resultJson = null ;
        if (result!=null) {
            try {
                resultJson = JSONObject.parseObject(result);
            }catch (Exception e){
                CONTROLLER_LOGGER.error("[AutoTestResultController.addResultTest] parse result exception",e);
            }
        }
//        boolean flag = autoTestResult.saveAutoTestResult(runInfo,caseName,caseDetail,success,fail,resultJson,testCase);
        //自动化大盘上线后，将自动不再统计本地自动化上报
        boolean flag = true ;
        return ResultUtils.build(flag);
    }


    /**
     * http://127.0.0.1:8788/g2-client/auto/test/result/weqa/add?script=22&success=true&result={}
     * @return
     */
    @RequestMapping("/test/result/weqa/add")
    @ResponseBody
    public ResultVO addWeqaResult( @RequestParam(name = "script")Long scriptId,
                                   @RequestParam(name = "success",required = false,defaultValue = "true") boolean success,
                                   @RequestParam(name = "result",required = false) String result){
//        JSONObject resultJson = null ;
//        if (result!=null) {
//            try {
//                resultJson = JSONObject.parseObject(result);
//            }catch (Exception e){
//                CONTROLLER_LOGGER.error("[AutoTestResultController.addResultTest] parse result exception",e);
//            }
//        }
        boolean flag = autoTestResult.saveAutoTestResult(scriptId,success,result);
        return ResultUtils.build(flag);
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/test/task/finish?task=22
     * @param taskId
     * @return
     */
    @RequestMapping("/test/task/finish")
    @ResponseBody
    public ResultVO notifyTaskFinish(@RequestParam(name = "task")Long taskId){
         autoTestResult.onTaskFinish(taskId);
         ResultVO resultVO = ResultUtils.build(true);
         return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/covered/add
     * 批量标记tc的覆盖情况
     * @param tcCoveredInfoDTO
     * @return
     */
    @RequestMapping("/covered/add")
    @ResponseBody
    public ResultVO addTcCoveredInfo(@RequestBody TCCoveredInfoDTO tcCoveredInfoDTO){
        ResultVO resultVO = null ;
        boolean flag = autoCoveredService.patchUpdateCoveredStatus(tcCoveredInfoDTO.getTcResult()) ;
        resultVO = ResultUtils.build(flag) ;
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/statistic/summer
     * @return
     */
    @RequestMapping("/statistic/summer")
    @ResponseBody
    public ResultVO queryGroupRunSummer(){
        ResultVO resultVO = new ResultVO() ;
        RunSummerInfoVO runSummerInfoVO = autoTestResultStatisticService.getGroupRunSummer() ;
        if (runSummerInfoVO != null){
            resultVO = ResultUtils.buildSuccess(runSummerInfoVO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/statistic/list?start=1669824000000&finish=1671180817000
     * @return
     */
    @RequestMapping("/statistic/list")
    @ResponseBody
    public ResultVO queryRunResultStatistic(@RequestParam("start") Long startTime ,
                                            @RequestParam("finish") Long finishTime){
        ResultVO resultVO = null ;
        RunResultStatisticInfoVO runResultStatisticInfoVO = autoTestResultStatisticService.queryRunResultStatistic(startTime, finishTime) ;
        if (runResultStatisticInfoVO!=null){
            resultVO = ResultUtils.buildSuccess(runResultStatisticInfoVO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/statistic/detail?start=1669824000000&finish=1671180817000&runInfo=
     * @return
     */
    @RequestMapping("/statistic/detail")
    @ResponseBody
    public  ResultVO queryRunResultDetail(@RequestParam("start") Long startTime ,
                                          @RequestParam("finish") Long finishTime ,
                                          @RequestParam("runInfo") String runInfo){
        ResultVO resultVO = null ;
        RunResultStatisticDetailVO runResultStatisticDetailVO = autoTestResultStatisticService.queryRunResultDetail(startTime, finishTime,runInfo) ;
        if (runResultStatisticDetailVO!=null){
            resultVO = ResultUtils.buildSuccess(runResultStatisticDetailVO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

}
