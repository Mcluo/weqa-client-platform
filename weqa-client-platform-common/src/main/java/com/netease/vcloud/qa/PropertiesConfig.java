package com.netease.vcloud.qa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置处理
 * @author luqiuwei@corp.netease.com
 * @date 2018/9/18  10:39
 */
@Service
public class PropertiesConfig {

    @Value("${spring.profiles.active}")
    private String env ;
    /**
     * 基础环境
     */
    public static final String ENV = "env" ;
    /**
     * 处理设备类型
     */
    public static final String JIRA_REST_BASEURL = "jira_rest_baseUrl";

    public static final String JIRA_USERNAME = "jira_username";

    public static final String JIRA_PASSWORD = "jira_password";

    public static final String JIRA_KEY = "jira_key";

    public static final String CONFIG_NOTIFY_USER = "config_download_notify_user";

    /**
     * 配置文件名
     */
    private static final String ENV_PROPERTIES = "application-env.properties" ;

    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("CommonLog");

    public static Map<String, String> propertiesMap = new HashMap<>();

    @PostConstruct
    public void init(){
        System.out.println("current env:" + env);
        this.loadAllProperties(ENV_PROPERTIES);
    }

    private  void processProperties(Properties props){
        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                propertiesMap.put(keyStr, new String(props.getProperty(keyStr).getBytes("ISO-8859-1"), "utf-8"));
            }  catch (Exception e) {
                COMMON_LOGGER.error("[PropertiesConfig.processProperties] process properties exception",e);
            }
        }
    }

    public  void loadAllProperties(String propertyFileName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertyFileName);
            processProperties(properties);
        } catch (IOException e) {
            COMMON_LOGGER.error("[PropertiesConfig.loadAllProperties] loadAllProperties exception ",e);
        }
    }

    public String getProperty(String name) {
        return propertiesMap.get(name);
    }

    public String getEnv(){
        return env;
    }

    public Map<String, String> getAllProperty() {
        return propertiesMap;
    }

}
