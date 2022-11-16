package com.netease.vcloud.qa.autotest;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTcScriptService;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/auto/exec")
public class AutoTestExecuteController {

    @Autowired
    private AutoTcScriptService autoTcScriptService ;

    /**
     * 
     * @param scriptName
     * @param scriptDetail
     * @param execClass
     * @param execMethod
     * @param execParam
     * @param tcId
     * @param owner
     * @return
     */
    @RequestMapping("/script/add")
    public ResultVO addOneNewTCScript( @RequestParam("name") String scriptName,
                                       @RequestParam("detail") String scriptDetail,
                                       @RequestParam("class") String execClass ,
                                       @RequestParam("method") String execMethod ,
                                       @RequestParam(name="param",required = false) String execParam ,
                                       @RequestParam("tcId") Long tcId ,
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
        boolean flag = autoTcScriptService.setScriptInfo(autoTCScriptInfoDTOList) ;
        ResultVO resultVO = ResultUtils.build(flag) ;
        return resultVO ;
    }
}
