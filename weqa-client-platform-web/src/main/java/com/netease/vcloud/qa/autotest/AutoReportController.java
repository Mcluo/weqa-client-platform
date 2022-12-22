package com.netease.vcloud.qa.autotest;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.model.VcloudClientAutoAndroidPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefMemoryInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoPerfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 17:16
 */
@RestController
@RequestMapping("/perf/report")
public class AutoReportController {

    @Autowired
    private AutoPerfService autoPerfService;
    @RequestMapping("/addIosMemoryInfo")
    @ResponseBody
    public ResultVO addIosMemoryInfo(
            @RequestParam(name = "memory") float memory,
            @RequestParam(name = "appCpu") float appCpu,
            @RequestParam(name = "times") Integer times,
            @RequestParam(name = "taskid") Integer taskid,
            @RequestParam(name = "sysCpu") float sysCpu) {
        VcloudClientAutoIosPrefMemoryInfoDO clientAutoIosPrefMemoryInfoDO = new VcloudClientAutoIosPrefMemoryInfoDO();
        clientAutoIosPrefMemoryInfoDO.setMemory(memory);
        clientAutoIosPrefMemoryInfoDO.setSysCpu(sysCpu);
        clientAutoIosPrefMemoryInfoDO.setAppCpu(appCpu);
        clientAutoIosPrefMemoryInfoDO.setTimes(times);
        clientAutoIosPrefMemoryInfoDO.setTaskid(taskid);
        autoPerfService.insertIosMemoryInfo(clientAutoIosPrefMemoryInfoDO);
        return ResultUtils.build(true);
    }

    @RequestMapping("/addIosPowerInfo")
    @ResponseBody
    public ResultVO addIosPowerInfo(
            @RequestParam(name = "voltage") float voltage,
            @RequestParam(name = "temperature") float temperature,
            @RequestParam(name = "instantAmperage") float instantAmperage,
            @RequestParam(name = "power") float power,
            @RequestParam(name = "level") float level,
            @RequestParam(name = "times") Integer times,
            @RequestParam(name = "taskId") Integer taskId) {
        VcloudClientAutoIosPrefInfoDO clientAutoIosPrefInfoDO = new VcloudClientAutoIosPrefInfoDO();
        clientAutoIosPrefInfoDO.setPower(power);
        clientAutoIosPrefInfoDO.setInstantamperage(instantAmperage);
        clientAutoIosPrefInfoDO.setLevel(level);
        clientAutoIosPrefInfoDO.setTemperature(temperature);
        clientAutoIosPrefInfoDO.setVoltage(voltage);
        clientAutoIosPrefInfoDO.setTimes(times);
        clientAutoIosPrefInfoDO.setTaskid(taskId);
        autoPerfService.insertIosInfo(clientAutoIosPrefInfoDO);
        return ResultUtils.build(true);
    }

    @RequestMapping("/addAndroidInfo")
    @ResponseBody
    public ResultVO addAndroidInfo(
            @RequestParam(name = "voltage") float voltage,
            @RequestParam(name = "temperature") float temperature,
            @RequestParam(name = "instantAmperage") float instantAmperage,
            @RequestParam(name = "power") float power,
            @RequestParam(name = "level") float level,
            @RequestParam(name = "times") Integer times,
            @RequestParam(name = "taskId") Integer taskId,
            @RequestParam(name = "memory") float memory) {
        VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO = new VcloudClientAutoAndroidPrefInfoDO();
        clientAutoAndroidPrefInfoDO.setInstantamperage(instantAmperage);
        clientAutoAndroidPrefInfoDO.setPower(power);
        clientAutoAndroidPrefInfoDO.setTaskid(taskId);
        clientAutoAndroidPrefInfoDO.setTemperature(temperature);
        clientAutoAndroidPrefInfoDO.setMemory(memory);
        clientAutoAndroidPrefInfoDO.setLevel(level);
        clientAutoAndroidPrefInfoDO.setTimes(times);
        clientAutoAndroidPrefInfoDO.setVoltage(voltage);
        autoPerfService.insertAndroidInfo(clientAutoAndroidPrefInfoDO);
        return ResultUtils.build(true);
    }

    @RequestMapping("/createReportTask")
    @ResponseBody
    public ResultVO createReportTask(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "cpuInfo") String cpuInfo,
            @RequestParam(name = "devicesModel") String devicesModel,
            @RequestParam(name = "devicesVersion") String devicesVersion,
            @RequestParam(name = "devicesPlatform") String devicesPlatform,
            @RequestParam(name = "sdkInfo") String sdkInfo,
            @RequestParam(name = "sdkVersion") String sdkVersion,
            @RequestParam(name = "user") String user) {
        VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO = new VcloudClientAutoPerfTaskDO();
        clientAutoPerfTaskDO.setName(name);
        clientAutoPerfTaskDO.setCpuinfo(cpuInfo);
        clientAutoPerfTaskDO.setDevicesmodel(devicesModel);
        clientAutoPerfTaskDO.setDevicesversion(devicesVersion);
        clientAutoPerfTaskDO.setDevicesplatform(devicesPlatform);
        clientAutoPerfTaskDO.setSdkinfo(sdkInfo);
        clientAutoPerfTaskDO.setSdkversion(sdkVersion);
        clientAutoPerfTaskDO.setUser(user);
        int taskId = autoPerfService.insertPerfTask(clientAutoPerfTaskDO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", clientAutoPerfTaskDO.getId());
        return ResultUtils.build(true,jsonObject);
    }

}

