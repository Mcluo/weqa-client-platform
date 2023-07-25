package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.PropertiesConfig;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.service.auto.data.AutoTestBugDTO;
import com.netease.vcloud.qa.version.JiraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 自动化测试相关配套支持服务
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/25 14:59
 */
@Service
public class AutoTestSupportService {
    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String ISSUE_TYPE = "Bug" ;

    @Autowired
    private JiraService jiraService;

    @Autowired
    private PropertiesConfig propertiesConfig ;

    @Autowired
    private ClientAutoScriptRunInfoDAO clientAutoScriptRunInfoDAO ;


    public boolean createAutoTestBug(AutoTestBugDTO autoTestBugDTO) throws AutoTestRunException{
        if (!this.autoTestBugParamCheck(autoTestBugDTO)) {
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        String jiraKey = this.getJiraKey();
        String jiraIssueKey = jiraService.createJiraIssue(jiraKey,autoTestBugDTO.getSummary(),ISSUE_TYPE,autoTestBugDTO.getDesc(),autoTestBugDTO.getReporter(),autoTestBugDTO.getCheckUser(),autoTestBugDTO.getHandleUser(),autoTestBugDTO.getPriority()+"",autoTestBugDTO.getVersion(),autoTestBugDTO.getFixVersion()) ;
        if (jiraIssueKey == null){
            return false ;
        }else {
            this.addJiraKeyToScript(autoTestBugDTO.getScriptRunId(),jiraIssueKey) ;
            return true ;
        }
    }

    private boolean addJiraKeyToScript(Long scriptRunId,String jiraIssueKey){
        if (scriptRunId == null || jiraIssueKey == null){
            return true ;
        }
        int count = clientAutoScriptRunInfoDAO.updateScriptBugInfo(scriptRunId,jiraIssueKey) ;
        if (count>0){
            return true ;
        }else{
            return false ;
        }
    }

    private boolean autoTestBugParamCheck(AutoTestBugDTO autoTestBugDTO){
        if (autoTestBugDTO == null){
            TC_LOGGER.error("[AutoCoveredService.createAutoTestBug] create auto test bug exception. params is null");
            return false ;
        }
        if (autoTestBugDTO.getPriority()>4|| autoTestBugDTO.getPriority()<0){
            TC_LOGGER.error("[AutoCoveredService.createAutoTestBug] priority is not support. priority is "+autoTestBugDTO.getPriority());
            return false ;
        }
        return true ;
    }

    private String getJiraKey(){
        String jiraKeyStr = propertiesConfig.getProperty(PropertiesConfig.JIRA_KEY) ;
        return jiraKeyStr ;
    }
}
