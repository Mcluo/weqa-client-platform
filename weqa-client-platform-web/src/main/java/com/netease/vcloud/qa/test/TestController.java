package com.netease.vcloud.qa.test;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestManagerService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * http://127.0.0.1:8788/client/test/test
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


    @RequestMapping("product")
    public ResultVO startProductScript(@RequestBody AutoTestTaskInfoDTO autoTestTaskInfoDTO){
        ResultVO resultVO = null ;
        boolean flag = false ;
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
