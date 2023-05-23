package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.DevicePlatform;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.dao.*;
import com.netease.vcloud.qa.model.*;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.perf.data.AutoPerfBaseReportResultDataInterface;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseReportInterface;
import com.netease.vcloud.qa.service.perf.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 21:04
 */
@Service
public class AutoPerfResultService implements AutoPerfBaseReportInterface {

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
        for (VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO : vcloudClientAutoAndroidPrefInfoDOList) {
            if (clientAutoAndroidPrefInfoDO == null){
                continue;
            }
            PerfTaskAndroidDetailListVO perfTaskAndroidDetailListVO = new PerfTaskAndroidDetailListVO() ;
            perfTaskAndroidDetailListVO.setTimes(clientAutoAndroidPrefInfoDO.getTimes());
            perfTaskAndroidDetailListVO.setMemory(clientAutoAndroidPrefInfoDO.getMemory());
            memoryStatistic.add(clientAutoAndroidPrefInfoDO.getMemory());
            perfTaskAndroidDetailListVO.setTemperature(clientAutoAndroidPrefInfoDO.getTemperature());
            temperatureStatistic.add(clientAutoAndroidPrefInfoDO.getTemperature());
            perfTaskAndroidDetailListVO.setVoltage(clientAutoAndroidPrefInfoDO.getVoltage());
            voltageStatistic.add(clientAutoAndroidPrefInfoDO.getVoltage());
            perfTaskAndroidDetailListVO.setInstantAmperage(clientAutoAndroidPrefInfoDO.getInstantamperage());
            instantAmperageStatistic.add(clientAutoAndroidPrefInfoDO.getInstantamperage());
            perfTaskAndroidDetailListVO.setPower(clientAutoAndroidPrefInfoDO.getPower());
            powerStatistic.add(clientAutoAndroidPrefInfoDO.getPower());
            perfTaskAndroidDetailListVO.setLevel(clientAutoAndroidPrefInfoDO.getLevel());
            levelStatistic.add(clientAutoAndroidPrefInfoDO.getLevel());
            detailList.add(perfTaskAndroidDetailListVO);
        }
        perfTaskAndroidDetailVO.setMemory(memoryStatistic.build());
        perfTaskAndroidDetailVO.setTemperature(temperatureStatistic.build());
        perfTaskAndroidDetailVO.setVoltage(voltageStatistic.build());
        perfTaskAndroidDetailVO.setInstantAmperage(instantAmperageStatistic.build());
        perfTaskAndroidDetailVO.setPower(powerStatistic.build());
        perfTaskAndroidDetailVO.setLevel(levelStatistic.build());
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
        if (!CollectionUtils.isEmpty(autoIosPrefInfoDOList)){
            for (VcloudClientAutoIosPrefInfoDO vcloudClientAutoIosPrefInfoDO : autoIosPrefInfoDOList){
                if (vcloudClientAutoIosPrefInfoDO == null){
                    continue;
                }
                PerfTaskIOSDetailListVO perfTaskIOSDetailListVO = new PerfTaskIOSDetailListVO() ;
                perfTaskIOSDetailListVO.setInstantAmperage(vcloudClientAutoIosPrefInfoDO.getInstantamperage());
                instantAmperageStatistic.add(vcloudClientAutoIosPrefInfoDO.getInstantamperage());
                perfTaskIOSDetailListVO.setLevel(vcloudClientAutoIosPrefInfoDO.getLevel());
                levelStatistic.add(vcloudClientAutoIosPrefInfoDO.getLevel());
                perfTaskIOSDetailListVO.setTemperature(vcloudClientAutoIosPrefInfoDO.getTemperature());
                temperatureStatistic.add(vcloudClientAutoIosPrefInfoDO.getTemperature());
                perfTaskIOSDetailListVO.setPower(vcloudClientAutoIosPrefInfoDO.getPower());
                powerStatistic.add(vcloudClientAutoIosPrefInfoDO.getPower());
                perfTaskIOSDetailListVO.setVoltage(vcloudClientAutoIosPrefInfoDO.getVoltage());
                voltageStatistic.add(vcloudClientAutoIosPrefInfoDO.getVoltage());
                perfTaskIOSDetailListVO.setTimes(vcloudClientAutoIosPrefInfoDO.getTimes());
                perfTaskIOSDetailListVOList.add(perfTaskIOSDetailListVO) ;
            }
        }
        if (!CollectionUtils.isEmpty(autoIosPrefMemoryInfoDOList)){
            for (VcloudClientAutoIosPrefMemoryInfoDO vcloudClientAutoIosPrefMemoryInfoDO : autoIosPrefMemoryInfoDOList){
                if (vcloudClientAutoIosPrefMemoryInfoDO == null){
                    continue;
                }
                PerfTaskIOSDetailMemoryListVO perfTaskIOSDetailMemoryListVO = new PerfTaskIOSDetailMemoryListVO() ;
                perfTaskIOSDetailMemoryListVO.setTimes(vcloudClientAutoIosPrefMemoryInfoDO.getTimes());
                perfTaskIOSDetailMemoryListVO.setMemory(vcloudClientAutoIosPrefMemoryInfoDO.getMemory());
                memoryStatistic.add(vcloudClientAutoIosPrefMemoryInfoDO.getMemory());
                perfTaskIOSDetailMemoryListVO.setAppCpu(vcloudClientAutoIosPrefMemoryInfoDO.getAppCpu());
                userCpuStatistic.add(vcloudClientAutoIosPrefMemoryInfoDO.getAppCpu());
                perfTaskIOSDetailMemoryListVO.setSysCpu(vcloudClientAutoIosPrefMemoryInfoDO.getSysCpu());
                sysCpuStatistic.add(vcloudClientAutoIosPrefMemoryInfoDO.getSysCpu());
                perfTaskIOSDetailMemoryListVOList.add(perfTaskIOSDetailMemoryListVO);
            }
        }
        if (CollectionUtils.isEmpty(autoIosPrefInfoDOList) && CollectionUtils.isEmpty(autoIosPrefMemoryInfoDOList)){
            return perfTaskIOSDetailVO;
        }
        perfTaskIOSDetailVO.setAppCPU(userCpuStatistic.build());
        perfTaskIOSDetailVO.setSysCPU(sysCpuStatistic.build());
        perfTaskIOSDetailVO.setMemory(memoryStatistic.build());
        perfTaskIOSDetailVO.setTemperature(temperatureStatistic.build());
        perfTaskIOSDetailVO.setVoltage(voltageStatistic.build());
        perfTaskIOSDetailVO.setInstantAmperage(instantAmperageStatistic.build());
        perfTaskIOSDetailVO.setPower(powerStatistic.build());
        perfTaskIOSDetailVO.setLevel(levelStatistic.build());
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
        return null;
    }


    @Override
    public AutoPerfBaseReportResultDataInterface buildBaseLineByReport(String reportResultDataStr) {
        return null;
    }

    @Override
    public AutoPerfBaseReportResultDataInterface buildResultVO(String resultDataStr) {
        return null;
    }
}
