package com.netease.vcloud.qa.service.risk.process;

import com.netease.vcloud.qa.dao.ClientRiskTaskDAO;
import com.netease.vcloud.qa.dao.ClientRiskTaskPersonDAO;
import com.netease.vcloud.qa.service.risk.manager.RiskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:52
 */
@Service
public class RiskTaskService {

    @Autowired
    private RiskManagerService riskManagerService ;

    @Autowired
    private RiskProjectService riskProjectService ;

    @Autowired
    private ClientRiskTaskDAO riskTaskDAO ;

    @Autowired
    private ClientRiskTaskPersonDAO riskTaskPersonDAO ;

}
