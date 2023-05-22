package com.netease.vcloud.qa.perf;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseLineService;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseReportService;
import com.netease.vcloud.qa.service.perf.view.PerfBaseLineListVO;
import com.netease.vcloud.qa.service.perf.view.PerfBasePerfTaskInfoVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportListVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportVO;
import com.offbytwo.jenkins.client.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/22 11:16
 */
@RestController
@RequestMapping("/perf/base")
public class AutoPerfBaseReportController {

    @Autowired
    private AutoPerfBaseReportService autoPerfBaseReportService ;

    @Autowired
    private AutoPerfBaseLineService autoPerfBaseLineService ;

    /**
     *  http://127.0.0.1:8788/g2-client/perf/base/task/query?type=firstFrame&key=测试
     * @param key
     * @param type
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/task/query")
    @ResponseBody
    public ResultVO queryBasePerfTask(@RequestParam(name = "key",required = false,defaultValue = "")String key,
                                      @RequestParam(name =  "type",required = false,defaultValue = "normal") String type,
                                      @RequestParam(name = "current",required = false,defaultValue = "1")int current,
                                      @RequestParam(name = "size",required = false,defaultValue = "10")int size){
        ResultVO resultVO = null ;
        AutoPerfType autoPerfType = AutoPerfType.getAutoPerfTypeByName(type) ;
        if (autoPerfType == null){
            resultVO = ResultUtils.buildFail()  ;
            return resultVO ;
        }
        try {
            List<PerfBasePerfTaskInfoVO> perfBasePerfTaskInfoVOList = autoPerfBaseReportService.getBasePerfTask(autoPerfType, key, current, size);
            if (perfBasePerfTaskInfoVOList != null) {
                resultVO = ResultUtils.buildSuccess(perfBasePerfTaskInfoVOList);
            } else {
                resultVO = ResultUtils.buildFail();
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     *http://127.0.0.1:8788/g2-client/perf/base/report/query?type=firstFrame
     * @param type
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/report/query")
    @ResponseBody
    public ResultVO queryPerfBaseReportList(@RequestParam(name = "type",required = false,defaultValue = "normal") String type,
                                            @RequestParam(name = "current",required = false,defaultValue = "1")int current,
                                            @RequestParam(name = "size",required = false,defaultValue = "10")int size){
        ResultVO resultVO = null ;
        AutoPerfType autoPerfType = AutoPerfType.getAutoPerfTypeByName(type) ;
        if (autoPerfType == null){
            resultVO = ResultUtils.buildFail()  ;
            return resultVO ;
        }
        try{
            PerfBaseReportListVO perfBaseReportListVO = autoPerfBaseReportService.getPerfBaseReportList(autoPerfType,current,size) ;
            if (perfBaseReportListVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else{
                resultVO = ResultUtils.buildSuccess(perfBaseReportListVO) ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/perf/base/baseline/query?type=firstFrame
     * @param type
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/baseline/query")
    @ResponseBody
    public ResultVO queryPerfBaseLineList(@RequestParam(name = "type",required = false,defaultValue = "normal") String type,
                                            @RequestParam(name = "current",required = false,defaultValue = "1")int current,
                                            @RequestParam(name = "size",required = false,defaultValue = "10")int size){
        ResultVO resultVO = null ;
        AutoPerfType autoPerfType = AutoPerfType.getAutoPerfTypeByName(type) ;
        if (autoPerfType == null){
            resultVO = ResultUtils.buildFail()  ;
            return resultVO ;
        }
        try{
            PerfBaseLineListVO perfBaseLineListVO = autoPerfBaseLineService.getPerfBaseLineList(autoPerfType,current,size) ;
            if (perfBaseLineListVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else{
                resultVO = ResultUtils.buildSuccess(perfBaseLineListVO) ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

}
