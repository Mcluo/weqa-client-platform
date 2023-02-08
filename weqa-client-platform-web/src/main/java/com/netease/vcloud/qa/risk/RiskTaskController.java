package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.service.risk.process.RiskTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:01
 */
@RestController
@RequestMapping("/risk/task")
public class RiskTaskController {
    @Autowired
    private RiskTaskService riskTaskService ;
}
