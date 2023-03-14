package com.netease.vcloud.qa.test;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestTaskManagerService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.tc.TCExecManagerService;
import com.netease.vcloud.qa.service.tc.data.ClientExecData;
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

}
