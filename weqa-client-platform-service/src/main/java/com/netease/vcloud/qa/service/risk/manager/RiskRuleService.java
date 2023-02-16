package com.netease.vcloud.qa.service.risk.manager;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.ClientRiskRuleDAO;
import com.netease.vcloud.qa.model.ClientRiskRuleDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.risk.RiskCheckStatus;
import com.netease.vcloud.qa.risk.RiskProjectStatus;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.data.RiskCheckStander;
import com.netease.vcloud.qa.service.risk.manager.data.RiskRuleInfoBO;
import com.netease.vcloud.qa.service.risk.manager.dto.RiskRuleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:54
 */
@Service
public class RiskRuleService {

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

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

    public Map<Long,RiskRuleInfoBO> getRuleByIdSet(Set<Long> ruleIdSet){
        if (CollectionUtils.isEmpty(ruleIdSet)){
            return null ;
        }
        List<ClientRiskRuleDO> clientRiskRuleDOList = clientRiskRuleDAO.getClientRiskRuleByIdSet(ruleIdSet) ;
        Map<Long,RiskRuleInfoBO> ruleInfoBOMap = new HashMap<Long,RiskRuleInfoBO>() ;
        if (clientRiskRuleDOList != null){
            for (ClientRiskRuleDO clientRiskRuleDO : clientRiskRuleDOList){
                RiskRuleInfoBO riskRuleInfoBO = this.buildRiskRuleInfoBOByDO(clientRiskRuleDO) ;
                if (riskRuleInfoBO!=null) {
                    ruleInfoBOMap.put(clientRiskRuleDO.getId(), riskRuleInfoBO);
                }
            }
        }
        return ruleInfoBOMap ;
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

    public Long insertNewRule(RiskRuleDTO riskRuleDTO) throws RiskCheckException {
        if (riskRuleDTO == null){
            RISK_LOGGER.error("[clientRiskRuleDAO.insertNewRule]");
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        ClientRiskRuleDO clientRiskRuleDO = this.buildClientRiskRuleDOByDTO(riskRuleDTO) ;
        if (clientRiskRuleDO==null){
            return null ;
        }
        int count = clientRiskRuleDAO.insertRiskRule(clientRiskRuleDO) ;
        if (count>0) {
            return clientRiskRuleDO.getId();
        }else {
            return null ;
        }
    }

    private  ClientRiskRuleDO buildClientRiskRuleDOByDTO(RiskRuleDTO riskRuleDTO){
        RiskCheckRange riskCheckRange = RiskCheckRange.getRiskCheckRageByName(riskRuleDTO.getName()) ;
        if (riskCheckRange == null){
            return null ;
        }
        RiskCheckStatus riskCheckStatus = riskCheckRange.equals(RiskCheckRange.PROJECT)? RiskProjectStatus.getStatusByName(riskRuleDTO.getStatus()) : RiskTaskStatus.getRiskTaskStatusByStatus(riskRuleDTO.getStatus());
        if (riskCheckStatus == null){
            return null ;
        }
        ClientRiskRuleDO clientRiskRuleDO = new ClientRiskRuleDO() ;
        clientRiskRuleDO.setRuleName(riskRuleDTO.getName());
        clientRiskRuleDO.setCheckPriority(riskRuleDTO.getPriority());
        clientRiskRuleDO.setCheckRange(riskCheckRange.getCode());
        clientRiskRuleDO.setCheckStage(riskCheckStatus.getCode());
        clientRiskRuleDO.setCheckInfo(riskRuleDTO.getCheckInfo());
        return clientRiskRuleDO ;
    }

}
