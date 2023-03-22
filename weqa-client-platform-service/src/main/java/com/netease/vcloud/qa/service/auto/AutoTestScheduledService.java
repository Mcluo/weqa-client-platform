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
import com.netease.vcloud.qa.date.DateUtils;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;

@Service
public class AutoTestScheduledService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String RESULT_ERROR_MESSAGE_ARGS = "errorMessage" ;

    private static final ConcurrentHashMap<Long, ScheduledFuture<?>> cache = new ConcurrentHashMap<>();

    private static final byte start = 1;

    private static final byte stop = 0;

    private static final byte running = 3;

    private static final int timeOut = 60;

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

    public long createScheduledTask(VcloudClientScheduledTaskInfoDO taskInfoDO) throws ParseException {

        Date runTime = DateUtils.nextTime(taskInfoDO.getCron(), new Date());
        taskInfoDO.setGmtUpdate(runTime);

        int id1 = scheduledTaskInfoDAO.insert(taskInfoDO);
        return id1;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public long updateScheduledStatus(Long taskId, int status) throws ParseException {
        VcloudClientScheduledTaskInfoDO taskInfoDO = scheduledTaskInfoDAO.selectByPrimaryKey(taskId);
        taskInfoDO.setTaskStatus((byte) status);
        if(status == 1){
            Date runTime = DateUtils.nextTime(taskInfoDO.getCron(), new Date());
            taskInfoDO.setGmtUpdate(runTime);
        }
        long id1 = updateScheduledTask(taskInfoDO);
        return id1;
    }

    @Scheduled(cron = "0/10 * * * * ? ")
    public void runScheduledTask() {
        VcloudClientScheduledTaskInfoDO taskInfoDO = getTaskInfoDO();
        if (taskInfoDO == null){
            return;
        }
        System.out.println("定时任务执行： " + taskInfoDO.getId());
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO();
        autoTestTaskInfoDTO.setTaskName(taskInfoDO.getTaskName());
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setGitInfo(taskInfoDO.getGitInfo());
        autoTestTaskInfoDTO.setGitBranch(taskInfoDO.getGitBranch());
        autoTestTaskInfoDTO.setOperator(taskInfoDO.getOperator());
        autoTestTaskInfoDTO.setDeviceType((byte) 1);
        List<ClientAutoDeviceInfoDO> clientAutoDeviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByOwner("system");
        ArrayList<Long> deviceList = new ArrayList<>();
        ArrayList<String> platformList = new ArrayList<>();
        for (ClientAutoDeviceInfoDO deviceInfoDO : clientAutoDeviceInfoDOList) {
            if (deviceInfoDO.getRun() == (byte)0 && deviceInfoDO.getAlive() == (byte)1) {
                if (deviceList.size() < 2) {
                    deviceList.add(deviceInfoDO.getId());
                    DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByCode(deviceInfoDO.getPlatform());
                    if (!platformList.contains(devicePlatform.getPlatform())) {
                        platformList.add(devicePlatform.getPlatform());
                    }
                } else {
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
//                            getJenkinsURL(platformList, autoTestTaskInfoDTO.getGitBranch(),taskId);
            VcloudClientAutoTestScheduledRelationInfoDO scheduledRelationInfoDO = new VcloudClientAutoTestScheduledRelationInfoDO();
            scheduledRelationInfoDO.setScheduledTaskId(taskInfoDO.getId());
            scheduledRelationInfoDO.setAutoTaskId(taskId);
            scheduledRelationInfoDAO.insert(scheduledRelationInfoDO);
            if (deviceList.size() == 2) {
                autoTestTaskManagerService.setTaskReadySuccess(taskId, true);
                autoTestDeviceService.updateDeviceRun(deviceList, (byte) 1);
            } else {
                autoTestTaskManagerService.setTaskReadySuccess(taskId, false);
            }
            updateScheduledStatus(taskInfoDO.getId(), 1);
        } catch (AutoTestRunException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public VcloudClientScheduledTaskInfoDO getTaskInfoDO(){
        VcloudClientScheduledTaskInfoDO taskInfoDO = null;
        List<VcloudClientScheduledTaskInfoDO> taskInfoDOList = scheduledTaskInfoDAO.queryAutoTaskRunInfo(start, new Date(), null);
        if(taskInfoDOList.size() == 0){
            taskInfoDOList = scheduledTaskInfoDAO.queryAutoTaskRunInfo(running, new Date(),new Date());
            System.out.println("定时任务执行running： " + taskInfoDOList.size());
            if(taskInfoDOList.size() == 0){
                return null;
            }
            taskInfoDO = taskInfoDOList.get(0);
            taskInfoDO.setGmtUpdate(new Date());
            scheduledTaskInfoDAO.updateByPrimaryKey(taskInfoDO);
            return taskInfoDO;
        }
        taskInfoDO = taskInfoDOList.get(0);
        taskInfoDO.setTaskStatus(running);
        scheduledTaskInfoDAO.updateByPrimaryKey(taskInfoDO);
        return taskInfoDO;
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
