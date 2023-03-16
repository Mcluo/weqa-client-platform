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
     * http://127.0.0.1:8788/g2-client/risk/rule/add?name=%E6%B5%8B%E8%AF%95%E8%A7%84%E5%88%991&priority=p2&range=task&status=develop&type=auto_test&desc=%E8%87%AA%E5%8A%A8%E5%8C%96%E6%B5%8B%E8%AF%95%E9%80%9A%E8%BF%87%E7%8E%87%E9%9C%80%E8%BE%BE%E5%88%B0%E8%A6%81%E6%B1%82&checkInfo=%7B%7D
     * http://127.0.0.1:8788/g2-client/risk/rule/add?name=%E6%B5%8B%E8%AF%95%E8%A7%84%E5%88%991&priority=p2&range=task&status=develop_test&type=develop_test&desc=%E5%BC%80%E5%8F%91%E8%87%AA%E6%B5%8B%E9%80%9A%E8%BF%87%E7%8E%87&checkInfo=%7B%22passPercent%22%3A95%7D%0A
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
                                        @RequestParam(name = "type") String ruleType,
                                        @RequestParam(name = "desc") String ruleDesc,
                                        @RequestParam(name = "checkInfo") String checkInfo){

        RiskRuleDTO riskRuleDTO = new RiskRuleDTO() ;
        riskRuleDTO.setName(ruleName);
        riskRuleDTO.setPriority(priority);
        riskRuleDTO.setRange(range);
        riskRuleDTO.setStatus(status);
        riskRuleDTO.setRuleType(ruleType);
        riskRuleDTO.setRuleDesc(ruleDesc);
        riskRuleDTO.setRuleCheckInfo(checkInfo);
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


