package com.netease.vcloud.qa.perf;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import com.netease.vcloud.qa.service.perf.data.PerfBaseLineDTO;
import com.netease.vcloud.qa.service.perf.data.PerfReportDataDTO;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseLineService;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseReportService;
import com.netease.vcloud.qa.service.perf.view.*;
import com.offbytwo.jenkins.client.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * http://127.0.0.1:8788/g2-client/perf/base/report/create?name=report&type=firstFrame&task=1&owner=luqiuwei
     * http://127.0.0.1:8788/g2-client/perf/base/report/create?name=report&type=normal&task=51&owner=system&base=9
     * @param name
     * @param type
     * @param taskList
     * @param baseLine
     * @param owner
     * @return
     */
    @RequestMapping("/report/create")
    @ResponseBody
    public ResultVO createPerfReport(@RequestParam(name = "name") String name ,
                                     @RequestParam(name = "type") String type,
                                     @RequestParam(name = "task") List<Long> taskList,
                                     @RequestParam(name = "base" ,required = false) Long baseLine ,
                                     @RequestParam(name = "owner") String owner){
        PerfReportDataDTO perfReportDataDTO = new PerfReportDataDTO() ;
        perfReportDataDTO.setName(name);
        perfReportDataDTO.setType(type);
        perfReportDataDTO.setTaskList(taskList);
        perfReportDataDTO.setBaseLine(baseLine);
        perfReportDataDTO.setOwner(owner);
        ResultVO resultVO = null ;
        try {
            Long id = autoPerfBaseReportService.createNewPerfReport(perfReportDataDTO);
            if (id == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(id) ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/perf/base/baseline/create?name=baseline&report=1&owner=system
     * http://127.0.0.1:8788/g2-client/perf/base/baseline/create?name=baseline&report=8&owner=system
     * @param name
     * @param reportId
     * @param owner
     * @return
     */
    @RequestMapping("/baseline/create")
    @ResponseBody
    public ResultVO createNewPerfBaseLine(@RequestParam(name = "name")String name ,
                                          @RequestParam(name = "report") Long reportId ,
                                          @RequestParam(name = "owner") String owner ){
        PerfBaseLineDTO perfBaseLineDTO = new PerfBaseLineDTO() ;
        perfBaseLineDTO.setName(name);
        perfBaseLineDTO.setReportId(reportId);
        perfBaseLineDTO.setOwner(owner);
        ResultVO resultVO = null ;
        try {
            Long id = autoPerfBaseLineService.insertNewPerfBaseLine(perfBaseLineDTO);
            if (id == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(id) ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/perf/base/report/get?id=4
     * @param id
     * @return
     */
    @RequestMapping("/report/get")
    @ResponseBody
    public ResultVO getPerfReport(@RequestParam(name = "id") Long id ){
        ResultVO resultVO = null ;
        try {
            PerfBaseReportDetailVO perfBaseReportDetailVO = autoPerfBaseReportService.getPerfBaseReportDetail(id);
            if (perfBaseReportDetailVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(perfBaseReportDetailVO) ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/perf/base/baseline/get?id=4
     * @param id
     * @return
     */
    @RequestMapping("/baseLine/get")
    @ResponseBody
    public ResultVO getBaseLine(@RequestParam(name = "id") Long id ){
        ResultVO resultVO = null ;
        try {
            PerfBaseLineDetailVO perfBaseLineDetailVO = autoPerfBaseLineService.getPerfBaselineDetail(id);
            if (perfBaseLineDetailVO == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(perfBaseLineDetailVO) ;
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }
}
