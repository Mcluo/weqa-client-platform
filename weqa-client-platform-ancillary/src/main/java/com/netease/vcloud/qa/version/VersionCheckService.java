package com.netease.vcloud.qa.version;

import com.netease.vcloud.qa.PropertiesConfig;
import com.netease.vcloud.qa.version.data.JiraVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/5 14:03
 */
@Service
public class VersionCheckService{

    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("CommonLog");

    private static final String JIRA_PROJECT_VERSION_PREFIX = "NRTC G2 V" ;

    @Autowired
    private JiraService jiraService;

    @Autowired
    private PropertiesConfig propertiesConfig ;

    @Scheduled(cron = "0 0 0 * *? *")
    public void VersionCheckSchedule() {
        String jiraKey = this.getJiraKey();
        if (StringUtils.isBlank(jiraKey)) {
            return;
        }
        //获取相关版本信息
        List<JiraVersion> jiraVersionList = jiraService.getProjectVersions(jiraKey);
        List<String> jiraFilterList = new ArrayList<String>();
        if (jiraVersionList==null){
            return;
        }
        for (JiraVersion jiraVersion : jiraVersionList) {
            String jiraVersionName = this.filterVersion(jiraVersion) ;
            if (jiraVersionName != null) {
                jiraFilterList.add(jiraVersionName);
            }
        }
        for (String jiraFilter : jiraFilterList) {
            System.out.println(jiraFilter);
        }
        //有效版本落库
    }

    private String filterVersion(JiraVersion jiraVersion){
        if (jiraVersion == null || jiraVersion.getName()== null ) {
            return null ;
        }
        String jiraVersionName = jiraVersion.getName() ;
        if(!jiraVersionName.startsWith(JIRA_PROJECT_VERSION_PREFIX)){
            // 前缀过滤
            return null ;
        }
        String versionName  = jiraVersionName.substring(JIRA_PROJECT_VERSION_PREFIX.length()) ;
        String[] versionArray = versionName.split("\\.") ;
        if (StringUtils.isNumeric(versionArray[versionArray.length-1])) {
            //最后一个是数组是数字
            return versionName ;
        }else {
            return null;
        }
    }


    private String getJiraKey(){
        String jiraKeyStr = propertiesConfig.getProperty(PropertiesConfig.JIRA_KEY) ;
        return jiraKeyStr ;
    }


}
