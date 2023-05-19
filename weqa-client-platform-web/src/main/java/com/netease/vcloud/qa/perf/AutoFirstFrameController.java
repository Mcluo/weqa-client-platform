package com.netease.vcloud.qa.perf;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.perf.AutoPerfFirstFrameService;
import com.netease.vcloud.qa.service.perf.data.FirstFrameDataDTO;
import com.netease.vcloud.qa.service.perf.data.FirstFrameTaskDTO;
import com.netease.vcloud.qa.service.perf.view.FirstFrameDetailInfoVO;
import com.netease.vcloud.qa.service.perf.view.FirstFrameListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首帧相关接口
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/10 14:21
 */
@RestController
@RequestMapping("/perf/first")
public class AutoFirstFrameController {

    @Autowired
    private AutoPerfFirstFrameService autoPerfFirstFrameService ;


    /**
     * http://127.0.0.1:8788/g2-client/perf/first/task/create?name=test&device=XXX&operator=luqiuwei@corp.netease.com
     * 创建任务
     * @param name
     * @param deviceInfo
     * @param operator
     * @return
     */
    @RequestMapping("/task/create")
    @ResponseBody
    public ResultVO createFirstFrameTask(@RequestParam("name") String name ,
                                         @RequestParam("device") String deviceInfo,
                                         @RequestParam("operator") String operator,
                                         @RequestParam(name = "suit",required = false) Long suitId,
                                         @RequestParam(name = "gitInfo" ,required = false) String gitInfo,
                                         @RequestParam(name = "gitBranch",required = false) String gitBranch,
                                         @RequestParam(name = "deviceList",required = false) List<Long> deviceList){
        ResultVO resultVO = null ;
        FirstFrameTaskDTO firstFrameTaskDTO = new FirstFrameTaskDTO() ;
        firstFrameTaskDTO.setTaskName(name);
        firstFrameTaskDTO.setDeviceInfo(deviceInfo);
        firstFrameTaskDTO.setOperator(operator);
        firstFrameTaskDTO.setSuitId(suitId);
        firstFrameTaskDTO.setGitInfo(gitInfo);
        firstFrameTaskDTO.setGitBranch(gitBranch);
        firstFrameTaskDTO.setDeviceList(deviceList);
        try {
            Long id = autoPerfFirstFrameService.createNewFirstFrame(firstFrameTaskDTO);
            if (id != null) {
                resultVO = ResultUtils.buildSuccess(id);
            } else {
                resultVO = ResultUtils.buildFail();
            }
        }catch (AutoTestRunException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     *  http://127.0.0.1:8788/g2-client/perf/first/task/query?current=1&size=10
     * @param current
     * @param size
     * @return
     */
    @RequestMapping("/task/query")
    @ResponseBody
    public ResultVO queryFirstFrameTaskList(@RequestParam(name="current",required = false,defaultValue = "1") int current ,
                                            @RequestParam(name="size",required = false,defaultValue = "10") int size,
                                            @RequestParam(name = "operator",required = false)String operator){
        ResultVO resultVO = null ;
        FirstFrameListVO firstFrameListVO = autoPerfFirstFrameService.queryFirstFrame(operator , current, size) ;
        if (firstFrameListVO != null){
            resultVO = ResultUtils.buildSuccess(firstFrameListVO) ;
        }else{
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }

    /**
     *  http://127.0.0.1:8788/g2-client/perf/first/task/get?id=3
     * @param id
     * @return
     */
    @RequestMapping("/task/get")
    @ResponseBody
    public ResultVO getFirstFrameTaskDetail(@RequestParam(name="id") Long id){
        ResultVO resultVO = null ;
        FirstFrameDetailInfoVO firstFrameDetailInfoVO = autoPerfFirstFrameService.getFirstFrameDetailInfoVO(id) ;
        if (firstFrameDetailInfoVO != null){
            resultVO = ResultUtils.buildSuccess(firstFrameDetailInfoVO) ;
        }else{
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


    /**
     * http://127.0.0.1:8788/g2-client/perf/first/data/upload?data=%7B%22taskId%22%3A3%2C%22device%22%3A%22iPhone9%2C1%22%2C%22type%22%3A%22log%22%2C%22data%22%3A%5B699%2C478%2C757%2C536%2C502%2C470%5D%7D
     *上报任务数据
     * @return
     */
    @RequestMapping("/data/upload")
    @ResponseBody
    public ResultVO uploadFirstFrameData(@RequestParam("data")String dataStr){
        ResultVO resultVO = null ;
        FirstFrameDataDTO firstFrameDataDTO = JSONObject.parseObject(dataStr,FirstFrameDataDTO.class) ;
        if (firstFrameDataDTO==null){
            resultVO = ResultUtils.buildFail() ;
            return resultVO ;
        }
        boolean result = autoPerfFirstFrameService.addFirstFrameTaskData(firstFrameDataDTO) ;
        if (result){
            resultVO = ResultUtils.buildSuccess();
        }else {
            resultVO = ResultUtils.buildFail() ;
        }
        return resultVO ;
    }


}
