package com.netease.vcloud.qa.config;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.version.VersionCheckService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/14 20:37
 */
@RestController
@RequestMapping("/config/download")
public class ConfigDownloadController {
    @Autowired
    private VersionCheckService versionCheckService ;

    /**
     * http://127.0.0.1:8788/g2-client/config/download/version/check
     * @return
     */
    @RequestMapping("/version/check")
    public ResultVO versionCheckNotify() {
        ResultVO resultVO = null ;
        versionCheckService.versionCheckSchedule();
        resultVO = ResultUtils.buildSuccess() ;
        return  resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/config/download/writelist/add?type=qos&version=4.6.100,4.6.101
     * @return
     */
    @RequestMapping("/writelist/add")
    @ResponseBody
    public ResultVO addWriteListVersion(@RequestParam("type") String type, @Param("version") String version) {
        ResultVO resultVO = null ;
        String[] versionArray = version.split(",") ;
        boolean flag = versionCheckService.addWriteList(type,Arrays.asList(versionArray));
        if (flag) {
            resultVO = ResultUtils.buildSuccess();
        }else {
            resultVO = ResultUtils.buildFail();
        }
        return  resultVO ;
    }

}
