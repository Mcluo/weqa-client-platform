package com.netease.vcloud.qa.version;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.PropertiesConfig;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.version.data.JiraVersion;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/4 15:48
 */
@Service
public class JiraService {

    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("CommonLog");

    @Autowired
    private PropertiesConfig propertiesConfig;

    private  String jiraUsername ;

    private  String jiraPassword ;

    @PostConstruct
    private void init() {
        this.jiraUsername = propertiesConfig.getProperty(PropertiesConfig.JIRA_USERNAME);
        this.jiraPassword = propertiesConfig.getProperty(PropertiesConfig.JIRA_PASSWORD);
    }

    public List<JiraVersion> getProjectVersions(String jiraKey) {
        List<JiraVersion> list = new ArrayList<>();
        try {
            String url = "http://jira.netease.com/rest/api/latest/project/" + jiraKey + "/versions";
            Map<String,String> headMap = new HashMap<String,String>() ;
            headMap.put("Content-Type","application/json; charset=UTF-8") ;
            headMap.put("Authorization",this.getAuth()) ;
            JSONArray jsonArray = HttpUtils.getInstance().getArray(url,headMap) ;
            if (jsonArray == null){
                return null ;
            }
            System.out.println(jsonArray.toJSONString());
        } catch (Exception e) {
            COMMON_LOGGER.error("Remote JIRA service getProjectVersions failed", e);
        }
        return list;
    }


    private String getAuth() {
        return "Basic " + encodeCredentials(this.jiraUsername, this.jiraPassword);
    }

    private String encodeCredentials(String username, String password) {
        byte[] credentials = (username + ':' + password).getBytes();
        return new String(Base64.encodeBase64(credentials));
    }
}
