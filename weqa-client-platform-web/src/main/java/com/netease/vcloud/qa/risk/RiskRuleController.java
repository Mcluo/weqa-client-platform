package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.RiskRuleService;
import com.netease.vcloud.qa.service.risk.manager.dto.RiskRuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/16 22:00
 */
@RestController
@RequestMapping("/risk/rule")
public class RiskRuleController {

    @Autowired
    private RiskRuleService riskRuleService ;

    /**
     * http://127.0.0.1:8788/g2-client/risk/rule/add?name=规则1&priority=p2&range=task&status=develop&checkInfo={}
     * @param ruleName
     * @param priority
     * @param range
     * @param status
     * @param checkInfo
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO addOneTaskToProject(@RequestParam(name = "name") String ruleName ,
                                        @RequestParam(name = "priority") String priority ,
                                        @RequestParam(name = "range") String range,
                                        @RequestParam(name = "status") String status,
                                        @RequestParam(name = "checkInfo") String checkInfo){

        RiskRuleDTO riskRuleDTO = new RiskRuleDTO() ;
        riskRuleDTO.setName(ruleName);
        riskRuleDTO.setPriority(priority);
        riskRuleDTO.setRange(range);
        riskRuleDTO.setStatus(status);
        riskRuleDTO.setCheckInfo(checkInfo);
        ResultVO resultVO = null ;
        try {
            Long id = riskRuleService.insertNewRule(riskRuleDTO);
            if (id==null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(id) ;
            }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

}


