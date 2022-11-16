package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.tc.ClientTcAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/27 11:29
 */
@RestController
@RequestMapping("/testCase")
public class TestCaseController {
    @Autowired
    private ClientTcAsyncService clientTcAsyncService ;

    /**
     * http://127.0.0.1:8080/g2-client/testCase/tc/async
     * @param start
     * @param finish
     * @return
     */
    @RequestMapping("/tc/async")
    @ResponseBody
    public ResultVO syncTestCaseFromTc(@RequestParam(name = "start",required = false) Long start ,
                                       @RequestParam(name = "finish",required = false)Long finish) {
        boolean flag = clientTcAsyncService.asyncProjectTestCase(start, finish) ;
        return ResultUtils.build(flag);
    }
}
