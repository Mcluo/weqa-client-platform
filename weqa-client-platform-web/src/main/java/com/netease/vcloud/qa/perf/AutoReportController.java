package com.netease.vcloud.qa.perf;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.EmailService;
import com.netease.vcloud.qa.service.perf.AutoPerfReportService;
import com.netease.vcloud.qa.service.perf.data.PerfAndroidDataDTO;
import com.netease.vcloud.qa.service.perf.data.PerfIOSDataDTO;
import com.netease.vcloud.qa.service.perf.data.PerfIOSMemoryDataDTO;
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
    private EmailService emailService;

    @Autowired
    private AutoPerfReportService autoPerfReportService;

    @RequestMapping("/test11")
    @ResponseBody
    public ResultVO test11() {
        String text= "<a href=\"http://weqa.netease.com/#/client-g2/detail/1448\">自动化测试已完成，点击查看详情!</a>";
        emailService.sendHtmlMail("yeguo@corp.netease.com","Rtc自动化测试" , text);
        return ResultUtils.build(true);
    }
    @RequestMapping("/addIosMemoryInfo")
    @ResponseBody
    public ResultVO addIosMemoryInfo(@RequestBody JSONObject jsonObject ) {
        List<PerfIOSMemoryDataDTO> list = jsonObject.getJSONArray("listData").toJavaList(PerfIOSMemoryDataDTO.class);
        Integer taskId = autoPerfReportService.getPerfTaskIdByAutoTask(jsonObject.getLong("taskId"));
//        for(VcloudClientAutoIosPrefMemoryInfoDO clientAutoIosPrefMemoryInfoDO : list){
//            clientAutoIosPrefMemoryInfoDO.setTaskid(taskId);
//            autoPerfReportService.insertIosMemoryInfo(clientAutoIosPrefMemoryInfoDO);
//        }

        boolean flag = autoPerfReportService.patchInsertIosMemoryInfo(taskId,list);
        return ResultUtils.build(flag);
    }

    @RequestMapping("/addIosPowerInfo")
    @ResponseBody
    public ResultVO addIosPowerInfo(@RequestBody JSONObject jsonObject ) {
        List<PerfIOSDataDTO> list =  jsonObject.getJSONArray("listData").toJavaList(PerfIOSDataDTO.class);
        Integer taskId = autoPerfReportService.getPerfTaskIdByAutoTask(jsonObject.getLong("taskId"));
//        for(VcloudClientAutoIosPrefInfoDO clientAutoIosPrefInfoDO : list){
//            clientAutoIosPrefInfoDO.setTaskid(taskId);
//            autoPerfReportService.insertIosInfo(clientAutoIosPrefInfoDO);
//        }
        boolean flag = autoPerfReportService.patchInsertIosInfo(taskId,list);
        return ResultUtils.build(flag);
    }

    @RequestMapping("/addAndroidInfo")
    @ResponseBody
    public ResultVO addAndroidInfo(@RequestBody JSONObject jsonObject ) {
        List<PerfAndroidDataDTO> perfAndroidDataUploadDTOList =  jsonObject.getJSONArray("listData").toJavaList(PerfAndroidDataDTO.class);
        Integer taskId = autoPerfReportService.getPerfTaskIdByAutoTask(jsonObject.getLong("taskId"));
//        for (VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO: list){
//            clientAutoAndroidPrefInfoDO.setTaskid(taskId);
//            autoPerfReportService.insertAndroidInfo(clientAutoAndroidPrefInfoDO);
//        }
        boolean flag = autoPerfReportService.patchInsertAndroidInfo(taskId,perfAndroidDataUploadDTOList);
        return ResultUtils.build(flag);
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

