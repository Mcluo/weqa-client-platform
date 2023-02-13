package com.netease.vcloud.qa.service.risk.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.ClientRiskRuleDAO;
import com.netease.vcloud.qa.model.ClientRiskRuleDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.risk.RiskCheckStatus;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.manager.data.RiskCheckStander;
import com.netease.vcloud.qa.service.risk.manager.data.RiskDetailInfoBO;
import com.netease.vcloud.qa.service.risk.manager.data.RiskRuleInfoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:54
 */
@Service
public class RiskRuleService {

    @Autowired
    private ClientRiskRuleDAO clientRiskRuleDAO ;

    public List<RiskRuleInfoBO> getRuleByTypeAndStage(RiskCheckRange riskCheckRange , RiskCheckStatus riskCheckStatus){
        if (riskCheckRange == null || riskCheckStatus == null){
            return null ;
        }
        List<ClientRiskRuleDO> clientRiskRuleDOList = clientRiskRuleDAO.getClientRiskRule(riskCheckRange.getCode(), riskCheckStatus.getCode()) ;
        List<RiskRuleInfoBO> riskRuleInfoBOList = new ArrayList<RiskRuleInfoBO>() ;
        if (clientRiskRuleDOList!=null){
            for (ClientRiskRuleDO clientRiskRuleDO : clientRiskRuleDOList){
                RiskRuleInfoBO riskRuleInfoBO = this.buildRiskRuleInfoBOByDO(clientRiskRuleDO);
                if (riskRuleInfoBO!=null){
                    riskRuleInfoBOList.add(riskRuleInfoBO) ;
                }
            }
        }
        return riskRuleInfoBOList ;
    }

    private RiskRuleInfoBO buildRiskRuleInfoBOByDO(ClientRiskRuleDO clientRiskRuleDO){
        if (clientRiskRuleDO == null){
            return null ;
        }
        RiskRuleInfoBO riskRuleInfoBO = new RiskRuleInfoBO() ;
        riskRuleInfoBO.setId(clientRiskRuleDO.getId());
        riskRuleInfoBO.setRuleName(clientRiskRuleDO.getRuleName());
        riskRuleInfoBO.setPriority(clientRiskRuleDO.getCheckPriority());
        RiskCheckRange riskCheckRange = RiskCheckRange.getRiskCheckRangeByCode(clientRiskRuleDO.getCheckRange()) ;
        riskRuleInfoBO.setRange(riskCheckRange);
        riskRuleInfoBO.setStage(clientRiskRuleDO.getCheckStage());
        RiskCheckStander riskCheckStander = JSONObject.parseObject(clientRiskRuleDO.getCheckInfo(),RiskCheckStander.class) ;
        riskRuleInfoBO.setCheckStander(riskCheckStander);
        return riskRuleInfoBO ;
    }

}
