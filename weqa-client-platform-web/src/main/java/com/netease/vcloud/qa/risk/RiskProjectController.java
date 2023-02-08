package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.service.risk.process.RiskProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:00
 */
@RestController
@RequestMapping("/risk/project")
public class RiskProjectController {
    @Autowired
    private RiskProjectService riskProjectService ;
}
