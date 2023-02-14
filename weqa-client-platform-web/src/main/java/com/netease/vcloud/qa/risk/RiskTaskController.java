package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.process.RiskTaskService;
import com.netease.vcloud.qa.service.risk.process.dto.RiskTaskDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:01
 */
@RestController
@RequestMapping("/risk/task")
public class RiskTaskController {
    @Autowired
    private RiskTaskService riskTaskService ;

    /**
     * http://127.0.0.1:8788/g2-client/risk/task/query?projectId=2
     * @param projectId
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO getTaskListByProject(@RequestParam(name = "projectId") long projectId){
        ResultVO resultVO = null ;
        try {
            List<RiskTaskBaseVO> riskTaskBaseVOList = riskTaskService.getTaskListByProject(projectId);
            if (riskTaskBaseVOList==null){
                resultVO = ResultUtils.buildFail();
            }else {
                resultVO = ResultUtils.buildSuccess(riskTaskBaseVOList) ;
            }

        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/task/add?projectId=2&name=测试任务&link=http&employee=luqiuwei,system
     * @param projectId
     * @param taskName
     * @param linkUrl
     * @param employees
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVO addOneTaskToProject(@RequestParam(name = "projectId") long projectId,
                                        @RequestParam(name = "name") String taskName ,
                                        @RequestParam(name = "link") String linkUrl,
                                        @RequestParam(name = "employee",required = false) List<String> employees){
        RiskTaskDTO riskTaskDTO = new RiskTaskDTO() ;
        riskTaskDTO.setProjectId(projectId);
        riskTaskDTO.setName(taskName);
        riskTaskDTO.setTaskLink(linkUrl);
        riskTaskDTO.setEmployeeList(employees);
        ResultVO resultVO = null ;
        try {
            Long taskId = riskTaskService.addProjectTask(riskTaskDTO);
            if (taskId == null){
                resultVO = ResultUtils.buildFail();
            }else {
                resultVO = ResultUtils.buildSuccess(taskId) ;
            }
        }catch (Exception e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/task/remove?project=2&task=1
     * @param projectId
     * @param taskId
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody
    public ResultVO removeTaskFromProject(@RequestParam(name = "project",required = false) Long projectId,
                                          @RequestParam(name = "task") Long taskId){
        ResultVO resultVO = null ;
        try {
            boolean flag = riskTaskService.removeProjectTask(projectId, taskId);
            resultVO = ResultUtils.build(flag);
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

}
