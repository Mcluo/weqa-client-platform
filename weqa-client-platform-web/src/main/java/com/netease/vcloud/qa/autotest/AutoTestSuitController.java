package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTcScriptService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.AutoTestTestSuitService;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTCSuitInfoDTO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.auto.view.TestSuitBaseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/10 14:02
 */
@RestController
@RequestMapping("/auto/suit")
public class AutoTestSuitController {

    @Autowired
    private AutoTestTestSuitService autoTestTestSuitService ;


    /**
     *http://127.0.0.1:8788/g2-client/auto/suit/getAll
     * @return
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public ResultVO getAllTestSuit() {
        ResultVO resultVO = null;
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = autoTestTestSuitService.getTestSuitBaseInfo(null);
        resultVO = ResultUtils.buildSuccess(testSuitBaseInfoVOList);
        return resultVO;
    }

    /**
     *http://127.0.0.1:8788/g2-client/auto/suit/query?key="test"
     * @param queryKey
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryTestSuit(@RequestParam("key") String queryKey){
        ResultVO resultVO = null;
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = autoTestTestSuitService.getTestSuitBaseInfo(queryKey);
        resultVO = ResultUtils.buildSuccess(testSuitBaseInfoVOList);
        return resultVO;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/suit/script?suit=1
     * @param suitId
     * @return
     */
    @RequestMapping("/script")
    @ResponseBody
    public ResultVO getTestSuitScript(@RequestParam("suit") Long suitId){
        ResultVO resultVO = null;
        try {
            List<AutoScriptInfoVO> autoScriptInfoVOList =autoTestTestSuitService.getTestSuitScriptInfo(suitId);
            if (autoScriptInfoVOList != null){
                resultVO = ResultUtils.buildSuccess(autoScriptInfoVOList) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/suit/add?name=测试用例集&owner=system
     * @param suitName
     * @param owner
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO addNewTestSuit(@RequestParam("name") String suitName, @RequestParam("owner") String owner){
        ResultVO resultVO = null;
        try {
            Long id = autoTestTestSuitService.addNewTestSuit(suitName, owner);
            if (id != null){
                resultVO = ResultUtils.buildSuccess(id) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/suit/delete?id=1
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO deleteTestSuit(@RequestParam("id") long id) {
        ResultVO resultVO = null;
        boolean flag = autoTestTestSuitService.deleteTestSuitById(id);
        resultVO = ResultUtils.build(flag);
        return resultVO;
    }

    /**
     *http://127.0.0.1:8788/g2-client/auto/suit/script/add?suit=2&script=1
     * @param suitId
     * @param scriptId
     * @return
     */
    @RequestMapping("/script/add")
    @ResponseBody
    public ResultVO addTestAndSuitRelation(@RequestParam("suit")Long suitId ,@RequestParam("script")Long scriptId){
        ResultVO resultVO = null ;
        try {
            boolean flag = autoTestTestSuitService.addTestAndSuitRelation(suitId,scriptId) ;
            if (flag){
                resultVO = ResultUtils.buildSuccess() ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    @RequestMapping("/init")
    @ResponseBody
    public ResultVO initAutoScriptWithSuit(@RequestBody String tcSuitInfo){
//        for (Object tcScriptObj:tcScriptArray){
//            AutoTestTaskInfoDTO autoTestTaskInfoDTO = (AutoTestTaskInfoDTO) JSONObject.toJSON(tcScriptObj) ;
//        }
        AutoTCSuitInfoDTO autoTCSuitInfoDTO= JSONObject.parseObject(tcSuitInfo ,AutoTCSuitInfoDTO.class) ;
//        System.out.println(tcScriptArray);
        ResultVO resultVO = null ;
        try {
            boolean flag = autoTestTestSuitService.initTestSuitScriptInfo(autoTCSuitInfoDTO) ;
            resultVO = ResultUtils.build(flag) ;
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }
}
