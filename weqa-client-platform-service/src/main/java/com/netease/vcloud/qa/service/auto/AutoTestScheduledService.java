package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.AutoTestResultDAO;
import com.netease.vcloud.qa.dao.ClientAutoDeviceInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoTestScheduledRelationInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientScheduledTaskInfoDAO;
import com.netease.vcloud.qa.model.AutoTestResultDO;
import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoTestScheduledRelationInfoDO;
import com.netease.vcloud.qa.model.VcloudClientScheduledTaskInfoDO;
import com.netease.vcloud.qa.service.auto.AutoCoveredService;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskUrlDTO;
import com.netease.vcloud.qa.service.auto.view.AutoTestScheduledVO;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;

@Service
public class AutoTestScheduledService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String RESULT_ERROR_MESSAGE_ARGS = "errorMessage" ;

    private static final ConcurrentHashMap<Long, ScheduledFuture<?>> cache = new ConcurrentHashMap<>();

    @Resource(name = "myThreadPoolTaskScheduler")
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private VcloudClientAutoTestScheduledRelationInfoDAO scheduledRelationInfoDAO ;

    @Autowired
    private VcloudClientScheduledTaskInfoDAO scheduledTaskInfoDAO;

    @Autowired
    private AutoTestTaskManagerService autoTestTaskManagerService;

    @Autowired
    private AutoTestDeviceService autoTestDeviceService;

    @Autowired
    private AutoTestTaskUrlService autoTestTaskUrlService;

    @Autowired
    private ClientAutoDeviceInfoDAO clientAutoDeviceInfoDAO ;

    public long createScheduledTask(VcloudClientScheduledTaskInfoDO taskInfoDO){
        int id1 = scheduledTaskInfoDAO.insert(taskInfoDO);
        return id1;
    }
    public void stopScheduledTask(Long id1){
//        VcloudClientScheduledTaskInfoDO taskInfoDO1 = scheduledTaskInfoDAO.selectByPrimaryKey(id1);
//        if (taskInfoDO1 != null){
//            taskInfoDO1.setTaskStatus((byte)0);
//            scheduledTaskInfoDAO.updateByPrimaryKey(taskInfoDO1);
//        }
        if (cache.get(id1) == null){
            return;
        }
        ScheduledFuture<?> future = cache.get(id1);
        future.cancel(true);
        cache.remove(id1);
    }

    public List<VcloudClientScheduledTaskInfoDO>  getList(String owner,int pageSize , int pageNo){
        int start = (pageNo-1) * pageSize ;
        return scheduledTaskInfoDAO.queryAutoTaskInfo(owner, start, pageSize);
    }

    public AutoTestScheduledVO getId(Long taskId){
        VcloudClientScheduledTaskInfoDO scheduledTaskInfoDO = scheduledTaskInfoDAO.selectByPrimaryKey(taskId);
        List<VcloudClientAutoTestScheduledRelationInfoDO> scheduledRelationInfoDOList = scheduledRelationInfoDAO.selectByTaskId(taskId);
        AutoTestScheduledVO scheduledVO = new AutoTestScheduledVO();
        scheduledVO.setScheduledTaskInfo(scheduledTaskInfoDO);
        scheduledVO.setScheduledRelationInfoDOList(scheduledRelationInfoDOList);
        return scheduledVO;
    }


    public long updateScheduledTask(VcloudClientScheduledTaskInfoDO taskInfoDO){
        int id1 = scheduledTaskInfoDAO.updateByPrimaryKey(taskInfoDO);
        return id1;
    }

    public long updateScheduledStatus(Long taskId, int status){
        VcloudClientScheduledTaskInfoDO taskInfoDO = scheduledTaskInfoDAO.selectByPrimaryKey(taskId);
        taskInfoDO.setTaskStatus((byte) status);
        long id1 = updateScheduledTask(taskInfoDO);
        if (id1 > 0 ){
            if(status == 0){
                this.stopScheduledTask(taskId);
            }
        }
        return id1;
    }

    public void getJenkinsURL(List<String> platformList, String branch, Long taskId){
        branch = branch.split("/")[1];
        String url = "http://yunxin-jenkins.netease.im:8080/view/Hermes/job/HermesRtcDemo/api/json?pretty=true";
        try {
            JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(new URI(url), "yeguo", "112a541a788238633a565d96d54cc34a92");
            JSONObject object = JSON.parseObject(jenkinsHttpClient.get(""));
            jenkinsHttpClient.close();
            JSONArray array = object.getJSONArray("builds");
            HashMap<String, String> urls = new HashMap<>();
            for(int i = 0; i < array.size(); i++){
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
                    if (!version.equals(branch)){
                        continue;
                    }
                    JSONArray buildUrls = buildJson.getJSONArray("artifacts");
                    for(String platform :platformList){
                        for(int j = 0; j < buildUrls.size(); j++){
                            String platform1 = platform;
                            if (platform.equals("windows")){
                                platform = "win-x64";
                            }
                            else if (platform.equals("mac")){
                                platform = "mac-x64";
                            }
                            String relativePath = buildUrls.getJSONObject(j).getString("relativePath");
                            if(relativePath.contains(platform)){
                                urls.put(platform, baseBuildUrl + "artifact/" + relativePath);
                                try {
                                    autoTestTaskUrlService.addTaskUrl(platform1, taskId, baseBuildUrl + "artifact/" + relativePath);
                                } catch (AutoTestRunException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                    break;
                }
            }
            System.out.println(urls.toString());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0/10 * * * * ? ")
    private void updateScheduledTask(){
        List<VcloudClientScheduledTaskInfoDO> taskInfoDOList = scheduledTaskInfoDAO.queryAutoTaskRunInfo((byte) 1);
        for(VcloudClientScheduledTaskInfoDO taskInfoDO: taskInfoDOList) {
            if(!cache.containsKey(taskInfoDO.getId())){
                ScheduledFuture<?> future = scheduler.schedule(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("定时任务执行： " + taskInfoDO.getId());
                        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
                        autoTestTaskInfoDTO.setTaskName(taskInfoDO.getTaskName());
                        autoTestTaskInfoDTO.setTaskType("python");
                        autoTestTaskInfoDTO.setGitInfo(taskInfoDO.getGitInfo());
                        autoTestTaskInfoDTO.setGitBranch(taskInfoDO.getGitBranch());
                        autoTestTaskInfoDTO.setOperator(taskInfoDO.getOperator());
                        autoTestTaskInfoDTO.setDeviceType((byte)1);
                        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByOwner("system");
                        ArrayList<Long> deviceList = new ArrayList<>();
                        ArrayList<String> platformList = new ArrayList<>();
                        for (ClientAutoDeviceInfoDO deviceInfoDO :clientAutoDeviceInfoDOList ){
                            if (deviceInfoDO.getRun() == 0){
                                if (deviceList.size() < 2){
                                    deviceList.add(deviceInfoDO.getId());
                                    DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(deviceInfoDO.getPlatform());
                                    if (!platformList.contains(devicePlatform.getPlatform())){
                                        platformList.add(devicePlatform.getPlatform());
                                    }
                                }else {
                                    break;
                                }
                            }
                        }
                        autoTestTaskInfoDTO.setDeviceList(deviceList);
                        List<Long> ScriptIds = JSONArray.parseArray(taskInfoDO.getScriptIds(), Long.class);
                        autoTestTaskInfoDTO.setTestCaseScriptId(ScriptIds);
                        autoTestTaskInfoDTO.setPrivateAddressId(Long.valueOf(taskInfoDO.getPrivateId()));
                        try {
                            Long taskId = autoTestTaskManagerService.addNewTaskInfo(autoTestTaskInfoDTO);
                            if (deviceList.size() < 2){
                                autoTestTaskManagerService.setTaskReadySuccess(taskId,false);
                            }
                            getJenkinsURL(platformList, autoTestTaskInfoDTO.getGitBranch(),taskId);
                            VcloudClientAutoTestScheduledRelationInfoDO scheduledRelationInfoDO = new VcloudClientAutoTestScheduledRelationInfoDO();
                            scheduledRelationInfoDO.setScheduledTaskId(taskInfoDO.getId());
                            scheduledRelationInfoDO.setAutoTaskId(taskId);
                            scheduledRelationInfoDAO.insert(scheduledRelationInfoDO);
                        } catch (AutoTestRunException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new CronTrigger(taskInfoDO.getCron()));
                cache.put(taskInfoDO.getId(), future);
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("windows");
        arrayList.add("mac");
        arrayList.add("ios");
        arrayList.add("android");
//        getJenkins(arrayList, "4.6.420");
    }

}
