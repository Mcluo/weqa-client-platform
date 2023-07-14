package com.netease.vcloud.qa.version;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientConfigVersionCheckWriteListDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 配置校验服务
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/6 10:48
 */
@Service
public class ConfigCheckService {

    private static final String AUDIO_CONFIG_FORMAT = "g2-%s-hotfix" ;
    private static final String VIDEO_CONFIG_FORMAT = "video-g2-%s-hotfix" ;
    private static final String QOS_CONFIG_FORMAT = "qos-g2-%s-hotfix" ;
    private static final String VIDEO_HW_CONFIG_FORMAT = "videoHW-g2-%s-hotfix" ;

    private static final String REQUEST_PROTOCOL = "https://" ;

//    private static final String RTC_CONFIG_URL = "config-test.netease.im" ;
    private static final String RTC_CONFIG_URL = "wecan-lbs.netease.im" ;

    private static final String RTC_CONFIG_PATH = "/multimedia-conf/v3/cc/config" ;


    public List<String> buildConfigIdByVersion(String version) {
        if (StringUtils.isBlank(version)){
            return null ;
        }
        version = version.trim() ;
        List<String> configIdList = new ArrayList<>();
//        String audioConfigId = String.format(AUDIO_CONFIG_FORMAT, version) ;
//        configIdList.add(audioConfigId);
//        String videoConfigId = String.format(VIDEO_CONFIG_FORMAT, version) ;
//        configIdList.add(videoConfigId);
        String qosConfigId = String.format(QOS_CONFIG_FORMAT, version) ;
        configIdList.add(qosConfigId);
//        String videoHWConfigId = String.format(VIDEO_HW_CONFIG_FORMAT, version) ;
//        configIdList.add(videoHWConfigId);
        return  configIdList;
    }

    public boolean isConfigExist(String  configId){
        if (StringUtils.isBlank(configId)){
            return false;
        }
        JSONObject result = this.queryConfig(configId) ;
        if (result == null){
            return false ;
        }
        int resultCode = result.getInteger("code") ;
        String resultData = result.getString("data") ;
        if (resultCode == 200 && StringUtils.isNotBlank(resultData)){
//            System.out.println(result.toJSONString());
            return true ;
        }else {
            return false;
        }
    }

    private JSONObject queryConfig(String configId){
        StringBuilder baseUrl = new StringBuilder(REQUEST_PROTOCOL) ;
        baseUrl.append(RTC_CONFIG_URL).append(RTC_CONFIG_PATH).append("?") ;
        baseUrl.append("id=").append(configId) ;
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("accept","*/*") ;
        JSONObject jsonObject = HttpUtils.getInstance().get(baseUrl.toString(),headerMap) ;
        return jsonObject ;
    }

    public String buildNotifyContent(List<String> configLostList){
        if (configLostList == null){
            return null ;
        }
        Map<String,List<String>> configMap = new LinkedHashMap<>();
        for (String configLost : configLostList) {
            String[] configArray = configLost.split("-");
            if (configArray.length < 2){
                continue;
            }
            String version = configArray[configArray.length-2] ;
            List<String> versionConfigList = configMap.get(version) ;
            if (versionConfigList == null){
                versionConfigList = new ArrayList<>() ;
                configMap.put(version,versionConfigList) ;
            }
            versionConfigList.add(configLost);
        }
        if (CollectionUtils.isEmpty(configMap)){
            return null ;
        }
        List<Map.Entry<String,List<String>>> entryList = new ArrayList<>(configMap.entrySet()) ;
        Collections.sort(entryList,new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                String key1 = o1.getKey() ;
                String key2 = o2.getKey() ;
                return key1.compareTo(key2);
            }
        }) ;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String,List<String>> entry : entryList) {
            String version = entry.getKey() ;
            List<String> configList = entry.getValue() ;
            if (CollectionUtils.isEmpty(configList)){
                continue;
            }
            stringBuilder.append(version).append(" , ") ;
//            for (String config : configList) {
//                stringBuilder.append(config).append(" , ") ;
//            }
//            stringBuilder.append("\n") ;
        }
        return stringBuilder.toString() ;
    }
}
