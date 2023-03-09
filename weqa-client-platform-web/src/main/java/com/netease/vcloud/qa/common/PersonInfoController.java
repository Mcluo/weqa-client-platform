package com.netease.vcloud.qa.common;

import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/23 10:08
 */
@RestController
@RequestMapping("/person/info")
public class PersonInfoController {

    @Autowired
    private UserInfoService userInfoService ;

    /**
     *  http://127.0.0.1:8788/g2-client/person/info/query?key=lu
     * @param queryKey
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryUserInfoList(@RequestParam(name = "key" , required = false) String queryKey){
        ResultVO resultVO = null ;
        List<UserInfoVO> userInfoVOList = userInfoService.queryUserInfoByKey(queryKey) ;
        if (userInfoVOList == null){
            resultVO = ResultUtils.buildFail() ;
        }else {
            resultVO = ResultUtils.buildSuccess(userInfoVOList) ;
        }
        return resultVO ;
    }
}
