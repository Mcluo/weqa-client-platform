package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONArray;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTcScriptService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/16 21:59
 */
@RestController
@RequestMapping("/auto/script")
public class AutoTestScriptController {

    @Autowired
    private AutoTcScriptService autoTcScriptService ;

    /**
     *http://127.0.0.1:8080/g2-client/auto/script/add
     * @param scriptName
     * @param scriptDetail
     * @param execClass
     * @param execMethod
     * @param execParam
     * @param tcId
     * @param owner
     * @return
     */
    @RequestMapping("/add")
    public ResultVO addOneNewTCScript( @RequestParam("name") String scriptName,
                                       @RequestParam("detail") String scriptDetail,
                                       @RequestParam("class") String execClass ,
                                       @RequestParam("method") String execMethod ,
                                       @RequestParam(name="param",required = false) String execParam ,
                                       @RequestParam(name="tcId",required = false) Long tcId ,
                                       @RequestParam("owner") String owner){
        AutoTCScriptInfoDTO autoTCScriptInfoDTO = new AutoTCScriptInfoDTO() ;
        autoTCScriptInfoDTO.setScriptName(scriptName);
        autoTCScriptInfoDTO.setScriptDetail(scriptDetail);
        autoTCScriptInfoDTO.setExecClass(execClass);
        autoTCScriptInfoDTO.setExecMethod(execMethod);
        autoTCScriptInfoDTO.setExecParam(execParam);
        autoTCScriptInfoDTO.setOwner(owner);
        autoTCScriptInfoDTO.setTcId(tcId);
        List<AutoTCScriptInfoDTO> autoTCScriptInfoDTOList = new ArrayList<AutoTCScriptInfoDTO>() ;
        autoTCScriptInfoDTOList.add(autoTCScriptInfoDTO) ;
        boolean flag = autoTcScriptService.addScriptInfo(autoTCScriptInfoDTOList) ;
        ResultVO resultVO = ResultUtils.build(flag) ;
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/auto/script/query
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/query")
    public ResultVO queryTCScript(@RequestParam(name = "page",required = false ,defaultValue = "1") Integer pageNo,
                                  @RequestParam(name = "size",required = false , defaultValue = "20") Integer pageSize){
        ResultVO resultVO = null ;
        try {
            AutoScriptListVO autoScriptListVO = autoTcScriptService.getAllScript(pageNo, pageSize);
            if (autoScriptListVO != null){
                resultVO = ResultUtils.buildSuccess(autoScriptListVO) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException autoTestRunException){
            resultVO = ResultUtils.buildFail(autoTestRunException.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/script/init
     * @param tcScriptArray
     * @return
     */
    @RequestMapping("/init")
    public ResultVO initAutoScript(@RequestBody String tcScriptArray){
//        for (Object tcScriptObj:tcScriptArray){
//            AutoTestTaskInfoDTO autoTestTaskInfoDTO = (AutoTestTaskInfoDTO) JSONObject.toJSON(tcScriptObj) ;
//        }
        List<AutoTCScriptInfoDTO> autoTCScriptInfoDTOList= JSONArray.parseArray(tcScriptArray ,AutoTCScriptInfoDTO.class) ;
//        System.out.println(tcScriptArray);
        boolean flag = autoTcScriptService.addScriptInfo(autoTCScriptInfoDTOList) ;

        return ResultUtils.build(flag) ;
    }
}
