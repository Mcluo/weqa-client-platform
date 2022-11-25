package com.netease.vcloud.qa.autotest;


import com.alibaba.fastjson.JSONObject;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoCoveredService;
import com.netease.vcloud.qa.service.auto.AutoTestService;
import com.netease.vcloud.qa.service.auto.data.TCCoveredInfoDTO;
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
        boolean flag = autoTestResult.saveAutoTestResult(runInfo,caseName,caseDetail,success,fail,resultJson,testCase);

        return ResultUtils.build(flag);
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
}
