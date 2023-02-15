package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.process.RiskProjectService;
import com.netease.vcloud.qa.service.risk.process.RiskTaskService;
import com.netease.vcloud.qa.service.risk.process.dto.RiskProjectDTO;
import com.netease.vcloud.qa.service.risk.process.dto.RiskTaskDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskProjectListVO;
import com.netease.vcloud.qa.service.risk.process.view.RiskProjectVO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:00
 */
@RestController
@RequestMapping("/risk/project")
public class RiskProjectController {
    @Autowired
    private RiskProjectService riskProjectService ;

    @Autowired
    private RiskTaskService riskTaskService ;

    /**
     *  http://127.0.0.1:8788/g2-client/risk/project/create?name=测试项目&operator=luqiuwei
     * @param projectName
     * @param operator
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public ResultVO createProject(@RequestParam(name = "name") String projectName , @RequestParam(name = "operator",required = false,defaultValue = "system") String operator){
        RiskProjectDTO riskProjectDTO = new RiskProjectDTO() ;
        riskProjectDTO.setName(projectName);
        riskProjectDTO.setCreator(operator);
        ResultVO resultVO = null ;
        try{
            Long id = riskProjectService.createNewProject(riskProjectDTO);
            if (id == null){
                resultVO = ResultUtils.buildFail() ;
            }else {
                resultVO = ResultUtils.buildSuccess(id) ;
            }
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/project/query?
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public ResultVO getProjectList(@RequestParam(name = "page" , required = false ,defaultValue = "1") int page ,
                                   @RequestParam(name = "size" , required = false ,defaultValue = "10") int size){
        RiskProjectListVO riskProjectListVO = riskProjectService.getProjectList(size,page) ;
        ResultVO resultVO = null ;
        if (riskProjectListVO == null){
            resultVO = ResultUtils.buildFail() ;
        }else {
            resultVO = ResultUtils.buildSuccess(riskProjectListVO) ;
        }
        return resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/project/delete?id=1
     * @param projectId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO deleteProject(@RequestParam(name= "id") long projectId){
        ResultVO resultVO = null ;
        try {
            boolean result = riskProjectService.deleteProject(projectId);
            resultVO = ResultUtils.build(result) ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return  resultVO ;
    }

    /**
     * http://127.0.0.1:8788/g2-client/risk/project/status/update?id=2&status=finish
     * @param projectId
     * @param status
     * @return
     */
    @RequestMapping("/status/update")
    @ResponseBody
    public ResultVO updateProjectStatus(@RequestParam(name="id")long projectId ,@RequestParam(name = "status")String status){
        ResultVO resultVO = null ;
        try{
            boolean result = riskProjectService.updateProjectStatus(projectId,status) ;
            resultVO = ResultUtils.build(result) ;
        }catch (RiskCheckException e){
            resultVO = ResultUtils.buildFail(e.getMessage()) ;
        }
        return  resultVO ;
    }




    /**
     * http://127.0.0.1:8788/g2-client/risk/project/task/query?projectId=2
     * @param projectId
     * @return
     */
    @RequestMapping("/task/query")
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
     * http://127.0.0.1:8788/g2-client/risk/project/task/add?projectId=2&name=测试任务&link=http&employee=luqiuwei@corp.netease.com,system
     * @param projectId
     * @param taskName
     * @param linkUrl
     * @param employees
     * @return
     */
    @RequestMapping("/task/add")
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
     * http://127.0.0.1:8788/g2-client/risk/project/task/remove?project=2&task=1
     * @param projectId
     * @param taskId
     * @return
     */
    @RequestMapping("/task/remove")
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


    @RequestMapping("/risk/get")
    @ResponseBody
    public ResultVO getProjectAllRisk(@RequestParam(name = "project")Long projectId){
        ResultVO resultVO = null ;
        return resultVO ;
    }

}
