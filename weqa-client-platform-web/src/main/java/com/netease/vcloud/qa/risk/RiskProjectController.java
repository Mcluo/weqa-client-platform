package com.netease.vcloud.qa.risk;

import com.netease.vcloud.qa.result.ResultUtils;
import com.netease.vcloud.qa.result.ResultVO;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.process.RiskProjectService;
import com.netease.vcloud.qa.service.risk.process.dto.RiskProjectDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskProjectListVO;
import com.netease.vcloud.qa.service.risk.process.view.RiskProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:00
 */
@RestController
@RequestMapping("/risk/project")
public class RiskProjectController {
    @Autowired
    private RiskProjectService riskProjectService ;

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

}
