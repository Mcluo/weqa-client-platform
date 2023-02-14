package com.netease.vcloud.qa.service.risk.process;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientRiskTaskDAO;
import com.netease.vcloud.qa.dao.ClientRiskTaskPersonDAO;
import com.netease.vcloud.qa.model.ClientRiskTaskDO;
import com.netease.vcloud.qa.model.ClientRiskTaskPersonDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.risk.RiskPersonType;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.RiskManagerService;
import com.netease.vcloud.qa.service.risk.process.dto.RiskTaskDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskBaseVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:52
 */
@Service
public class RiskTaskService {

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private RiskManagerService riskManagerService ;

    @Autowired
    private RiskProjectService riskProjectService ;

    @Autowired
    private ClientRiskTaskDAO riskTaskDAO ;

    @Autowired
    private ClientRiskTaskPersonDAO riskTaskPersonDAO ;

    @Autowired
    private UserInfoService userInfoService ;


    public Long addProjectTask(RiskTaskDTO riskTaskDTO) throws RiskCheckException{
        if (riskTaskDTO == null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        //1，基础信息落库
        ClientRiskTaskDO clientRiskTaskDO = this.buildClientRiskTaskDOByDTO(riskTaskDTO) ;
        int count = riskTaskDAO.insertClientRiskTask(clientRiskTaskDO) ;
        Long taskId = clientRiskTaskDO.getId() ;
        if (count < 1 || taskId == null){
            RISK_LOGGER.error("[RiskTaskService.addProjectTask]create task exception");
//            return null ;
            throw  new RiskCheckException(RiskCheckException.RISK_TASK_MANAGER_EXCEPTION) ;

        }
        //2,人员信息落库
        List<String> employeeList = riskTaskDTO.getEmployeeList() ;
        List<ClientRiskTaskPersonDO> employeeDOList = new ArrayList<>() ;
        if(!CollectionUtils.isEmpty(employeeList)){
            for (String employee : employeeList){
                ClientRiskTaskPersonDO clientRiskTaskPersonDO = this.buildClientRiskTaskPerson(taskId,employee) ;
                if (clientRiskTaskPersonDO != null){
                    employeeDOList.add(clientRiskTaskPersonDO) ;
                }
            }
            count = riskTaskPersonDAO.patchInsertTaskPersonInfo(employeeDOList) ;
            if (count < employeeDOList.size()){
                RISK_LOGGER.error("[RiskTaskService.addProjectTask]add task person exception");
                throw  new RiskCheckException(RiskCheckException.RISK_TASK_MANAGER_EXCEPTION) ;
            }
        }
        //3，风险信息创建
        boolean createRiskFlag = riskManagerService.createTaskRiskInfo(taskId,RiskTaskStatus.IN_DEVELOP) ;
        if (!createRiskFlag){
            RISK_LOGGER.error("[RiskTaskService.addProjectTask]create task risk exception");
        }
        return  taskId ;
    }

    private ClientRiskTaskDO buildClientRiskTaskDOByDTO(RiskTaskDTO riskTaskDTO){
        if (riskTaskDTO == null){
            return null ;
        }
        ClientRiskTaskDO clientRiskTaskDO = new ClientRiskTaskDO() ;
        clientRiskTaskDO.setTaskName(riskTaskDTO.getName());
        clientRiskTaskDO.setProjectId(riskTaskDTO.getProjectId());
        clientRiskTaskDO.setJiraInfo(riskTaskDTO.getTaskLink());
        return clientRiskTaskDO ;
    }

    private ClientRiskTaskPersonDO buildClientRiskTaskPerson(Long taskId , String employee){
        if (taskId == null || StringUtils.isBlank(employee)){
            return null ;
        }
        ClientRiskTaskPersonDO clientRiskTaskPersonDO = new ClientRiskTaskPersonDO() ;
        clientRiskTaskPersonDO.setTaskId(taskId);
        clientRiskTaskPersonDO.setEmployee(employee);
        clientRiskTaskPersonDO.setRole(RiskPersonType.NORMAL_NUMBER.getType());
        return clientRiskTaskPersonDO ;
    }

    public boolean removeProjectTask(Long projectId , Long taskId ) throws RiskCheckException{
        if ( taskId == null){
            //注意，项目可以不传
            throw  new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        int count  = riskTaskDAO.logicDeleteClientRiskTask(projectId,taskId) ;
        if (count <= 0) {
            return false;
        }else {
            return true ;
        }
    }


    public List<RiskTaskBaseVO> getTaskListByProject(Long project) throws RiskCheckException {
        if (project == null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        List<ClientRiskTaskDO> clientRiskTaskDOList = riskTaskDAO.getClientRiskTaskListByProjectId(project) ;
        List<RiskTaskBaseVO> riskTaskBaseVOList = new ArrayList<RiskTaskBaseVO>() ;
        if (CollectionUtils.isEmpty(clientRiskTaskDOList)){
            return riskTaskBaseVOList ;
        }
        //1,任务列表获取
        Set<Long> taskIdSet = new HashSet<Long>();
        for (ClientRiskTaskDO clientRiskTaskDO : clientRiskTaskDOList){
            if (clientRiskTaskDO!=null){
                taskIdSet.add(clientRiskTaskDO.getId()) ;
            }
        }
        List<ClientRiskTaskPersonDO> riskPersonList =  riskTaskPersonDAO.getPersonDOByTaskSet(taskIdSet) ;
        //2，任务-人员拼装
        Map<Long,List<ClientRiskTaskPersonDO>> taskPersonListMap = new HashMap<Long,List<ClientRiskTaskPersonDO>>() ;
        Set<String> employeeSet = new HashSet<String>() ;
        if (CollectionUtils.isEmpty(riskPersonList)) {
            for (ClientRiskTaskPersonDO clientRiskTaskPersonDO : riskPersonList){
                if (clientRiskTaskPersonDO == null){
                    continue;
                }
                String employee = clientRiskTaskPersonDO.getEmployee();
                long taskId = clientRiskTaskPersonDO.getTaskId() ;
                employeeSet.add(employee) ;
                List<ClientRiskTaskPersonDO> taskPersonList = taskPersonListMap.get(taskId);
                if (taskPersonList == null){
                    taskPersonList = new ArrayList<>() ;
                    taskPersonListMap.put(taskId,taskPersonList) ;
                }
                taskPersonList.add(clientRiskTaskPersonDO) ;
            }
        }
        //3,人员信息处理
        Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(employeeSet) ;
        //4，结果拼装
        for (ClientRiskTaskDO clientRiskTaskDO : clientRiskTaskDOList){
            if (clientRiskTaskDO == null){
                continue;
            }
            List<ClientRiskTaskPersonDO> taskPersonDOList = taskPersonListMap.get(clientRiskTaskDO.getId()) ;
            RiskTaskBaseVO riskTaskBaseVO = this.buildRiskTaskVOByDO(clientRiskTaskDO,taskPersonDOList,userInfoBOMap) ;
            if (riskTaskBaseVO != null){
                riskTaskBaseVOList.add(riskTaskBaseVO) ;
            }
        }
        return riskTaskBaseVOList ;
    }

    private RiskTaskBaseVO buildRiskTaskVOByDO(ClientRiskTaskDO clientRiskTaskDO, List<ClientRiskTaskPersonDO> taskPersonDOList,Map<String, UserInfoBO> userInfoBOMap){
        if (clientRiskTaskDO == null){
            return null ;
        }
        RiskTaskBaseVO riskTaskBaseVO = new RiskTaskBaseVO() ;
        riskTaskBaseVO.setId(clientRiskTaskDO.getId());
        riskTaskBaseVO.setTaskName(clientRiskTaskDO.getTaskName());
        RiskTaskStatus riskTaskStatus = RiskTaskStatus.getRiskTaskStatusByCode(clientRiskTaskDO.getTaskStatus()) ;
        if (riskTaskStatus!=null){
            riskTaskBaseVO.setStatus(riskTaskStatus.getStatus());
        }
        List<UserInfoVO> userList = new ArrayList<UserInfoVO>() ;
        if (taskPersonDOList != null) {
            for (ClientRiskTaskPersonDO clientRiskTaskPersonDO : taskPersonDOList) {
                if (clientRiskTaskPersonDO == null) {
                    continue;
                }
                UserInfoBO userInfoBO = userInfoBOMap.get(clientRiskTaskPersonDO.getEmployee());
                UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO);
                userList.add(userInfoVO);
            }
        }
        riskTaskBaseVO.setUserList(userList);
        return riskTaskBaseVO ;
    }

}
