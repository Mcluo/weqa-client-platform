package com.netease.vcloud.qa.service.risk.manager;

import com.netease.vcloud.qa.dao.ClientRiskRuleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:54
 */
@Service
public class RiskRuleService {

    @Autowired
    private ClientRiskRuleDAO clientRiskRuleDAO ;

}
