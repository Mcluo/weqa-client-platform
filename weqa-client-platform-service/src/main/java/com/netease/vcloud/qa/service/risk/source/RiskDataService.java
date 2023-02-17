package com.netease.vcloud.qa.service.risk.source;

import com.netease.vcloud.qa.service.risk.source.manager.AutoTestCheckManageService;
import com.netease.vcloud.qa.service.risk.source.manager.RiskTestCheckManageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 12:01
 */
@Service
public class RiskDataService {
    /**
     * 自动化测试
     */
    private static final String AUTO_TEST = "auto_test" ;
    /**
     * 开发自测
     */
    private static final String DEVELOP_TEST = "develop_test" ;
    /**
     * 冒烟测试
     */
    private static final String SMOKE_TEST = "smoke_test" ;
    /**
     * 缺陷状态
     */
    private static final String BUG_STATUS = "bug_status" ;

    private Map<String , RiskTestCheckManageInterface> riskTestCheckManageMap ;

    @Autowired
    private AutoTestCheckManageService autoTestCheckManageService ;

    public void init(){
        riskTestCheckManageMap = new HashMap<String, RiskTestCheckManageInterface>() ;
        riskTestCheckManageMap.put(AUTO_TEST,autoTestCheckManageService) ;
    }
}
