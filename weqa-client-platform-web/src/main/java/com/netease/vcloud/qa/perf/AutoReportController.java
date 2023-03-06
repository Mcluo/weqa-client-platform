package com.netease.vcloud.qa.perf;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.model.VcloudClientAutoAndroidPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefMemoryInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.perf.AutoPerfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 17:16
 */
@RestController
@RequestMapping("/perf/report")
public class AutoReportController {

    @Autowired
    private AutoPerfReportService autoPerfReportService;
    @RequestMapping("/addIosMemoryInfo")
    @ResponseBody
    public ResultVO addIosMemoryInfo(@RequestBody JSONObject jsonObject ) {
        List<VcloudClientAutoIosPrefMemoryInfoDO> list =  jsonObject.getJSONArray("listData").toJavaList(VcloudClientAutoIosPrefMemoryInfoDO.class);
        for(VcloudClientAutoIosPrefMemoryInfoDO clientAutoIosPrefMemoryInfoDO : list){
            clientAutoIosPrefMemoryInfoDO.setTaskid(jsonObject.getIntValue("taskId"));
            autoPerfReportService.insertIosMemoryInfo(clientAutoIosPrefMemoryInfoDO);
        }
        return ResultUtils.build(true);
    }

    @RequestMapping("/addIosPowerInfo")
    @ResponseBody
    public ResultVO addIosPowerInfo(@RequestBody JSONObject jsonObject ) {
        List<VcloudClientAutoIosPrefInfoDO> list =  jsonObject.getJSONArray("listData").toJavaList(VcloudClientAutoIosPrefInfoDO.class);
        for(VcloudClientAutoIosPrefInfoDO clientAutoIosPrefInfoDO : list){
            clientAutoIosPrefInfoDO.setTaskid(jsonObject.getIntValue("taskId"));
            autoPerfReportService.insertIosInfo(clientAutoIosPrefInfoDO);
        }
        return ResultUtils.build(true);
    }

    @RequestMapping("/addAndroidInfo")
    @ResponseBody
    public ResultVO addAndroidInfo(@RequestBody JSONObject jsonObject ) {
        List<VcloudClientAutoAndroidPrefInfoDO> list =  jsonObject.getJSONArray("listData").toJavaList(VcloudClientAutoAndroidPrefInfoDO.class);
        for (VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO: list){
            clientAutoAndroidPrefInfoDO.setTaskid(jsonObject.getIntValue("taskId"));
            autoPerfReportService.insertAndroidInfo(clientAutoAndroidPrefInfoDO);
        }
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
        autoPerfReportService.insertPerfTask(clientAutoPerfTaskDO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", clientAutoPerfTaskDO.getId());
        return ResultUtils.build(true,jsonObject);
    }

}

