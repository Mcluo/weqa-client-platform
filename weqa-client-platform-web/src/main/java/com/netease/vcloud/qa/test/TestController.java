package com.netease.vcloud.qa.test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/11 22:12
 */
@RestController
@RequestMapping("/test")
public class TestController {

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

}
