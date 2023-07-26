package com.netease.vcloud.qa.datacenter;

import com.netease.vcloud.qa.data.ApiCallResultService;
import com.netease.vcloud.qa.data.data.ApiCallData;
import com.netease.vcloud.qa.data.data.ApiCallResultVO;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/26 14:21
 */
@Controller
@RequestMapping("/data/api/result")
public class ApiCallResultController {

    @Autowired
    private ApiCallResultService apiCallResultService ;

    /**
     * http://127.0.0.1:8788/g2-client/data/api/result/add
     * @param apiCallData
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO uploadApiCallResult(@RequestBody ApiCallData apiCallData) {
        ResultVO resultVO = null ;
        boolean flag = apiCallResultService.pitchUploadApiCallResult(apiCallData) ;
        if (flag){
            resultVO = ResultUtils.buildSuccess() ;
        }else{
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/data/api/result/query?caseId=122313
     * @param caseId
     * @param userId
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryApiCallResult(@RequestParam("caseId") Long caseId ,
                                       @RequestParam(name="userId",required = false) Long userId) {
        ResultVO resultVO = null ;
        List<ApiCallResultVO> apiCallResultList = apiCallResultService.queryApiCallResult(caseId,userId) ;
        if (apiCallResultList!= null) {
            resultVO = ResultUtils.buildSuccess(apiCallResultList) ;
        }else{
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

}
