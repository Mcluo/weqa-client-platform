package com.netease.vcloud.qa.version;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.PropertiesConfig;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientG2JiraVersionInfoDAO;
import com.netease.vcloud.qa.model.ClientG2JiraVersionInfoDO;
import com.netease.vcloud.qa.version.data.JiraVersion;
import com.netease.vcloud.qa.version.data.JiraVersionVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.net.URI;
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

    private static final String JIRA_BROWSE_URL= "https://jira.netease.com/browse/" ;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private ClientG2JiraVersionInfoDAO g2JiraVersionInfoDAO ;

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
//            System.out.println(jsonArray.toJSONString());
            for (Object object : jsonArray){
                if (object == null || object instanceof JSONObject == false) {
                    continue;
                }
                JSONObject jsonObject = (JSONObject) object ;
                JiraVersion jiraVersion = new JiraVersion() ;
                String selfStr = jsonObject.getString("self") ;
                if (StringUtils.isNotBlank(selfStr)) {
                    jiraVersion.setSelf(new URI(selfStr));
                }
                String id = jsonObject.getString("id") ;
                if (id != null) {
                    try {
                        jiraVersion.setId(Long.parseLong(id));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }
                jiraVersion.setName(jsonObject.getString("name"));
                jiraVersion.setArchived(jsonObject.getBoolean("archived"));
                jiraVersion.setReleased(jsonObject.getBoolean("released"));
                jiraVersion.setStartDate(jsonObject.getString("startDate"));
                jiraVersion.setReleaseDate(jsonObject.getString("releaseDate"));
                jiraVersion.setUserStartDate(jsonObject.getString("userStartDate"));
                jiraVersion.setUserReleaseDate(jsonObject.getString("userReleaseDate"));
                jiraVersion.setProjectId(jsonObject.getLong("projectId"));
                list.add(jiraVersion);
            }
        } catch (Exception e) {
            COMMON_LOGGER.error("Remote JIRA service getProjectVersions failed", e);
        }

        this.saveOrUpdateJiraVersion(list) ;
        return list;
    }


    /**
     * 保存/更新Jira信息，用于数据上报。
     * @param jiraVersionList
     * @return
     */
    private void saveOrUpdateJiraVersion(List<JiraVersion> jiraVersionList){
        if (CollectionUtils.isEmpty(jiraVersionList )){
            return;
        }
        List<ClientG2JiraVersionInfoDO> jiraVersionInfoDOList = new ArrayList<>() ;
        for (JiraVersion jiraVersion : jiraVersionList){
            ClientG2JiraVersionInfoDO clientG2JiraVersionInfoDO = new ClientG2JiraVersionInfoDO() ;
            clientG2JiraVersionInfoDO.setJiraId(jiraVersion.getId());
            clientG2JiraVersionInfoDO.setJiraName(jiraVersion.getName());
            clientG2JiraVersionInfoDO.setJiraKey(jiraVersion.getJiraKey());
            clientG2JiraVersionInfoDO.setProjectId(jiraVersion.getProjectId());
            jiraVersionInfoDOList.add(clientG2JiraVersionInfoDO) ;
        }
        int count = g2JiraVersionInfoDAO.patchSaveAndUpdateJiraVersionDO(jiraVersionInfoDOList) ;
        if (count < jiraVersionList.size()){
            COMMON_LOGGER.error("[JiraService.saveOrUpdateJiraVersion]save jira version failed");
        }
    }

    public List<JiraVersionVO> queryJiraVersion(String jiraKey) {
        List<JiraVersionVO> jiraVersionVOList = new ArrayList<>();
        if (StringUtils.isBlank(jiraKey)){
            return jiraVersionVOList ;
        }
        List<ClientG2JiraVersionInfoDO> jiraVersionInfoDOList = g2JiraVersionInfoDAO.queryJiraVersionInfo(jiraKey) ;
        if (CollectionUtils.isEmpty(jiraVersionInfoDOList)){
            return jiraVersionVOList ;
        }
        for(ClientG2JiraVersionInfoDO clientG2JiraVersionInfoDO : jiraVersionInfoDOList){
            if (clientG2JiraVersionInfoDO!= null){
                JiraVersionVO jiraVersionVO = new JiraVersionVO() ;
                jiraVersionVO.setJiraId(clientG2JiraVersionInfoDO.getJiraId());
                jiraVersionVO.setJiraName(clientG2JiraVersionInfoDO.getJiraName());
                jiraVersionVO.setJiraKey(clientG2JiraVersionInfoDO.getJiraKey());
                jiraVersionVO.setProjectId(clientG2JiraVersionInfoDO.getProjectId());
                jiraVersionVOList.add(jiraVersionVO) ;
            }
        }
        return jiraVersionVOList ;
    }


    public String createJiraIssue(String projectKey, String summary, String issuetype, String desc, String reporter, String checkUser, String handleUser, String priority, String version, String fixVersion){
        try {
            String url = "http://jira.netease.com/rest/api/2/issue";
            Map<String, String> headers = new HashMap<>();
//            headers.put("Content-Type","application/json; charset=UTF-8") ;
            headers.put("Authorization",getAuth());
            Map<String, Object> body = new HashMap<>();
            Map<String, Object> fields = new HashMap<>();
            fields.put("summary",summary);
            fields.put("description",desc);
            Map<String,String> project = new HashMap<>();
            project.put("key",projectKey);
            fields.put("project",project);
            Map<String,String> issue = new HashMap<>();
            issue.put("name",issuetype);
            fields.put("issuetype",issue);
            Map<String,String> report = new HashMap<>();
            report.put("name",reporter);
            fields.put("reporter",report);
            Map<String,String> checker = new HashMap<>();
            checker.put("name",checkUser);
            fields.put("customfield_10301",checker);
            Map<String,String> assignee = new HashMap<>();
            assignee.put("name",handleUser);
            fields.put("assignee",assignee);
            Map<String,String> p = new HashMap<>();
            p.put("id",priority);
            fields.put("priority",p);
            Map<String,String> versionMap = new HashMap<>();
            versionMap.put("id",version);
            List<Map<String,String>> versionList = new ArrayList<>() ;
            versionList.add(versionMap) ;
            fields.put("versions",versionList);
            Map<String,String> fixVersionMap = new HashMap<>();
            fixVersionMap.put("id",fixVersion);
            List<Map<String,String>> fixVersionList = new ArrayList<>() ;
            fixVersionList.add(fixVersionMap) ;
            fields.put("fixVersions",fixVersionList);
            //特殊字段
            Map<String,String> bugFrom = new HashMap<>();
            bugFrom.put("id","17165");
            fields.put("customfield_15325",bugFrom);
            Map<String,String> findWay = new HashMap<>();
            findWay.put("id","19829");
            fields.put("customfield_15805",findWay);

            body.put("fields",fields);
//            System.out.println(JSON.toJSONString(body));
            JSONObject res = HttpUtils.getInstance().jsonPost(url,headers,JSONObject.toJSONString(body));
//            System.out.println(res);
//            JSONObject res = new JSONObject();
//            res.put("key","NRTCG2-27973") ;
//            res.put("id","1762862") ;
//            res.put("self","http://jira.netease.com/rest/api/2/issue/1762862") ;
            if (res == null){
                COMMON_LOGGER.error("[JiraService.createJiraIssue]result is null");
                return null ;
            }
            String issKey = res.getString("key") ;
            return issKey ;
        }catch (Exception e){
            COMMON_LOGGER.error("[JiraService.createJiraIssue]create jira exception",e);
        }
        return null;
    }

    private String getAuth() {
        return "Basic " + encodeCredentials(this.jiraUsername, this.jiraPassword);
    }

    private String encodeCredentials(String username, String password) {
        byte[] credentials = (username + ':' + password).getBytes();
        return new String(Base64.encodeBase64(credentials));
    }


    public static String buildJiraUrl(String jiraKey) {
        return JIRA_BROWSE_URL + jiraKey;
    }
}
