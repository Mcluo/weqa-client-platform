package com.netease.vcloud.qa.autotest;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.AutoTestPrivateAddressService;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.view.PrivateAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/1 21:31
 */
@RestController
@RequestMapping("/auto/private")
public class AutoPrivateAddressController {

    @Autowired
    private AutoTestPrivateAddressService autoTestPrivateAddressService ;

    /**
     *
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO queryAllPrivateAddress() {
        ResultVO resultVO = null;
        List<PrivateAddressVO> privateAddressVOList = autoTestPrivateAddressService.queryPrivateAddressList();
        if (privateAddressVOList != null) {
            resultVO = ResultUtils.buildSuccess(privateAddressVOList);
        } else {
            resultVO = ResultUtils.buildFail();
        }
        return resultVO;
    }

    /**
     *
     * @param name
     * @param config
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO  addNewPrivateAddress(@RequestParam("name") String name ,@RequestParam("config") String config){
        ResultVO resultVO = null;
        try {
            boolean flag = autoTestPrivateAddressService.insertNewClientPrivateAddressInfo(name, config);
            if (flag){
                resultVO = ResultUtils.buildSuccess() ;
            }else {
                resultVO = ResultUtils.buildFail() ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO deletePrivateAddress(@RequestParam("id") Long id) {
        ResultVO resultVO = null;

        boolean flag = autoTestPrivateAddressService.deleteClientPrivateAddressById(id);
        if (flag) {
            resultVO = ResultUtils.buildSuccess();
        } else {
            resultVO = ResultUtils.buildFail();
        }
        return resultVO;
    }

}
