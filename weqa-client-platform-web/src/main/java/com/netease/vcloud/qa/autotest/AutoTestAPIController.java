package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.auto.DeviceType;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.api.ApiTaskBuildData;
import com.netease.vcloud.qa.service.api.AutoTaskApiService;
import com.netease.vcloud.qa.service.api.JenkinsBuildDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/7 16:14
 */

@RestController
@RequestMapping("/auto/api")
public class AutoTestAPIController {

    private static final Logger CONTROLLER_LOGGER = LoggerFactory.getLogger("controller");

    private static final String DEFAULT_GIT_INFO = "ssh://git@g.hz.netease.com:22222/ClientTest/fusion/GeneralTester.git" ;

    private static final String DEFAULT_OPERATOR = "" ;

    @Autowired
    private  AutoTestTaskController autoTestTaskController ;

    @Autowired
    private AutoTaskApiService autoTaskApiService ;

    /**
     * 对外提供的接口，用于触发自动化测试用例
     * http://127.0.0.1:8788/g2-client/auto/api/task/create?name=构建自动化测试&version=5.4.0&buildId=3721&url={}
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
                                      @RequestParam(name = "operator",required = false) String operator,
                                      @RequestParam(name = "url",required = false) String url ,
                                      @RequestParam(name = "extend",required = false) String buildExtendInfo){
        ResultVO resultVO = ResultUtils.buildSuccess();
        String gitBranch = autoTaskApiService.getGitBranchByVersion(version) ;
        JSONObject extendInfoObject = JSONObject.parseObject(buildExtendInfo) ;
        List<Long> runCasedIds = autoTaskApiService.getTCIds(extendInfoObject) ;
        JenkinsBuildDTO jenkinsBuildDTO = null ;
        try {
            jenkinsBuildDTO = JSONObject.parseObject(url, JenkinsBuildDTO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<ApiTaskBuildData> apiTaskBuildDataList = autoTaskApiService.getTaskBuildData(buildID,jenkinsBuildDTO) ;
        if(CollectionUtils.isEmpty(apiTaskBuildDataList)){
            resultVO = ResultUtils.buildFail("缺少合适运行给设备") ;
        }
        for(int i = 1 ; i <= apiTaskBuildDataList.size() ; i++) {
            String name = taskName +"【"+i+"】" ;
            ApiTaskBuildData apiTaskBuildData = apiTaskBuildDataList.get(i-1) ;
            if(apiTaskBuildData == null){
                continue ;
            }
            ResultVO simpleResultVO = autoTestTaskController.createAutoTask(name, DEFAULT_GIT_INFO, gitBranch, operator == null ? DEFAULT_OPERATOR : operator, DeviceType.REMOTE_DEVICE_TYPE, apiTaskBuildData.getDeviceList(), runCasedIds, apiTaskBuildData.getUrls(), null, null);
            if (simpleResultVO.getCode()!=200) {
                resultVO.setCode(simpleResultVO.getCode());
                resultVO.setMsg(simpleResultVO.getMsg());
                resultVO.setData(simpleResultVO.getData());
                break;
            }
        }
        return resultVO ;
    }
//    private String getGitBranchByVersion(String version){
//        return null ;
//    }

//    private List<Long>  getEffectDevice() {
//        return null ;
//    }

//    private List<Long> getEffectIds(){
//        return null ;
//    }


//    private String getEffectUrls(){
//        return null ;
//    }
}
