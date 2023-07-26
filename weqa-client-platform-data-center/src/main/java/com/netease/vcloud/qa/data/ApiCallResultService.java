package com.netease.vcloud.qa.data;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.data.data.ApiCallData;
import org.springframework.stereotype.Service;


/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 14:29
 */
@Service
public class ApiCallResultService {

    public boolean pitchUploadApiCallResult(ApiCallData apiCallData) {
        System.out.print(JSONObject.toJSONString(apiCallData));
        return true ;
    }

}
