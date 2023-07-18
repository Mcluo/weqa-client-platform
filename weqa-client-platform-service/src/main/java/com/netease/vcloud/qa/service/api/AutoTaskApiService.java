package com.netease.vcloud.qa.service.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.auto.DeviceType;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.AutoTestDeviceService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.AutoTestTestSuitService;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskUrlDTO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.git.GitInfoService;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/7 19:27
 */
@Service
public class AutoTaskApiService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    private static final Long DEFAULT_GIT_ID = 49837L ;

    private static final String DEFAULT_GIT_BRANCH = "master" ;

//    private static final Long TEST_SUIT_ID = 93L ;
    private static final Long TEST_SUIT_ID = 3L ;

    @Autowired
    private GitInfoService gitInfoService ;

    @Autowired
    private AutoTestTestSuitService autoTestTestSuitService;

    @Autowired
    private AutoTestDeviceService autoTestDeviceService ;
    /**
     * 根据版本获取分支信息
     * @param version
     * @return
     */
    public String getGitBranchByVersion(String version){
        List<String> gitList = gitInfoService.queryGitBranchList(DEFAULT_GIT_ID,version) ;
        if (CollectionUtils.isEmpty(gitList)){
            return DEFAULT_GIT_BRANCH;
        }
        for (String git : gitList){
            //以stab/[版本]为优先分支
            if (git.startsWith("stab")&& git.endsWith(version)){
                return git;
            }
        }
        for (String git : gitList){
            //以feature/[版本]为次优分支
            if(git.startsWith("feature")&& git.endsWith(version)){
                return git ;
            }
        }
        for (String git : gitList){
            //以stab/[版本]+xxx为分支
            if(git.startsWith("stable")){
                return git ;
            }
        }
        return gitList.get(0);
    }

    public String getGitBranchByVersionAndScript(String version,String script){
        if (StringUtils.isNotBlank(script)){
            List<String> gitList = gitInfoService.queryGitBranchList(DEFAULT_GIT_ID,script) ;
            if (!CollectionUtils.isEmpty(gitList)){
                return gitList.get(0);
            }
        }
        return this.getGitBranchByVersion(version) ;
    }


    /**
     * 获取TC用例集，当前直接使用最小回归集合
     * @param extentJsonObject
     * @return
     */
    public List<Long> getTCIds(JSONObject extentJsonObject){
        List<Long> tcIdList = new ArrayList<Long>() ;
        try {
            List<AutoScriptInfoVO> autoScriptInfoVOList = autoTestTestSuitService.getTestSuitScriptInfo(TEST_SUIT_ID);
            if (!CollectionUtils.isEmpty(autoScriptInfoVOList)){
                for (AutoScriptInfoVO autoScriptInfoVO : autoScriptInfoVOList){
                    if (autoScriptInfoVO!=null){
                        tcIdList.add(autoScriptInfoVO.getId());
                    }
                }
            }
        }catch (AutoTestRunException e){
            AUTO_LOGGER.error("[AutoTaskApiService.getTCIds] getTestSuitScriptInfo exception" ,e);
        }
        return tcIdList ;
    }

    public List<ApiTaskBuildData> getTaskBuildData(Long buildId,JenkinsBuildDTO jenkinsBuildDTO) {
        String urls = null ;
        if(jenkinsBuildDTO == null){
            //仅可以在办公网络使用
            urls = this.buildUrlList(buildId) ;
        }else {
            //均可以使用
            urls = this.buildUrlList(jenkinsBuildDTO);
        }
        List<ApiTaskBuildData>  apiTaskBuildDataList = new ArrayList<ApiTaskBuildData>() ;
        List<List<Long>> deviceList = this.getDeviceList() ;
        if (deviceList!=null){
            for (List<Long> devicePairs : deviceList){
                ApiTaskBuildData apiTaskBuildData = new ApiTaskBuildData() ;
                apiTaskBuildData.setUrls(urls);
                apiTaskBuildData.setDeviceList(devicePairs);
                apiTaskBuildDataList.add(apiTaskBuildData) ;
            }
        }
        return apiTaskBuildDataList ;
    }

    public List<List<Long>> getDeviceList(){
        List<List<Long>> deviceList = new ArrayList<List<Long>>() ;
        try {
            int count = 2 ;
            List<Long> deviceIds = new ArrayList<Long>() ;
            List<DeviceInfoVO>  deviceInfoVOList = autoTestDeviceService.getDeviceList(null, DeviceType.REMOTE_DEVICE_TYPE);
            for (DeviceInfoVO deviceInfoVO : deviceInfoVOList){
                if (deviceInfoVO==null){
                    continue;
                }
                if (deviceInfoVO.isRun()==true || deviceInfoVO.isAlive()==false){
                    continue;
                }
                deviceIds.add(deviceInfoVO.getId());
                count-- ;
                if (count<=0){
                    break;
                }
            }
            if (deviceIds.size()>=2) {
                deviceList.add(deviceIds);
            }
        }catch (AutoTestRunException e){
            AUTO_LOGGER.error("[AutoTaskApiService.getDeviceList] exception" ,e);
        }
        return deviceList ;
    }
    private String buildUrlList(JenkinsBuildDTO jenkinsBuildDTO){
        List<AutoTestTaskUrlDTO> urlList = new ArrayList<AutoTestTaskUrlDTO>() ;
        String windowsUrl = StringUtils.isNotBlank(jenkinsBuildDTO.getPc_x64())?jenkinsBuildDTO.getPc_x64():jenkinsBuildDTO.getPc_x86() ;
        if (StringUtils.isNotBlank(windowsUrl)) {
            AutoTestTaskUrlDTO autoTestTaskUrlDTO = new AutoTestTaskUrlDTO();
            autoTestTaskUrlDTO.setPlatform("windows");
            autoTestTaskUrlDTO.setUrl(windowsUrl);
            urlList.add(autoTestTaskUrlDTO);
        }
        String macUrl = StringUtils.isNotBlank(jenkinsBuildDTO.getMac_x64())?jenkinsBuildDTO.getMac_x64():jenkinsBuildDTO.getMac_arm64() ;
        if (StringUtils.isNotBlank(macUrl)) {
            AutoTestTaskUrlDTO autoTestTaskUrlDTO = new AutoTestTaskUrlDTO();
            autoTestTaskUrlDTO.setPlatform("mac");
            autoTestTaskUrlDTO.setUrl(macUrl);
            urlList.add(autoTestTaskUrlDTO);
        }
        if (jenkinsBuildDTO.getAndroid()!=null) {
            AutoTestTaskUrlDTO autoTestTaskUrlDTO = new AutoTestTaskUrlDTO();
            autoTestTaskUrlDTO.setPlatform("android");
            autoTestTaskUrlDTO.setUrl(jenkinsBuildDTO.getAndroid());
            urlList.add(autoTestTaskUrlDTO);
        }
        if (jenkinsBuildDTO.getIos()!=null) {
            AutoTestTaskUrlDTO autoTestTaskUrlDTO = new AutoTestTaskUrlDTO();
            autoTestTaskUrlDTO.setPlatform("ios");
            autoTestTaskUrlDTO.setUrl(jenkinsBuildDTO.getIos());
            urlList.add(autoTestTaskUrlDTO);
        }
        return JSONArray.toJSONString(urlList) ;
    }

    private String buildUrlList(long buildId){
        List<String> platformList = new ArrayList<String>() ;
        platformList.add("windows");
        platformList.add("mac");
        platformList.add("android");
        platformList.add("ios");
        Map<String,String> urlMap = AutoTaskApiService.getJenkinsURL(platformList,buildId);
        List<AutoTestTaskUrlDTO> urlList = new ArrayList<AutoTestTaskUrlDTO>() ;
        for (Map.Entry<String,String> entry : urlMap.entrySet()){
            AutoTestTaskUrlDTO autoTestTaskUrlDTO = new AutoTestTaskUrlDTO() ;
            autoTestTaskUrlDTO.setPlatform(entry.getKey());
            autoTestTaskUrlDTO.setUrl(entry.getValue());
            urlList.add(autoTestTaskUrlDTO);
        }
        return JSONArray.toJSONString(urlList) ;
    }

    public static Map<String,String> getJenkinsURL(List<String> platformList, Long buildId){
        Map<String,String> urlMap = new HashMap<>() ;
        String url = "http://yunxin-jenkins.netease.im:8080/view/Hermes/job/HermesRtcDemo/api/json?pretty=true";
        try {
            JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(new URI(url), "yeguo", "112a541a788238633a565d96d54cc34a92");
            JSONObject object = JSON.parseObject(jenkinsHttpClient.get(""));
            jenkinsHttpClient.close();
            JSONArray array = object.getJSONArray("builds");
//            HashMap<String, String> urls = new HashMap<>();
            for(int i = 0; i < array.size(); i++){
                Long number = array.getJSONObject(i).getLong("number");
                if(!buildId.equals(number)){
                    //匹配构建ID
                    continue;
                }
                String baseBuildUrl = array.getJSONObject(i).getString("url");
                String buildUrl = baseBuildUrl + "api/json?pretty=true";
//                System.out.println("url: " + buildUrl);
                jenkinsHttpClient = new JenkinsHttpClient(new URI(buildUrl), "yeguo", "112a541a788238633a565d96d54cc34a92");
                JSONObject buildJson = JSON.parseObject(jenkinsHttpClient.get(""));
                jenkinsHttpClient.close();
                String buildResult = buildJson.getString("result");
                if (buildResult == null){
                    continue;
                }
                if (buildResult.equals("SUCCESS")){
                    JSONArray actions = buildJson.getJSONArray("actions");
                    JSONArray buildParameters = null;
                    for (int g = 0; g < actions.size(); g++){
                        if(actions.getJSONObject(g).getString("_class").equals("hudson.model.ParametersAction")){
                            buildParameters = actions.getJSONObject(g).getJSONArray("parameters");
                            break;
                        }
//                        buildParameters = buildJson.getJSONArray("actions").getJSONObject(0).getJSONArray("parameters");
                    }
                    String develop = buildParameters.getJSONObject(0).getString("value");
                    if (!develop.equals("develop")){
                        continue;
                    }
                    String version = buildParameters.getJSONObject(1).getString("value");
                    JSONArray buildUrls = buildJson.getJSONArray("artifacts");
                    for(String platform :platformList){
                        String platformOrigin = platform;
                        for(int j = 0; j < buildUrls.size(); j++){
                            if (platform.equals("windows")){
                                platform = "win-x64";
                            }
                            else if (platform.equals("mac")){
                                platform = "mac-x64";
                            }
                            String relativePath = buildUrls.getJSONObject(j).getString("relativePath");
                            if(relativePath.contains(platform)){
                                urlMap.put(platformOrigin, baseBuildUrl + "artifact/" + relativePath);
//                                addTaskUrl(platform1, taskId, baseBuildUrl + "artifact/" + relativePath);
                            }
                        }
                    }
                    break;
                }
                break;
            }
//            System.out.println(urls);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return urlMap ;
    }

//    public static final void main(String[] args) {
//        List<String> platformList = new ArrayList<String>() ;
//        platformList.add("windows");
//        platformList.add("mac");
//        platformList.add("android");
//        platformList.add("ios");
//        AutoTaskApiService.getJenkinsURL(platformList,3721L);
//    }

}
