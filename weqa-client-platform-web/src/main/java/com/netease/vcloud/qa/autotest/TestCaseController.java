package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONObject;
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

    @RequestMapping("/tc/async")
    @ResponseBody
    public JSONObject syncTestCaseFromTc(@RequestParam(name = "start",required = false) Long start ,
                                         @RequestParam(name = "finish",required = false)Long finish) {
        JSONObject json = new JSONObject() ;
        boolean flag = clientTcAsyncService.AsyncProjectTestCase(start, finish) ;
        if (flag){
            json.put("code",200) ;
        }else {
            json.put("code",500) ;
        }
        return json ;
    }
}
