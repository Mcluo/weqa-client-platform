package com.netease.vcloud.qa.autotest;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/7 16:14
 */

@RestController
@RequestMapping("/auto/api")
public class AutoTestAPIController {

    @Autowired
    private  AutoTestTaskController autoTestTaskController ;

    /**
     * http://127.0.0.1:8788/g2-client/auto/api/task/create?name=构建自动化测试&version=4.6.60&buildId=1554
     * @param taskName
     * @param version
     * @param buildID
     * @param buildExtendInfo
     * @return
     */
    @RequestMapping("/task/create")
    public ResultVO createAutoTaskAPI(@RequestParam("name")String taskName,
                                      @RequestParam("version") String version,
                                      @RequestParam("buildId") Long buildID,
                                      @RequestParam(name = "extend",required = false) String buildExtendInfo){
        ResultVO resultVO = null ;
//        resultVO resultVO = autoTestTaskController.createAutoTask() ;
        resultVO = ResultUtils.buildSuccess(1630) ;
        return resultVO ;
    }
}
