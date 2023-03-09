package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.RiskManagerService;
import com.netease.vcloud.qa.service.risk.manager.view.RiskDetailWithDataInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/27 16:08
 */
@RestController
@RequestMapping("/risk/risk")
public class RiskInfoController {

    @Autowired
    private RiskManagerService riskManagerService ;

    /**
     * http://127.0.0.1:8788/g2-client/risk/risk/detail?risk=7
     * @param riskId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResultVO getRiskDetail(@RequestParam("risk") long riskId){
        ResultVO resultVO = null ;
        try {
            RiskDetailWithDataInfoVO riskDetailInfoVO = riskManagerService.getRiskDetailInfoVO(riskId);
            if (riskDetailInfoVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(riskDetailInfoVO) ;
            }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/risk/check?risk=11
     * 重新校验风险项目
     * @param riskId
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public ResultVO checkRiskInfo(@RequestParam("risk") long riskId){
        ResultVO resultVO = null ;
        try {
             boolean flag = riskManagerService.checkRiskInfoAndData(riskId);
             if (flag){
                 resultVO = ResultUtils.buildSuccess() ;
             }else {
                 resultVO = ResultUtils.buildFail() ;
             }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }
}
