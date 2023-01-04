package com.netease.vcloud.qa.autotest;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.AutoTestDeviceService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 17:16
 */
@RestController
@RequestMapping("/auto/device")
public class AutoTestDeviceController {

    @Autowired
    private AutoTestDeviceService autoTestDeviceService ;

    /**
     * http://127.0.0.1:8788/g2-client/auto/device/get
     * 获取可用设备列表
     * @return
     */
    // TODO: 2023/1/3 增加获取远端设备和本地设备的区别
    @RequestMapping("/get")
    @ResponseBody
    public ResultVO getAllDevice(@RequestParam(name = "user" ,required = false) String useInfo,
                                 @RequestParam(name = "type" , required = false,defaultValue = "0") byte deviceType){
        ResultVO resultVO = null ;
        try {
            List<DeviceInfoVO> deviceInfoVOList = autoTestDeviceService.getDeviceList(useInfo,deviceType);
            if (deviceInfoVOList!=null){
                resultVO = ResultUtils.buildSuccess(deviceInfoVOList) ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/auto/device/add?ip=127.0.0.1&port=5001&platform=pc
     * @param ip
     * @param port
     * @param platform
     * @param userId
     * @param cpu
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO addNewDevice(@RequestParam("ip") String ip ,
                                 @RequestParam("port") Integer port ,
                                 @RequestParam("platform") String platform ,
                                 @RequestParam(name = "user" ,required = false) String userId ,
                                 @RequestParam(name = "cpu" ,required = false) String cpu,
                                 @RequestParam(name = "owner", required = false,defaultValue = "system") String owner){
        ResultVO resultVO = null ;
        try {
            boolean flag = autoTestDeviceService.addNewDeviceInfo(ip, port, platform, userId, cpu,owner);
            resultVO = ResultUtils.build(flag) ;
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

    /**
     *http://127.0.0.1:8788/g2-client/auto/device/update?id=1&ip=127.0.0.1&port=5001&platform=pc&user=44944
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultVO updateDeviceInfo(@RequestParam("id") Long id ,
                                     @RequestParam("ip") String ip ,
                                     @RequestParam("port") Integer port ,
                                     @RequestParam("platform") String platform ,
                                     @RequestParam(name = "user" ,required = false) String userId ,
                                     @RequestParam(name = "cpu" ,required = false) String cpu,
                                     @RequestParam(name = "owner", required = false,defaultValue = "system") String owner){
        ResultVO resultVO = null ;
        try{
            boolean flag = autoTestDeviceService.updateDeviceInfo(id,ip, port, platform, userId, cpu,owner);
            resultVO = ResultUtils.build(flag) ;
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getExceptionInfo()) ;
        }
        return resultVO ;
    }

}
