package com.netease.vcloud.qa.version;

import com.netease.vcloud.qa.PropertiesConfig;
import com.netease.vcloud.qa.dao.ClientConfigVersionCheckWriteListDAO;
import com.netease.vcloud.qa.model.ClientConfigVersionCheckWriteListDO;
import com.netease.vcloud.qa.notify.PopoNotifyService;
import com.netease.vcloud.qa.version.data.ConfigType;
import com.netease.vcloud.qa.version.data.JiraVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private ConfigCheckService configCheckService ;

    @Autowired
    private PopoNotifyService popoNotifyService ;

    @Autowired
    private ClientConfigVersionCheckWriteListDAO clientConfigVersionCheckWriteListDAO ;

    private Map<String, Set> versionMap = new HashMap<>();

    public boolean addWriteList(String type ,List<String> versionList){
        if (CollectionUtils.isEmpty(versionList)|| StringUtils.isBlank(type)){
            return false ;
        }
        List<ClientConfigVersionCheckWriteListDO> clientConfigVersionCheckWriteListDOList = new ArrayList<>() ;
        for (String version : versionList){
            version = version.trim() ;
            ClientConfigVersionCheckWriteListDO clientConfigVersionCheckWriteListDO = new ClientConfigVersionCheckWriteListDO() ;
            clientConfigVersionCheckWriteListDO.setConfigType(type);
            clientConfigVersionCheckWriteListDO.setConfigVersion(version);
            clientConfigVersionCheckWriteListDOList.add(clientConfigVersionCheckWriteListDO) ;
        }
        int count = clientConfigVersionCheckWriteListDAO.patchInsertWriteList(clientConfigVersionCheckWriteListDOList);
        if (count >= clientConfigVersionCheckWriteListDOList.size()){
            return true ;
        }else {
            return false ;
        }
    }


    @Scheduled(cron = "0 0 10 * * ? ")
    public void versionCheckSchedule() {
        this.updateWriteList();
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
        Map<String,String> configIdMap = this.buildConfigIdList(jiraFilterList) ;
//        for (Map.Entry<String,String> entry : configIdMap.entrySet()){
//            System.out.println(entry.getKey()+"|"+entry.getValue());
//        }
        List<String> configLostVersionList = this.getConfigLostVersionList(configIdMap) ;
        boolean notifyResult = this.configWarningNotify(configLostVersionList) ;
        if (!notifyResult){
            COMMON_LOGGER.error("[VersionCheckService.VersionCheckSchedule] configWarningNotify failed");
        }
    }

    private void updateWriteList(){
        this.versionMap = new HashMap<>() ;
        List<ClientConfigVersionCheckWriteListDO> clientConfigVersionCheckWriteListDOS = clientConfigVersionCheckWriteListDAO.getClientConfigVersionCheckWriteListByType(ConfigType.QOS) ;
        if (!CollectionUtils.isEmpty(clientConfigVersionCheckWriteListDOS)){
            Set<String> qosList = new HashSet<String>();
            for (ClientConfigVersionCheckWriteListDO clientConfigVersionCheckWriteListDO : clientConfigVersionCheckWriteListDOS) {
                if (clientConfigVersionCheckWriteListDO !=null){
                    qosList.add(clientConfigVersionCheckWriteListDO.getConfigVersion()) ;
                }
            }
            versionMap.put(ConfigType.QOS,qosList) ;
        }
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

    public String getJiraKey(){
        String jiraKeyStr = propertiesConfig.getProperty(PropertiesConfig.JIRA_KEY) ;
        return jiraKeyStr ;
    }

    //根据版本号，生成对应的ID Map,kye为配置ID ，value为对应版本
    private Map<String,String> buildConfigIdList(List<String> versionList){
        if (CollectionUtils.isEmpty(versionList)) {
            return null;
        }
        //生成二级配置ID
        Map<String,String> idMap = new HashMap<>() ;
        for(String version : versionList){
            if (version.startsWith("4.6.")) {
                List<String> versionIdList = configCheckService.buildConfigIdByVersion(version);
                if (versionIdList != null) {
                    for (String versionId : versionIdList) {
                        idMap.put(versionId, version);
                    }
                }
            }else{
                //不需要校验
            }
        }
        return idMap ;
    }

    private List<String> getConfigLostVersionList(Map<String,String> idMap){
        if (CollectionUtils.isEmpty(idMap)) {
            return null;
        }
        List<String> configNotExistList = new ArrayList<>() ;
        Set qosWriteList = versionMap.get(ConfigType.QOS) ;
        for(Map.Entry<String,String> entry : idMap.entrySet()){
            boolean isConfigExist = configCheckService.isConfigExist(entry.getKey()) ;
            if (!isConfigExist){
                if (qosWriteList.contains(entry.getValue())){
                    continue;
                }
                COMMON_LOGGER.info("[ConfigCheckService.getConfigLostVersionList] config not exist.version is " +entry.getValue() +"key is"+entry.getKey());
                configNotExistList.add(entry.getKey()) ;
            }
        }
        return configNotExistList ;
    }

    private boolean configWarningNotify(List<String> configLostList){
        String notifyUserStr = propertiesConfig.getProperty(PropertiesConfig.CONFIG_NOTIFY_USER) ;
        if (StringUtils.isBlank(notifyUserStr)){
           return true ;
        }
        String content = this.buildNotifyContent(configLostList) ;
        String[] notifyUserArray = notifyUserStr.split(",") ;
        boolean flag = true ;
        for (String notifyUser : notifyUserArray) {
            flag = popoNotifyService.sendPOPOMessage(notifyUser, content) && flag;
        }
        return flag ;
    }

    private String buildNotifyContent(List<String> configLostList){
        StringBuilder contentBuilder = new StringBuilder() ;
        if (CollectionUtils.isEmpty(configLostList)){
            contentBuilder.append("所有版本配置均存在");
            return null ;
        }else {
            contentBuilder.append("QOS以下版本存在配置缺失：\n");
            String info = configCheckService.buildNotifyContent(configLostList);
//            System.out.println(info);
            contentBuilder.append(info);
        }
        return contentBuilder.toString() ;
    }
}
