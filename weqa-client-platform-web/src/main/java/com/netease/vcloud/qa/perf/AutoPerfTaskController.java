package com.netease.vcloud.qa.perf;

import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.perf.AutoPerfResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/6 20:59
 */
@RestController
@RequestMapping("/perf/task")
public class AutoPerfTaskController {

    @Autowired
    private AutoPerfResultService autoPerfResultService ;

    @RequestMapping("list")
    @ResponseBody
    public ResultVO getPerfTaskList(@RequestParam(name = "current",required = false,defaultValue = "0") int current , @RequestParam(name = "size",required = false,defaultValue = "20") int size){
        ResultVO resultVO = null ;

        return resultVO ;
    }

}
