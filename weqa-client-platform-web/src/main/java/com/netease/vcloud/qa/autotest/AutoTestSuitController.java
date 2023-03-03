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
import org.apache.commons.lang3.StringUtils;
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
    public ResultVO getAllTestSuit(@RequestParam(name = "owner",required = false) String owner) {
        ResultVO resultVO = null;
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = autoTestTestSuitService.getTestSuitBaseInfo(owner,null);
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
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = autoTestTestSuitService.getTestSuitBaseInfo(null,queryKey);
        resultVO = ResultUtils.buildSuccess(testSuitBaseInfoVOList);
        return resultVO;
    }

    /**
     * 查询suit下面的全量脚本
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
     * @param scriptIds
     * @return
     */
    @RequestMapping("/script/add")
    @ResponseBody
    public ResultVO addTestAndSuitRelation(@RequestParam("suit")Long suitId ,@RequestParam("scriptList")List<Long> scriptIds){
        ResultVO resultVO = null ;
        try {
            boolean flag = autoTestTestSuitService.addTestAndSuitRelation(suitId,scriptIds) ;
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

    /**
     * http://127.0.0.1:8788/g2-client/auto/suit/script/delete?suit=2&script=1
     * 解除脚本-执行集绑定关系
     * @param suitId
     * @param scriptId
     * @return
     */
    @RequestMapping("/script/delete")
    @ResponseBody
    public ResultVO deleteTestAndSuitRelation(@RequestParam("suit")Long suitId ,@RequestParam("script")Long scriptId){
        ResultVO resultVO = null ;
        try {
            boolean flag = autoTestTestSuitService.deleteTestAndSuitRelation(suitId,scriptId) ;
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
    @Deprecated
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

    @RequestMapping("/script/patch/add")
    @ResponseBody
    public ResultVO patchAddAutoScriptToSuit(@RequestParam("suit") String suitName, @RequestParam(name = "tc" ,required = false,defaultValue = "") String tcArray, @RequestParam("operator") String operator){
        if(StringUtils.isBlank(tcArray)){
            return this.addNewTestSuit(suitName,operator) ;
        }
        ResultVO resultVO = null ;
        List<AutoTCScriptInfoDTO> autoTCScriptInfoDTOList = null ;
        try {
            autoTCScriptInfoDTOList = JSONArray.parseArray(tcArray, AutoTCScriptInfoDTO.class);
        }catch (Throwable t){
            t.printStackTrace();
        }
        if (autoTCScriptInfoDTOList == null){
            resultVO = ResultUtils.buildFail("参数解析错误") ;
            return resultVO ;
        }
        try {
            boolean flag = autoTestTestSuitService.patchAddTestScriptToSuit(suitName, autoTCScriptInfoDTOList, operator);
            resultVO = ResultUtils.build(flag) ;
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }
}
