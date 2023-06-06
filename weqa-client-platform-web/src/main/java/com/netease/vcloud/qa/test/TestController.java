package com.netease.vcloud.qa.test;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.service.auto.AutoTestTaskManagerService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.source.RiskDataService;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import com.netease.vcloud.qa.service.tc.TCExecManagerService;
import com.netease.vcloud.qa.service.tc.data.ClientExecData;
import com.netease.vcloud.qa.service.tc.data.ClientExecDataBO;
import com.netease.vcloud.qa.service.tc.data.ClientExecResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/11 22:12
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AutoTestTaskManagerService autoTestTaskManagerService;

    @Autowired
    private TCExecManagerService tcExecManagerService ;

    @Autowired
    private RiskDataService riskDataService ;
    /**
     * http://127.0.0.1:8788/g2-client/test/test
     * @return
     */
    @RequestMapping("/test")
    public JSONObject test(){
        JSONObject jsonObject = new JSONObject() ;
        jsonObject.put("code",200);
        jsonObject.put("message","SUCCESS");
        jsonObject.put("data","{}") ;
        return jsonObject ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/test/task/create?taskName=测试任务&gitBranch=feature_500&ids=1&operator=luqiuwei
     * 测任务生成逻辑
     * @param
     * @return
     */
    @RequestMapping("/task/create")
    public ResultVO createTcScript(@RequestParam("taskName") String taskName,
                                       @RequestParam(name = "gitInfo" ,required = false) String gitInfo,
                                       @RequestParam("gitBranch") String gitBranch,
                                       @RequestParam("operator") String operator,
                                       @RequestParam("ids") List<Long> idSet){
        ResultVO resultVO = null ;
        Long id = null ;
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
        autoTestTaskInfoDTO.setTaskName(taskName);
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setGitInfo(gitInfo);
        autoTestTaskInfoDTO.setGitBranch(gitBranch);
        autoTestTaskInfoDTO.setOperator(operator);
        autoTestTaskInfoDTO.setTestCaseScriptId(idSet);
        try {
            id = autoTestTaskManagerService.addNewTaskInfo(autoTestTaskInfoDTO);
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.build(false,e.getExceptionInfo()) ;
            return resultVO ;
        }
        if (id != null){
            resultVO = ResultUtils.buildSuccess(id) ;
        }else {
            resultVO = ResultUtils.build(false) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/test/test/exec?tv=111867
     * @param tvId
     * @return
     */
    @RequestMapping("/test/exec")
    public ResultVO getTcExeInfo(@RequestParam("tv") Long tvId){
        ResultVO resultVO = null ;
        List<ClientExecResultData> clientExecResultDataList = tcExecManagerService.getClientExecDataList(tvId) ;
        if (clientExecResultDataList!=null) {
            resultVO = ResultUtils.buildSuccess(clientExecResultDataList);
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/test/test/add?tv=111867
     * @param tvId
     * @return
     */
    @RequestMapping("/test/add")
    public ResultVO addOrUpdateTVDetailInfo(@RequestParam("tv") Long tvId){
        ResultVO resultVO = null ;
        boolean flag = tcExecManagerService.addOrUpdateTVDetailInfo(tvId) ;
        if (flag){
            resultVO = ResultUtils.buildSuccess() ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/test/test/get?tv=111867
     * @param tvId
     * @return
     */
    @RequestMapping("/test/get")
    public ResultVO getTVStatisticInfo(@RequestParam("tv") Long tvId){
        ResultVO resultVO = null ;
        ClientExecDataBO clientExecDataBO = tcExecManagerService.getTVDetailInfo(tvId) ;
        if (clientExecDataBO != null){
            resultVO = ResultUtils.buildSuccess(clientExecDataBO) ;
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/test/risk/async?data=auto_test_cover&type=task&id=12
     * @param dataType
     * @param rangeType
     * @param rangeId
     * @return
     */
    @RequestMapping("/risk/async")
    public ResultVO riskDataAsync(@RequestParam("data") String dataType,@RequestParam("type") String rangeType, @RequestParam("id") Long rangeId) {
        RiskCheckRange riskCheckRange = RiskCheckRange.getRiskCheckRageByName(rangeType) ;
        ResultVO resultVO = null ;
        try {
            riskDataService.asyncDate(dataType, riskCheckRange, rangeId);
            resultVO = ResultUtils.buildSuccess() ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * 获取当前数值
     * http://127.0.0.1:8788/g2-client/test/risk/current?data=auto_test_cover&type=task&id=12
     * @param dataType
     * @param rangeType
     * @param rangeId
     * @return
     */
    @RequestMapping("/risk/current")
    public ResultVO riskDataCurrent(@RequestParam("data") String dataType,@RequestParam("type") String rangeType, @RequestParam("id") Long rangeId) {
        RiskCheckRange riskCheckRange = RiskCheckRange.getRiskCheckRageByName(rangeType) ;
        ResultVO resultVO = null ;
        try {
            String result = riskDataService.getCurrentDate(dataType, riskCheckRange, rangeId);
            resultVO = ResultUtils.buildSuccess(result) ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/test/risk/detail?data=auto_test_cover&type=task&id=12
     * @param dataType
     * @param rangeType
     * @param rangeId
     * @return
     */
    @RequestMapping("/risk/detail")
    public ResultVO riskDataDetail(@RequestParam("data") String dataType,@RequestParam("type") String rangeType, @RequestParam("id") Long rangeId) {
        RiskCheckRange riskCheckRange = RiskCheckRange.getRiskCheckRageByName(rangeType) ;
        ResultVO resultVO = null ;
        try {
            CheckDataVOInterface checkDataVO = riskDataService.getCheckData(dataType, riskCheckRange, rangeId);
            resultVO = ResultUtils.buildSuccess(checkDataVO) ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }
}
