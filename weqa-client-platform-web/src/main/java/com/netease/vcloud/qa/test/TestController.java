package com.netease.vcloud.qa.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestManagerService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    private AutoTestManagerService autoTestManagerService ;

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
     * http://127.0.0.1:8788/g2-client/test/product?taskName=测试任务&gitBranch=feature_500&ids=1&operator=luqiuwei
     * 测任务生成逻辑
     * @param
     * @return
     */
    @RequestMapping("/tc/create")
    public ResultVO createTcScript(@RequestParam("taskName") String taskName,
                                       @RequestParam(name = "gitInfo" ,required = false) String gitInfo,
                                       @RequestParam("gitBranch") String gitBranch,
                                       @RequestParam("operator") String operator,
                                       @RequestParam("ids") List<Long> idSet){
        ResultVO resultVO = null ;
        boolean flag = false ;
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
        autoTestTaskInfoDTO.setTaskName(taskName);
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setGitInfo(gitInfo);
        autoTestTaskInfoDTO.setGitBranch(gitBranch);
        autoTestTaskInfoDTO.setOperator(operator);
        autoTestTaskInfoDTO.setTestCaseScriptId(idSet);
        try {
            flag = autoTestManagerService.addNewTaskInfoInfo(autoTestTaskInfoDTO);
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.build(false,e.getExceptionInfo()) ;
            return resultVO ;
        }
        if (flag){
            resultVO = ResultUtils.build(true) ;
        }else {
            resultVO = ResultUtils.build(false) ;
        }
        return resultVO ;
    }



}
