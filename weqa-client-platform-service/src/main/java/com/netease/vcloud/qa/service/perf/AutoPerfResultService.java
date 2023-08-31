package com.netease.vcloud.qa.service.perf;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.dao.*;
import com.netease.vcloud.qa.model.*;
import com.netease.vcloud.qa.service.perf.data.AutoPerfBaseReportResultDataInterface;
import com.netease.vcloud.qa.service.perf.data.PerfReportResultDetailData;
import com.netease.vcloud.qa.service.perf.data.PerfReportResultData;
import com.netease.vcloud.qa.service.perf.data.PerfTestReportData;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseReportInterface;
import com.netease.vcloud.qa.service.perf.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 21:04
 */
@Service
public class AutoPerfResultService implements AutoPerfBaseReportInterface {

    private SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("hh:mm:ss");


    @Autowired
    private VcloudClientAutoPerfTaskDAO  clientAutoPerfTaskDAO ;

    @Autowired
    private VcloudClientAutoAndroidPrefInfoDAO clientAutoAndroidPrefInfoDAO ;

    @Autowired
    private VcloudClientAutoIosPrefInfoDAO  vcloudClientAutoIosPrefInfoDAO ;

    @Autowired
    private VcloudClientAutoIosPrefMemoryInfoDAO vcloudClientAutoIosPrefMemoryInfoDAO ;

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;

    @Autowired
    private UserInfoService userInfoService ;
    /**
     * 获取性能任务列表
     * @param current
     * @param size
     * @return
     */
    public PerfTaskInfoListVO getPerfTaskInfoList(String userInfo,int current, int size){
        int start = (current - 1) * size ;
        PerfTaskInfoListVO perfTaskInfoListVO = new PerfTaskInfoListVO() ;
        List<PerfTaskInfoVO> taskInfoVOList = new ArrayList<PerfTaskInfoVO>() ;
        perfTaskInfoListVO.setList(taskInfoVOList);
        perfTaskInfoListVO.setPage(current);
        perfTaskInfoListVO.setSize(size);
        List<VcloudClientAutoPerfTaskDO> autoPerfTaskDOList = clientAutoPerfTaskDAO.queryAutoPerfTaskList(userInfo,start,size) ;
        int total = clientAutoPerfTaskDAO.countAUtoPerfTask(userInfo) ;
        perfTaskInfoListVO.setTotal(total);
        if (CollectionUtils.isEmpty(autoPerfTaskDOList)){
            return perfTaskInfoListVO ;
        }
        Set<String> userSet = new HashSet<String>() ;
        for (VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO : autoPerfTaskDOList){
            if (clientAutoPerfTaskDO != null) {
                userSet.add(clientAutoPerfTaskDO.getUser());
            }
        }
        Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(userSet) ;
        for (VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO : autoPerfTaskDOList){
            PerfTaskInfoVO perfTaskInfoVO = this.buildPerfTaskInfoVOByDO(clientAutoPerfTaskDO,userInfoBOMap) ;
            if (perfTaskInfoVO != null){
                taskInfoVOList.add(perfTaskInfoVO) ;
            }
        }
        return perfTaskInfoListVO ;
    }

    private PerfTaskInfoVO buildPerfTaskInfoVOByDO(VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO,Map<String,UserInfoBO> userInfoBOMap){
        if (clientAutoPerfTaskDO == null){
            return null ;
        }
        PerfTaskInfoVO perfTaskInfoVO = new PerfTaskInfoVO() ;
        perfTaskInfoVO.setId((long)clientAutoPerfTaskDO.getId());
        perfTaskInfoVO.setName(clientAutoPerfTaskDO.getName());
        perfTaskInfoVO.setCpuInfo(clientAutoPerfTaskDO.getCpuinfo());
        perfTaskInfoVO.setSdkInfo(clientAutoPerfTaskDO.getSdkinfo());
        perfTaskInfoVO.setSdkVersion(clientAutoPerfTaskDO.getSdkversion());
        perfTaskInfoVO.setPlatform(clientAutoPerfTaskDO.getDevicesplatform());
        perfTaskInfoVO.setModel(clientAutoPerfTaskDO.getDevicesmodel());
        perfTaskInfoVO.setPlatformVersion(clientAutoPerfTaskDO.getDevicesversion());
        if (clientAutoPerfTaskDO.getCreateTime()!=null) {
            perfTaskInfoVO.setTaskTime(clientAutoPerfTaskDO.getCreateTime().getTime());
        }
        if (userInfoBOMap!=null) {
            UserInfoBO userInfoBO = userInfoBOMap.get(clientAutoPerfTaskDO.getUser());
            if (userInfoBO != null) {
                perfTaskInfoVO.setUser(CommonUtils.buildUserInfoVOByBO(userInfoBO));
            }
        }
        perfTaskInfoVO.setAutoId(clientAutoPerfTaskDO.getAutoTaskId());
        return perfTaskInfoVO ;
    }

    /**
     * 性能测试的详情数据
     * @param taskId
     * @return
     */
    public PerfTaskDetailVO getPerfTaskDetailById(long taskId){
        VcloudClientAutoPerfTaskDO vcloudClientAutoPerfTaskDO = clientAutoPerfTaskDAO.selectByPrimaryKey((int)taskId) ;
        if (vcloudClientAutoPerfTaskDO == null){
            return null ;
        }
        PerfTaskDetailVO perfTaskDetailVO = new PerfTaskDetailVO() ;
        UserInfoBO userInfoBO = userInfoService.getUserInfoByEmail(vcloudClientAutoPerfTaskDO.getUser()) ;
        PerfTaskInfoVO perfTaskInfoVO = this.buildPerfTaskInfoVOByDO(vcloudClientAutoPerfTaskDO,null) ;
        if (perfTaskInfoVO==null){
            return null ;
        }
        perfTaskInfoVO.setUser(CommonUtils.buildUserInfoVOByBO(userInfoBO));
        //添加和查找自动化测试信息
        if (vcloudClientAutoPerfTaskDO.getAutoTaskId() != null){
            ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(vcloudClientAutoPerfTaskDO.getAutoTaskId()) ;
            if (clientAutoTaskInfoDO != null){
                TaskRunStatus taskRunStatus = TaskRunStatus.getTaskRunStatusByCode(clientAutoTaskInfoDO.getTaskStatus()) ;
                if (taskRunStatus != null){
                    perfTaskInfoVO.setAutoStatus(taskRunStatus.getStatus());
                }
            }
        }
        perfTaskDetailVO.setPerfTaskInfo(perfTaskInfoVO);
        String devicePlatformStr = vcloudClientAutoPerfTaskDO.getDevicesplatform() ;
        DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByName(devicePlatformStr) ;
        if (devicePlatform == null){
            return perfTaskDetailVO ;
        }
        PerfTaskDetailInterface perfTaskDetailInterface = null ;
        if (devicePlatform == DevicePlatform.ANDROID){
            perfTaskDetailInterface = this.buildAndroidDetailInfo(taskId) ;
        }else if (devicePlatform == DevicePlatform.IOS){
            perfTaskDetailInterface = this.buildIOSDetailInfo(taskId) ;
        }else {
            //do nothing
        }
        perfTaskDetailVO.setDetailInfo(perfTaskDetailInterface);
        return perfTaskDetailVO ;
    }

    private PerfTaskAndroidDetailVO buildAndroidDetailInfo(long taskId){
        List<VcloudClientAutoAndroidPrefInfoDO> vcloudClientAutoAndroidPrefInfoDOList = clientAutoAndroidPrefInfoDAO.queryAndroidPrefInfoDOByTaskId((int)taskId) ;
        if (CollectionUtils.isEmpty(vcloudClientAutoAndroidPrefInfoDOList)){
            return null ;
        }
        PerfTaskAndroidDetailVO perfTaskAndroidDetailVO = new PerfTaskAndroidDetailVO() ;
        List<PerfTaskAndroidDetailListVO> detailList = new ArrayList<PerfTaskAndroidDetailListVO>() ;
        perfTaskAndroidDetailVO.setDetailList(detailList);
        PerfTaskStatisticBuild memoryStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild temperatureStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild voltageStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild instantAmperageStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild powerStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild levelStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild appCpuStatistic = new PerfTaskStatisticBuild();
        PerfTaskStatisticBuild cpuStatistic = new PerfTaskStatisticBuild();
        PerfTaskStatisticBuild gpuStatistic = new PerfTaskStatisticBuild() ;
        for (VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO : vcloudClientAutoAndroidPrefInfoDOList) {
            if (clientAutoAndroidPrefInfoDO == null){
                continue;
            }
            PerfTaskAndroidDetailListVO perfTaskAndroidDetailListVO = new PerfTaskAndroidDetailListVO() ;
            perfTaskAndroidDetailListVO.setTimes(clientAutoAndroidPrefInfoDO.getTimes());
            String timeStr = SIMPLE_DATE_FORMAT.format(new Date (clientAutoAndroidPrefInfoDO.getTimes())) ; ;
            perfTaskAndroidDetailListVO.setMemory(clientAutoAndroidPrefInfoDO.getMemory());
            memoryStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getMemory());
            perfTaskAndroidDetailListVO.setTemperature(clientAutoAndroidPrefInfoDO.getTemperature());
            temperatureStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getTemperature());
            perfTaskAndroidDetailListVO.setVoltage(clientAutoAndroidPrefInfoDO.getVoltage());
            voltageStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getVoltage());
            perfTaskAndroidDetailListVO.setInstantAmperage(clientAutoAndroidPrefInfoDO.getInstantamperage());
            instantAmperageStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getInstantamperage());
            perfTaskAndroidDetailListVO.setPower(clientAutoAndroidPrefInfoDO.getPower());
            powerStatistic.add(timeStr , clientAutoAndroidPrefInfoDO.getPower());
            perfTaskAndroidDetailListVO.setLevel(clientAutoAndroidPrefInfoDO.getLevel());
            levelStatistic.add(timeStr , clientAutoAndroidPrefInfoDO.getLevel());
            perfTaskAndroidDetailListVO.setAppCPU(clientAutoAndroidPrefInfoDO.getAppCpu());
            appCpuStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getAppCpu());
            perfTaskAndroidDetailListVO.setCpu(clientAutoAndroidPrefInfoDO.getCpu());
            cpuStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getCpu());
            perfTaskAndroidDetailListVO.setGpu(clientAutoAndroidPrefInfoDO.getGpu());
            gpuStatistic.add(timeStr,clientAutoAndroidPrefInfoDO.getGpu());
            detailList.add(perfTaskAndroidDetailListVO);
        }
        perfTaskAndroidDetailVO.setMemory(memoryStatistic.build());
        perfTaskAndroidDetailVO.setMemoryDetail(memoryStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setTemperature(temperatureStatistic.build());
        perfTaskAndroidDetailVO.setTemperatureDetail(temperatureStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setVoltage(voltageStatistic.build());
        perfTaskAndroidDetailVO.setVoltageDetail(voltageStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setInstantAmperage(instantAmperageStatistic.build());
        perfTaskAndroidDetailVO.setInstantAmperageDetail(instantAmperageStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setPower(powerStatistic.build());
        perfTaskAndroidDetailVO.setPowerDetail(powerStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setLevel(levelStatistic.build());
        perfTaskAndroidDetailVO.setLevelDetail(levelStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setAppCPU(appCpuStatistic.build());
        perfTaskAndroidDetailVO.setAppCPUDetail(appCpuStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setCpu(cpuStatistic.build());
        perfTaskAndroidDetailVO.setCpuDetail(cpuStatistic.buildDetailList());
        perfTaskAndroidDetailVO.setGpu(gpuStatistic.build());
        perfTaskAndroidDetailVO.setGpuDetail(gpuStatistic.buildDetailList());
        return perfTaskAndroidDetailVO ;
    }


    private PerfTaskIOSDetailVO buildIOSDetailInfo(long taskId){
        List<VcloudClientAutoIosPrefInfoDO> autoIosPrefInfoDOList= vcloudClientAutoIosPrefInfoDAO.queryIOSPrefInfoDOByTaskId((int)taskId) ;
        List<VcloudClientAutoIosPrefMemoryInfoDO> autoIosPrefMemoryInfoDOList = vcloudClientAutoIosPrefMemoryInfoDAO.queryIOSPrefMemoryInfoDOByTaskId((int)taskId) ;
        PerfTaskIOSDetailVO perfTaskIOSDetailVO = new PerfTaskIOSDetailVO() ;
        List<PerfTaskIOSDetailListVO> perfTaskIOSDetailListVOList = new ArrayList<PerfTaskIOSDetailListVO>() ;
        perfTaskIOSDetailVO.setDetailList(perfTaskIOSDetailListVOList);
        List<PerfTaskIOSDetailMemoryListVO> perfTaskIOSDetailMemoryListVOList = new ArrayList<PerfTaskIOSDetailMemoryListVO>() ;
        perfTaskIOSDetailVO.setMemoryDetailList(perfTaskIOSDetailMemoryListVOList);
        PerfTaskStatisticBuild memoryStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild userCpuStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild sysCpuStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild temperatureStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild voltageStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild instantAmperageStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild powerStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild levelStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild deviceGPUStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild tilerGPUStatistic = new PerfTaskStatisticBuild() ;
        PerfTaskStatisticBuild renderGPUStatistic = new PerfTaskStatisticBuild() ;
        if (!CollectionUtils.isEmpty(autoIosPrefInfoDOList)){
            for (VcloudClientAutoIosPrefInfoDO vcloudClientAutoIosPrefInfoDO : autoIosPrefInfoDOList){
                if (vcloudClientAutoIosPrefInfoDO == null){
                    continue;
                }
                String timeStr = SIMPLE_DATE_FORMAT.format(new Date (vcloudClientAutoIosPrefInfoDO.getTimes())) ; ;
                PerfTaskIOSDetailListVO perfTaskIOSDetailListVO = new PerfTaskIOSDetailListVO() ;
                perfTaskIOSDetailListVO.setInstantAmperage(vcloudClientAutoIosPrefInfoDO.getInstantamperage());
                instantAmperageStatistic.add(timeStr,vcloudClientAutoIosPrefInfoDO.getInstantamperage());
                perfTaskIOSDetailListVO.setLevel(vcloudClientAutoIosPrefInfoDO.getLevel());
                levelStatistic.add(timeStr,vcloudClientAutoIosPrefInfoDO.getLevel());
                perfTaskIOSDetailListVO.setTemperature(vcloudClientAutoIosPrefInfoDO.getTemperature());
                temperatureStatistic.add(timeStr,vcloudClientAutoIosPrefInfoDO.getTemperature());
                perfTaskIOSDetailListVO.setPower(vcloudClientAutoIosPrefInfoDO.getPower());
                powerStatistic.add(timeStr,vcloudClientAutoIosPrefInfoDO.getPower());
                perfTaskIOSDetailListVO.setVoltage(vcloudClientAutoIosPrefInfoDO.getVoltage());
                voltageStatistic.add(timeStr,vcloudClientAutoIosPrefInfoDO.getVoltage());
                perfTaskIOSDetailListVO.setTimes(vcloudClientAutoIosPrefInfoDO.getTimes());
                perfTaskIOSDetailListVOList.add(perfTaskIOSDetailListVO) ;
            }
        }
        if (!CollectionUtils.isEmpty(autoIosPrefMemoryInfoDOList)){
            for (VcloudClientAutoIosPrefMemoryInfoDO vcloudClientAutoIosPrefMemoryInfoDO : autoIosPrefMemoryInfoDOList){
                if (vcloudClientAutoIosPrefMemoryInfoDO == null){
                    continue;
                }
                String timeStr = SIMPLE_DATE_FORMAT.format(new Date (vcloudClientAutoIosPrefMemoryInfoDO.getTimes())) ; ;
                PerfTaskIOSDetailMemoryListVO perfTaskIOSDetailMemoryListVO = new PerfTaskIOSDetailMemoryListVO() ;
                perfTaskIOSDetailMemoryListVO.setTimes(vcloudClientAutoIosPrefMemoryInfoDO.getTimes());
                perfTaskIOSDetailMemoryListVO.setMemory(vcloudClientAutoIosPrefMemoryInfoDO.getMemory());
                memoryStatistic.add(timeStr,vcloudClientAutoIosPrefMemoryInfoDO.getMemory());
                perfTaskIOSDetailMemoryListVO.setAppCpu(vcloudClientAutoIosPrefMemoryInfoDO.getAppCpu());
                userCpuStatistic.add(timeStr,vcloudClientAutoIosPrefMemoryInfoDO.getAppCpu());
                perfTaskIOSDetailMemoryListVO.setSysCpu(vcloudClientAutoIosPrefMemoryInfoDO.getSysCpu());
                sysCpuStatistic.add(timeStr,vcloudClientAutoIosPrefMemoryInfoDO.getSysCpu());
                perfTaskIOSDetailMemoryListVO.setDeviceGPU(vcloudClientAutoIosPrefMemoryInfoDO.getDeviceGPU());
                deviceGPUStatistic.add(timeStr,vcloudClientAutoIosPrefMemoryInfoDO.getDeviceGPU());
                perfTaskIOSDetailMemoryListVO.setRenderGPU(vcloudClientAutoIosPrefMemoryInfoDO.getRenderGPU());
                renderGPUStatistic.add(timeStr,vcloudClientAutoIosPrefMemoryInfoDO.getRenderGPU());
                perfTaskIOSDetailMemoryListVO.setTilerGPU(vcloudClientAutoIosPrefMemoryInfoDO.getTilerGPU());
                tilerGPUStatistic.add(timeStr,vcloudClientAutoIosPrefMemoryInfoDO.getTilerGPU());
                perfTaskIOSDetailMemoryListVOList.add(perfTaskIOSDetailMemoryListVO);
            }
        }
        if (CollectionUtils.isEmpty(autoIosPrefInfoDOList) && CollectionUtils.isEmpty(autoIosPrefMemoryInfoDOList)){
            return perfTaskIOSDetailVO;
        }
        perfTaskIOSDetailVO.setAppCPU(userCpuStatistic.build());
        perfTaskIOSDetailVO.setAppCPUDetail(userCpuStatistic.buildDetailList());
        perfTaskIOSDetailVO.setSysCPU(sysCpuStatistic.build());
        perfTaskIOSDetailVO.setSysCPUDetail(sysCpuStatistic.buildDetailList());
        perfTaskIOSDetailVO.setMemory(memoryStatistic.build());
        perfTaskIOSDetailVO.setMemoryDetail(memoryStatistic.buildDetailList());
        perfTaskIOSDetailVO.setTemperature(temperatureStatistic.build());
        perfTaskIOSDetailVO.setTemperatureDetail(temperatureStatistic.buildDetailList());
        perfTaskIOSDetailVO.setVoltage(voltageStatistic.build());
        perfTaskIOSDetailVO.setVoltageDetail(voltageStatistic.buildDetailList());
        perfTaskIOSDetailVO.setInstantAmperage(instantAmperageStatistic.build());
        perfTaskIOSDetailVO.setInstantAmperageDetail(instantAmperageStatistic.buildDetailList());
        perfTaskIOSDetailVO.setPower(powerStatistic.build());
        perfTaskIOSDetailVO.setPowerDetail(powerStatistic.buildDetailList());
        perfTaskIOSDetailVO.setLevel(levelStatistic.build());
        perfTaskIOSDetailVO.setLevelDetail(levelStatistic.buildDetailList());
        perfTaskIOSDetailVO.setDeviceGPU(deviceGPUStatistic.build());
        perfTaskIOSDetailVO.setDeviceGPUDetail(deviceGPUStatistic.buildDetailList());
        perfTaskIOSDetailVO.setTilerGPU(tilerGPUStatistic.build());
        perfTaskIOSDetailVO.setTilerGPUDetail(tilerGPUStatistic.buildDetailList());
        perfTaskIOSDetailVO.setRenderGPU(renderGPUStatistic.build());
        perfTaskIOSDetailVO.setRenderGPUDetail(renderGPUStatistic.buildDetailList());
        return perfTaskIOSDetailVO ;
    }

    @Override
    public List<PerfBasePerfTaskInfoVO> getBaseTaskInfoList(String query, int start, int limit) {
        List<VcloudClientAutoPerfTaskDO> vcloudClientAutoPerfTaskDOS = clientAutoPerfTaskDAO.queryAutoPerfTaskListByKey(query, start, limit) ;
        List<PerfBasePerfTaskInfoVO> perfBasePerfTaskInfoVOList = new ArrayList<PerfBasePerfTaskInfoVO>() ;
        if (CollectionUtils.isEmpty(vcloudClientAutoPerfTaskDOS)){
            return perfBasePerfTaskInfoVOList ;
        }
        for (VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO : vcloudClientAutoPerfTaskDOS){
            if (clientAutoPerfTaskDO == null){
                continue;
            }
            PerfBasePerfTaskInfoVO perfBasePerfTaskInfoVO = new PerfBasePerfTaskInfoVO() ;
            perfBasePerfTaskInfoVO.setId(new Long(clientAutoPerfTaskDO.getId()));
            perfBasePerfTaskInfoVO.setName(clientAutoPerfTaskDO.getName());
            perfBasePerfTaskInfoVO.setType(AutoPerfType.NORMAL.getName());
            perfBasePerfTaskInfoVOList.add(perfBasePerfTaskInfoVO) ;
        }
        return perfBasePerfTaskInfoVOList;
    }

    @Override
    public AutoPerfBaseReportResultDataInterface buildAutoPerfBaseReportResultData(List<Long> taskIdList, String baselineResultDataStr) {
        if (CollectionUtils.isEmpty(taskIdList)){
            return null ;
        }
        PerfReportResultData perfReportResultData = new PerfReportResultData() ;
        List<PerfReportResultDetailData> dataList = new ArrayList<>() ;
        perfReportResultData.setDataList(dataList);

        PerfReportResultData perfBaseLine = StringUtils.isBlank(baselineResultDataStr)?null:JSONObject.parseObject(baselineResultDataStr,PerfReportResultData.class) ;
        Map<String,PerfReportResultDetailData> perfBaseLineDeviceMap = new HashMap<>() ;
        if (perfBaseLine!=null && !CollectionUtils.isEmpty(perfBaseLine.getDataList())){
            for (PerfReportResultDetailData perfReportResultDetailData :perfBaseLine.getDataList()){
                perfBaseLineDeviceMap.put(perfReportResultDetailData.getDeviceInfo(),perfReportResultDetailData) ;
            }
        }
        for (long taskId : taskIdList){
            VcloudClientAutoPerfTaskDO vcloudClientAutoPerfTaskDO = clientAutoPerfTaskDAO.selectByPrimaryKey((int)taskId) ;
            if (vcloudClientAutoPerfTaskDO == null){
                continue;
            }
            //运行基础信息
            String platform = vcloudClientAutoPerfTaskDO.getDevicesplatform() ;
            DevicePlatform devicePlatform = DevicePlatform.getDevicePlatformByName(platform) ;
            String device = vcloudClientAutoPerfTaskDO.getDevicesmodel() ;
            PerfReportResultDetailData perfBaseLineDetailData = perfBaseLineDeviceMap.get(device) ;
            PerfReportResultDetailData perfReportResultDetailData = new PerfReportResultDetailData() ;
            perfReportResultDetailData.setTaskId((long)vcloudClientAutoPerfTaskDO.getId());
            perfReportResultDetailData.setPlatform(devicePlatform.getPlatform());
            perfReportResultDetailData.setDeviceInfo(vcloudClientAutoPerfTaskDO.getDevicesmodel());
            //数据
            if (devicePlatform.equals(DevicePlatform.ANDROID)){
                List<VcloudClientAutoAndroidPrefInfoDO> vcloudClientAutoAndroidPrefInfoDOList = clientAutoAndroidPrefInfoDAO.queryAndroidPrefInfoDOByTaskId((int)taskId) ;
                int count = 0 ;
                double total_memory = 0  ;
                double max_memory = Long.MIN_VALUE ;
                double min_memory = Long.MAX_VALUE ;
                double total_voltage = 0  ;
                double max_voltage = Long.MIN_VALUE ;
                double min_voltage = Long.MAX_VALUE ;
                double total_temperature = 0  ;
                double max_temperature = Long.MIN_VALUE ;
                double min_temperature = Long.MAX_VALUE ;
                double total_instantamperage = 0  ;
                double max_instantamperage = Long.MIN_VALUE ;
                double min_instantamperage = Long.MAX_VALUE ;
                double total_power = 0  ;
                double max_power = Long.MIN_VALUE ;
                double min_power = Long.MAX_VALUE ;
                double total_level = 0  ;
                double max_level = Long.MIN_VALUE ;
                double min_level = Long.MAX_VALUE ;

                for (VcloudClientAutoAndroidPrefInfoDO vcloudClientAutoAndroidPrefInfoDO : vcloudClientAutoAndroidPrefInfoDOList){
                    if(vcloudClientAutoAndroidPrefInfoDO==null){
                        continue;
                    }
                    count ++ ;
                    total_memory += vcloudClientAutoAndroidPrefInfoDO.getMemory() ;
                    if (max_memory < vcloudClientAutoAndroidPrefInfoDO.getMemory()) {
                        max_memory = vcloudClientAutoAndroidPrefInfoDO.getMemory() ;
                    }
                    if (min_memory > vcloudClientAutoAndroidPrefInfoDO.getMemory()) {
                        min_memory = vcloudClientAutoAndroidPrefInfoDO.getMemory() ;
                    }
                    total_voltage += vcloudClientAutoAndroidPrefInfoDO.getVoltage() ;
                    if (max_voltage < vcloudClientAutoAndroidPrefInfoDO.getVoltage()) {
                        max_voltage = vcloudClientAutoAndroidPrefInfoDO.getVoltage() ;
                    }
                    if (min_voltage > vcloudClientAutoAndroidPrefInfoDO.getVoltage()) {
                        min_voltage = vcloudClientAutoAndroidPrefInfoDO.getVoltage() ;
                    }
                    total_temperature += vcloudClientAutoAndroidPrefInfoDO.getTemperature() ;
                    if (max_temperature < vcloudClientAutoAndroidPrefInfoDO.getTemperature()) {
                        max_temperature = vcloudClientAutoAndroidPrefInfoDO.getTemperature() ;
                    }
                    if (min_temperature > vcloudClientAutoAndroidPrefInfoDO.getTemperature()) {
                        min_temperature = vcloudClientAutoAndroidPrefInfoDO.getTemperature() ;
                    }
                    total_instantamperage += vcloudClientAutoAndroidPrefInfoDO.getInstantamperage() ;
                    if (max_instantamperage < vcloudClientAutoAndroidPrefInfoDO.getInstantamperage()) {
                        max_instantamperage = vcloudClientAutoAndroidPrefInfoDO.getInstantamperage() ;
                    }
                    if (min_instantamperage > vcloudClientAutoAndroidPrefInfoDO.getInstantamperage()) {
                        min_instantamperage = vcloudClientAutoAndroidPrefInfoDO.getInstantamperage() ;
                    }
                    total_power += vcloudClientAutoAndroidPrefInfoDO.getPower() ;
                    if (max_power < vcloudClientAutoAndroidPrefInfoDO.getPower()) {
                        max_power = vcloudClientAutoAndroidPrefInfoDO.getPower() ;
                    }
                    if (min_power > vcloudClientAutoAndroidPrefInfoDO.getPower()) {
                        min_power = vcloudClientAutoAndroidPrefInfoDO.getPower() ;
                    }
                    total_level += vcloudClientAutoAndroidPrefInfoDO.getLevel() ;
                    if (max_level < vcloudClientAutoAndroidPrefInfoDO.getLevel()) {
                        max_level = vcloudClientAutoAndroidPrefInfoDO.getLevel() ;
                    }
                    if (min_level > vcloudClientAutoAndroidPrefInfoDO.getLevel()) {
                        min_level = vcloudClientAutoAndroidPrefInfoDO.getLevel() ;
                    }
                }

                if (count > 0 ) {
                    PerfTestReportData memoryPerfTestReportData = new PerfTestReportData();
                    memoryPerfTestReportData.setCount(count);
                    memoryPerfTestReportData.setAvg(total_memory / count);
                    memoryPerfTestReportData.setMax(max_memory);
                    memoryPerfTestReportData.setMin(min_memory);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getMemory()!=null){
                        memoryPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getMemory().getBaseAvg());
                        memoryPerfTestReportData.setBaseMax(perfBaseLineDetailData.getMemory().getBaseMax());
                        memoryPerfTestReportData.setBaseMin(perfBaseLineDetailData.getMemory().getBaseMin());
                    }
                    perfReportResultDetailData.setMemory(memoryPerfTestReportData);

                    PerfTestReportData voltagePerfTestReportData = new PerfTestReportData();
                    voltagePerfTestReportData.setCount(count);
                    voltagePerfTestReportData.setAvg(total_voltage / count);
                    voltagePerfTestReportData.setMax(max_voltage);
                    voltagePerfTestReportData.setMin(min_voltage);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getVoltage()!=null){
                        voltagePerfTestReportData.setBaseAvg(perfBaseLineDetailData.getVoltage().getBaseAvg());
                        voltagePerfTestReportData.setBaseMax(perfBaseLineDetailData.getVoltage().getBaseMax());
                        voltagePerfTestReportData.setBaseMin(perfBaseLineDetailData.getVoltage().getBaseMin());
                    }
                    perfReportResultDetailData.setVoltage(voltagePerfTestReportData);

                    PerfTestReportData temperaturePerfTestReportData = new PerfTestReportData();
                    temperaturePerfTestReportData.setCount(count);
                    temperaturePerfTestReportData.setAvg(total_temperature / count);
                    temperaturePerfTestReportData.setMax(max_temperature);
                    temperaturePerfTestReportData.setMin(min_temperature);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getVoltage()!=null){
                        temperaturePerfTestReportData.setBaseAvg(perfBaseLineDetailData.getTemperature().getBaseAvg());
                        temperaturePerfTestReportData.setBaseMax(perfBaseLineDetailData.getTemperature().getBaseMax());
                        temperaturePerfTestReportData.setBaseMin(perfBaseLineDetailData.getTemperature().getBaseMin());
                    }
                    perfReportResultDetailData.setTemperature(temperaturePerfTestReportData);

                    PerfTestReportData instantamperagePerfTestReportData = new PerfTestReportData();
                    instantamperagePerfTestReportData.setCount(count);
                    instantamperagePerfTestReportData.setAvg(total_instantamperage / count);
                    instantamperagePerfTestReportData.setMax(max_instantamperage);
                    instantamperagePerfTestReportData.setMin(min_instantamperage);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getInstantAmperage()!=null){
                        instantamperagePerfTestReportData.setBaseAvg(perfBaseLineDetailData.getInstantAmperage().getBaseAvg());
                        instantamperagePerfTestReportData.setBaseMax(perfBaseLineDetailData.getInstantAmperage().getBaseMax());
                        instantamperagePerfTestReportData.setBaseMin(perfBaseLineDetailData.getInstantAmperage().getBaseMin());
                    }
                    perfReportResultDetailData.setInstantAmperage(instantamperagePerfTestReportData);

                    PerfTestReportData powerPerfTestReportData = new PerfTestReportData();
                    powerPerfTestReportData.setCount(count);
                    powerPerfTestReportData.setAvg(total_power / count);
                    powerPerfTestReportData.setMax(max_power);
                    powerPerfTestReportData.setMin(min_power);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getPower()!=null){
                        powerPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getPower().getBaseAvg());
                        powerPerfTestReportData.setBaseMax(perfBaseLineDetailData.getPower().getBaseMax());
                        powerPerfTestReportData.setBaseMin(perfBaseLineDetailData.getPower().getBaseMin());
                    }
                    perfReportResultDetailData.setPower(powerPerfTestReportData);

                    PerfTestReportData levelPerfTestReportData = new PerfTestReportData();
                    levelPerfTestReportData.setCount(count);
                    levelPerfTestReportData.setAvg(total_level / count);
                    levelPerfTestReportData.setMax(max_level);
                    levelPerfTestReportData.setMin(min_level);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getLevel()!=null){
                        levelPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getLevel().getBaseAvg());
                        levelPerfTestReportData.setBaseMax(perfBaseLineDetailData.getLevel().getBaseMax());
                        levelPerfTestReportData.setBaseMin(perfBaseLineDetailData.getLevel().getBaseMin());
                    }
                    perfReportResultDetailData.setLevel(levelPerfTestReportData);
                }

            }else if(devicePlatform.equals(DevicePlatform.IOS)){
                List<VcloudClientAutoIosPrefInfoDO> vcloudClientAutoIosPrefInfoDOList = vcloudClientAutoIosPrefInfoDAO.queryIOSPrefInfoDOByTaskId((int)taskId) ;
                List<VcloudClientAutoIosPrefMemoryInfoDO> vcloudClientAutoIosPrefMemoryInfoDOList = vcloudClientAutoIosPrefMemoryInfoDAO.queryIOSPrefMemoryInfoDOByTaskId((int)taskId);
                int count = 0 ;
                double total_voltage = 0  ;
                double max_voltage = Long.MIN_VALUE ;
                double min_voltage = Long.MAX_VALUE ;
                double total_temperature = 0  ;
                double max_temperature = Long.MIN_VALUE ;
                double min_temperature = Long.MAX_VALUE ;
                double total_instantamperage = 0  ;
                double max_instantamperage = Long.MIN_VALUE ;
                double min_instantamperage = Long.MAX_VALUE ;
                double total_power = 0  ;
                double max_power = Long.MIN_VALUE ;
                double min_power = Long.MAX_VALUE ;
                double total_level = 0  ;
                double max_level = Long.MIN_VALUE ;
                double min_level = Long.MAX_VALUE ;

                for (VcloudClientAutoIosPrefInfoDO vcloudClientAutoIosPrefInfoDO : vcloudClientAutoIosPrefInfoDOList){
                    if(vcloudClientAutoIosPrefInfoDO==null){
                        continue;
                    }
                    count ++ ;
                    total_voltage += vcloudClientAutoIosPrefInfoDO.getVoltage() ;
                    if (max_voltage < vcloudClientAutoIosPrefInfoDO.getVoltage()) {
                        max_voltage = vcloudClientAutoIosPrefInfoDO.getVoltage() ;
                    }
                    if (min_voltage > vcloudClientAutoIosPrefInfoDO.getVoltage()) {
                        min_voltage = vcloudClientAutoIosPrefInfoDO.getVoltage() ;
                    }
                    total_temperature += vcloudClientAutoIosPrefInfoDO.getTemperature() ;
                    if (max_temperature < vcloudClientAutoIosPrefInfoDO.getTemperature()) {
                        max_temperature = vcloudClientAutoIosPrefInfoDO.getTemperature() ;
                    }
                    if (min_temperature > vcloudClientAutoIosPrefInfoDO.getTemperature()) {
                        min_temperature = vcloudClientAutoIosPrefInfoDO.getTemperature() ;
                    }
                    total_instantamperage += vcloudClientAutoIosPrefInfoDO.getInstantamperage() ;
                    if (max_instantamperage < vcloudClientAutoIosPrefInfoDO.getInstantamperage()) {
                        max_instantamperage = vcloudClientAutoIosPrefInfoDO.getInstantamperage() ;
                    }
                    if (min_instantamperage > vcloudClientAutoIosPrefInfoDO.getInstantamperage()) {
                        min_instantamperage = vcloudClientAutoIosPrefInfoDO.getInstantamperage() ;
                    }
                    total_power += vcloudClientAutoIosPrefInfoDO.getPower() ;
                    if (max_power < vcloudClientAutoIosPrefInfoDO.getPower()) {
                        max_power = vcloudClientAutoIosPrefInfoDO.getPower() ;
                    }
                    if (min_power > vcloudClientAutoIosPrefInfoDO.getPower()) {
                        min_power = vcloudClientAutoIosPrefInfoDO.getPower() ;
                    }
                    total_level += vcloudClientAutoIosPrefInfoDO.getLevel() ;
                    if (max_level < vcloudClientAutoIosPrefInfoDO.getLevel()) {
                        max_level = vcloudClientAutoIosPrefInfoDO.getLevel() ;
                    }
                    if (min_level > vcloudClientAutoIosPrefInfoDO.getLevel()) {
                        min_level = vcloudClientAutoIosPrefInfoDO.getLevel() ;
                    }
                }
                if (count > 0 ) {

                    PerfTestReportData voltagePerfTestReportData = new PerfTestReportData();
                    voltagePerfTestReportData.setCount(count);
                    voltagePerfTestReportData.setAvg(total_voltage / count);
                    voltagePerfTestReportData.setMax(max_voltage);
                    voltagePerfTestReportData.setMin(min_voltage);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getVoltage()!=null){
                        voltagePerfTestReportData.setBaseAvg(perfBaseLineDetailData.getVoltage().getBaseAvg());
                        voltagePerfTestReportData.setBaseMax(perfBaseLineDetailData.getVoltage().getBaseMax());
                        voltagePerfTestReportData.setBaseMin(perfBaseLineDetailData.getVoltage().getBaseMin());
                    }
                    perfReportResultDetailData.setVoltage(voltagePerfTestReportData);

                    PerfTestReportData temperaturePerfTestReportData = new PerfTestReportData();
                    temperaturePerfTestReportData.setCount(count);
                    temperaturePerfTestReportData.setAvg(total_temperature / count);
                    temperaturePerfTestReportData.setMax(max_temperature);
                    temperaturePerfTestReportData.setMin(min_temperature);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getTemperature()!=null){
                        temperaturePerfTestReportData.setBaseAvg(perfBaseLineDetailData.getTemperature().getBaseAvg());
                        temperaturePerfTestReportData.setBaseMax(perfBaseLineDetailData.getTemperature().getBaseMax());
                        temperaturePerfTestReportData.setBaseMin(perfBaseLineDetailData.getTemperature().getBaseMin());
                    }
                    perfReportResultDetailData.setTemperature(temperaturePerfTestReportData);

                    PerfTestReportData instantamperagePerfTestReportData = new PerfTestReportData();
                    instantamperagePerfTestReportData.setCount(count);
                    instantamperagePerfTestReportData.setAvg(total_instantamperage / count);
                    instantamperagePerfTestReportData.setMax(max_instantamperage);
                    instantamperagePerfTestReportData.setMin(min_instantamperage);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getInstantAmperage()!=null){
                        instantamperagePerfTestReportData.setBaseAvg(perfBaseLineDetailData.getInstantAmperage().getBaseAvg());
                        instantamperagePerfTestReportData.setBaseMax(perfBaseLineDetailData.getInstantAmperage().getBaseMax());
                        instantamperagePerfTestReportData.setBaseMin(perfBaseLineDetailData.getInstantAmperage().getBaseMin());
                    }
                    perfReportResultDetailData.setInstantAmperage(instantamperagePerfTestReportData);

                    PerfTestReportData powerPerfTestReportData = new PerfTestReportData();
                    powerPerfTestReportData.setCount(count);
                    powerPerfTestReportData.setAvg(total_power / count);
                    powerPerfTestReportData.setMax(max_power);
                    powerPerfTestReportData.setMin(min_power);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getPower()!=null){
                        powerPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getPower().getBaseAvg());
                        powerPerfTestReportData.setBaseMax(perfBaseLineDetailData.getPower().getBaseMax());
                        powerPerfTestReportData.setBaseMin(perfBaseLineDetailData.getPower().getBaseMin());
                    }
                    perfReportResultDetailData.setPower(powerPerfTestReportData);

                    PerfTestReportData levelPerfTestReportData = new PerfTestReportData();
                    levelPerfTestReportData.setCount(count);
                    levelPerfTestReportData.setAvg(total_level / count);
                    levelPerfTestReportData.setMax(max_level);
                    levelPerfTestReportData.setMin(min_level);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getLevel()!=null){
                        levelPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getLevel().getBaseAvg());
                        levelPerfTestReportData.setBaseMax(perfBaseLineDetailData.getLevel().getBaseMax());
                        levelPerfTestReportData.setBaseMin(perfBaseLineDetailData.getLevel().getBaseMin());
                    }
                    perfReportResultDetailData.setLevel(levelPerfTestReportData);
                }
                count = 0 ;
                double total_memory = 0  ;
                double max_memory = Long.MIN_VALUE ;
                double min_memory = Long.MAX_VALUE ;

                double total_appCpu = 0  ;
                double max_appCpu = Long.MIN_VALUE ;
                double min_appCpu = Long.MAX_VALUE ;

                double total_systemCpu = 0  ;
                double max_systemCpu = Long.MIN_VALUE ;
                double min_systemCpu = Long.MAX_VALUE ;
                for (VcloudClientAutoIosPrefMemoryInfoDO clientAutoIosPrefMemoryInfoDO : vcloudClientAutoIosPrefMemoryInfoDOList){
                    if(clientAutoIosPrefMemoryInfoDO==null){
                        continue;
                    }
                    count ++ ;
                    total_memory += clientAutoIosPrefMemoryInfoDO.getMemory() ;
                    if (max_memory < clientAutoIosPrefMemoryInfoDO.getMemory()) {
                        max_memory = clientAutoIosPrefMemoryInfoDO.getMemory() ;
                    }
                    if (min_memory > clientAutoIosPrefMemoryInfoDO.getMemory()) {
                        min_memory = clientAutoIosPrefMemoryInfoDO.getMemory() ;
                    }
                    total_appCpu += clientAutoIosPrefMemoryInfoDO.getAppCpu() ;
                    if (max_appCpu < clientAutoIosPrefMemoryInfoDO.getAppCpu()) {
                        max_appCpu = clientAutoIosPrefMemoryInfoDO.getAppCpu() ;
                    }
                    if (min_appCpu > clientAutoIosPrefMemoryInfoDO.getAppCpu()) {
                        min_appCpu = clientAutoIosPrefMemoryInfoDO.getAppCpu() ;
                    }
                    total_systemCpu += clientAutoIosPrefMemoryInfoDO.getSysCpu() ;
                    if (max_systemCpu < clientAutoIosPrefMemoryInfoDO.getSysCpu()) {
                        max_systemCpu = clientAutoIosPrefMemoryInfoDO.getSysCpu() ;
                    }
                    if (min_systemCpu > clientAutoIosPrefMemoryInfoDO.getSysCpu()) {
                        min_systemCpu = clientAutoIosPrefMemoryInfoDO.getSysCpu() ;
                    }

                }
                if (count > 0 ) {
                    PerfTestReportData memoryPerfTestReportData = new PerfTestReportData();
                    memoryPerfTestReportData.setCount(count);
                    memoryPerfTestReportData.setAvg(total_memory / count);
                    memoryPerfTestReportData.setMax(max_memory);
                    memoryPerfTestReportData.setMin(min_memory);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getMemory()!=null){
                        memoryPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getMemory().getBaseAvg());
                        memoryPerfTestReportData.setBaseMax(perfBaseLineDetailData.getMemory().getBaseMax());
                        memoryPerfTestReportData.setBaseMin(perfBaseLineDetailData.getMemory().getBaseMin());
                    }
                    perfReportResultDetailData.setMemory(memoryPerfTestReportData);

                    PerfTestReportData appCpuPerfTestReportData = new PerfTestReportData();
                    appCpuPerfTestReportData.setCount(count);
                    appCpuPerfTestReportData.setAvg(total_appCpu / count);
                    appCpuPerfTestReportData.setMax(max_appCpu);
                    appCpuPerfTestReportData.setMin(min_appCpu);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getAppCpu()!=null){
                        appCpuPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getAppCpu().getBaseAvg());
                        appCpuPerfTestReportData.setBaseMax(perfBaseLineDetailData.getAppCpu().getBaseMax());
                        appCpuPerfTestReportData.setBaseMin(perfBaseLineDetailData.getAppCpu().getBaseMin());
                    }
                    perfReportResultDetailData.setAppCpu(appCpuPerfTestReportData);

                    PerfTestReportData sysCpuPerfTestReportData = new PerfTestReportData();
                    sysCpuPerfTestReportData.setCount(count);
                    sysCpuPerfTestReportData.setAvg(total_systemCpu / count);
                    sysCpuPerfTestReportData.setMax(max_systemCpu);
                    sysCpuPerfTestReportData.setMin(min_systemCpu);
                    if (perfBaseLineDetailData!=null && perfBaseLineDetailData.getSysCpu()!=null){
                        sysCpuPerfTestReportData.setBaseAvg(perfBaseLineDetailData.getSysCpu().getBaseAvg());
                        sysCpuPerfTestReportData.setBaseMax(perfBaseLineDetailData.getSysCpu().getBaseMax());
                        sysCpuPerfTestReportData.setBaseMin(perfBaseLineDetailData.getSysCpu().getBaseMin());
                    }
                    perfReportResultDetailData.setSysCpu(sysCpuPerfTestReportData);
                }

            }else{
                continue;
            }
            //基线
            dataList.add(perfReportResultDetailData);
        }
        return perfReportResultData;
    }




    @Override
    public AutoPerfBaseReportResultDataInterface buildBaseLineByReport(String reportResultDataStr) {
        if (StringUtils.isBlank(reportResultDataStr)){
            return null ;
        }
        PerfReportResultData perfReportResultData = JSONObject.parseObject(reportResultDataStr,PerfReportResultData.class) ;
        if (perfReportResultData == null||CollectionUtils.isEmpty(perfReportResultData.getDataList())){
            return null ;
        }
        PerfReportResultData perfReportBaseListData = new PerfReportResultData() ;
        List<PerfReportResultDetailData> baseLineDataList = new ArrayList<PerfReportResultDetailData>() ;
        perfReportBaseListData.setDataList(baseLineDataList);
        for (PerfReportResultDetailData perfReportResultDetailData : perfReportResultData.getDataList()){
            if (perfReportResultDetailData == null){
                continue;
            }
            PerfReportResultDetailData baselineDetailData = new PerfReportResultDetailData() ;
            baselineDetailData.setTaskId(perfReportResultDetailData.getTaskId());
            baselineDetailData.setPlatform(perfReportResultDetailData.getPlatform());
            baselineDetailData.setDeviceInfo(perfReportResultDetailData.getDeviceInfo());
            if (perfReportResultDetailData.getAppCpu()!=null){
                PerfTestReportData perfTestReportData = new PerfTestReportData() ;
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getAppCpu().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getAppCpu().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getAppCpu().getMin());
                baselineDetailData.setAppCpu(perfTestReportData);
            }else {
                baselineDetailData.setAppCpu(new PerfTestReportData());
            }
            if (perfReportResultDetailData.getInstantAmperage()!=null) {
                PerfTestReportData perfTestReportData = new PerfTestReportData();
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getInstantAmperage().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getInstantAmperage().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getInstantAmperage().getMin());
                baselineDetailData.setInstantAmperage(perfTestReportData);
            }else{
                baselineDetailData.setInstantAmperage(new PerfTestReportData());
            }
            if (perfReportResultDetailData.getLevel()!=null){
                PerfTestReportData perfTestReportData = new PerfTestReportData() ;
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getLevel().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getLevel().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getLevel().getMin());
                baselineDetailData.setLevel(perfTestReportData);
            }else{
                baselineDetailData.setLevel(new PerfTestReportData());
            }
            if (perfReportResultDetailData.getMemory()!=null){
                PerfTestReportData perfTestReportData = new PerfTestReportData() ;
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getMemory().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getMemory().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getMemory().getMin());
                baselineDetailData.setMemory(perfTestReportData);
            }else{
                baselineDetailData.setMemory(new PerfTestReportData());
            }
            if (perfReportResultDetailData.getPower()!=null){
                PerfTestReportData perfTestReportData = new PerfTestReportData() ;
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getPower().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getPower().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getPower().getMin());
                baselineDetailData.setPower(perfTestReportData);
            }else{
                baselineDetailData.setPower(new PerfTestReportData());
            }
            if (perfReportResultDetailData.getTemperature()!=null) {
                PerfTestReportData perfTestReportData = new PerfTestReportData();
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getTemperature().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getTemperature().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getTemperature().getMin());
                baselineDetailData.setTemperature(perfTestReportData);
            }else{
                baselineDetailData.setTemperature(new PerfTestReportData());
            }
            if (perfReportResultDetailData.getSysCpu()!=null){
                PerfTestReportData perfTestReportData = new PerfTestReportData() ;
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getSysCpu().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getSysCpu().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getSysCpu().getMin());
                baselineDetailData.setSysCpu(perfTestReportData);
            }else{
                baselineDetailData.setSysCpu(new PerfTestReportData() );
            }
            if (perfReportResultDetailData.getVoltage()!=null){
                PerfTestReportData perfTestReportData = new PerfTestReportData() ;
                perfTestReportData.setBaseAvg(perfReportResultDetailData.getVoltage().getAvg());
                perfTestReportData.setBaseMax(perfReportResultDetailData.getVoltage().getMax());
                perfTestReportData.setBaseMin(perfReportResultDetailData.getVoltage().getMin());
                baselineDetailData.setVoltage(perfTestReportData);
            }else {
                baselineDetailData.setVoltage(new PerfTestReportData() );
            }
            baseLineDataList.add(baselineDetailData);
        }
        return perfReportBaseListData;
    }

    @Override
    public AutoPerfBaseReportResultDataInterface buildResultVO(String resultDataStr) {
        if (StringUtils.isBlank(resultDataStr)){
            return null ;
        }
        PerfReportResultData perfReportResultData = JSONObject.parseObject(resultDataStr,PerfReportResultData.class) ;
        return perfReportResultData;
    }
}
