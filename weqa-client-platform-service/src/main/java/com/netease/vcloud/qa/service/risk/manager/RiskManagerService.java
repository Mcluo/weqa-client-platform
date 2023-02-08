package com.netease.vcloud.qa.service.risk.manager;

import com.netease.vcloud.qa.dao.ClientRiskDetailDAO;
import com.netease.vcloud.qa.service.risk.source.RiskDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:53
 */
@Service
public class RiskManagerService {
    @Autowired
    private RiskRuleService riskRuleService ;

    @Autowired
    private RiskDataService riskDataService ;

    @Autowired
    private ClientRiskDetailDAO riskDetailDAO ;


}
