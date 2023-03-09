package com.netease.vcloud.qa.service.risk.process;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientRiskTaskDAO;
import com.netease.vcloud.qa.dao.ClientRiskTaskPersonDAO;
import com.netease.vcloud.qa.model.ClientRiskProjectDO;
import com.netease.vcloud.qa.model.ClientRiskTaskDO;
import com.netease.vcloud.qa.model.ClientRiskTaskPersonDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.risk.RiskCheckStatus;
import com.netease.vcloud.qa.risk.RiskPersonType;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.RiskManagerService;
import com.netease.vcloud.qa.service.risk.manager.data.RiskDetailInfoBO;
import com.netease.vcloud.qa.service.risk.manager.view.RiskDetailInfoVO;
import com.netease.vcloud.qa.service.risk.manager.view.RiskTaskRiskDetailVO;
import com.netease.vcloud.qa.service.risk.process.dto.RiskTaskDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskBaseVO;
import com.netease.vcloud.qa.service.risk.process.view.RiskTaskDetailVO;
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
        //设置默认任务状态
        clientRiskTaskDO.setTaskStatus(RiskTaskStatus.IN_DEVELOP.getCode());
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
        boolean createRiskFlag = riskManagerService.createTaskRiskInfo(taskId,null) ;
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
        if (!CollectionUtils.isEmpty(riskPersonList)) {
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

    public RiskTaskDetailVO getTaskDetailByTaskId(Long taskId) throws RiskCheckException{
        if (taskId == null){
            RISK_LOGGER.error("[RiskTaskService.getTaskDetailByTaskId] taskId is null") ;
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION);
        }
        ClientRiskTaskDO riskTask = riskTaskDAO.getClientRiskTaskByTaskId(taskId) ;
        if (riskTask==null){
            RISK_LOGGER.error("[RiskTaskService.getTaskDetailByTaskId] riskTask is null") ;
            throw new RiskCheckException(RiskCheckException.RISK_TASK_IS_NOT_EXIST_EXCEPTION) ;
        }
        ClientRiskProjectDO clientRiskProjectDO = riskProjectService.getProjectDOById(riskTask.getProjectId());
        if (clientRiskProjectDO == null){
            RISK_LOGGER.error("[RiskTaskService.getTaskDetailByTaskId] clientRiskProjectDO is null") ;
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        List<ClientRiskTaskPersonDO> clientRiskTaskPersonDOList = riskTaskPersonDAO.getPersonDOByID(taskId) ;
        RiskTaskDetailVO riskTaskDetailVO = this.buildRiskTaskDetailVOByDO(riskTask,clientRiskProjectDO,clientRiskTaskPersonDOList) ;
        return riskTaskDetailVO ;
    }

    private RiskTaskDetailVO buildRiskTaskDetailVOByDO (ClientRiskTaskDO clientRiskTaskDO , ClientRiskProjectDO clientRiskProjectDO,List<ClientRiskTaskPersonDO> clientRiskTaskPersonDOList){
        if (clientRiskProjectDO == null || clientRiskProjectDO ==null){
            return null ;
        }
        RiskTaskDetailVO riskTaskDetailVO = new RiskTaskDetailVO() ;
        riskTaskDetailVO.setId(clientRiskTaskDO.getId());
        riskTaskDetailVO.setTaskName(clientRiskTaskDO.getTaskName());
        riskTaskDetailVO.setProjectId(clientRiskProjectDO.getId());
        riskTaskDetailVO.setProjectName(clientRiskProjectDO.getProjectName());
        riskTaskDetailVO.setLinkUrl(clientRiskTaskDO.getJiraInfo());
        RiskTaskStatus riskTaskStatus = RiskTaskStatus.getRiskTaskStatusByCode(clientRiskTaskDO.getTaskStatus()) ;
        if (riskTaskStatus!=null) {
            riskTaskDetailVO.setStatus(riskTaskStatus.getStatus());
        }
        Set<String> personSet = new HashSet<String>() ;
        for (ClientRiskTaskPersonDO clientRiskTaskPersonDO : clientRiskTaskPersonDOList){
            if (clientRiskProjectDO != null){
                personSet.add(clientRiskTaskPersonDO.getEmployee()) ;
            }
        }
        Map<String,UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(personSet) ;
        List<UserInfoVO> userInfoVOList = new ArrayList<>() ;
        if (userInfoBOMap!=null){
            for(String person : personSet){
                UserInfoBO userInfoBO = userInfoBOMap.get(person) ;
                if (userInfoBO!=null){
                    userInfoVOList.add(CommonUtils.buildUserInfoVOByBO(userInfoBO)) ;
                }
            }
        }
        riskTaskDetailVO.setUserList(userInfoVOList);
        return riskTaskDetailVO ;
    }


    public RiskTaskRiskDetailVO getTaskRiskDetailInfo(Long taskId) throws RiskCheckException{
        if (taskId == null){
            RISK_LOGGER.error("[RiskTaskService.getTaskRiskDetailInfo] taskId is null") ;
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        RiskTaskRiskDetailVO riskTaskRiskDetailVO = new RiskTaskRiskDetailVO() ;
        Map<String,List<RiskDetailInfoVO>> riskDetailMap = new HashMap<String, List<RiskDetailInfoVO>>() ;
        List<RiskDetailInfoBO> riskDetailInfoBOList = riskManagerService.getTaskRiskInfo(taskId) ;
        if (riskDetailInfoBOList == null){
           return riskTaskRiskDetailVO ;
        }
        riskTaskRiskDetailVO.setRiskDetailInfo(riskDetailMap);
        for (RiskDetailInfoBO riskDetailInfoBO : riskDetailInfoBOList){
            RiskTaskStatus riskTaskStatus = (RiskTaskStatus) riskDetailInfoBO.getCheckStatus() ;
            if (riskTaskStatus==null) {
                continue;
            }
            List<RiskDetailInfoVO> riskDetailInfoVOList = riskDetailMap.get(riskTaskStatus.getStatus());
            if (riskDetailInfoVOList==null){
                riskDetailInfoVOList = new ArrayList<RiskDetailInfoVO>() ;
                riskDetailMap.put(riskTaskStatus.getStatus(), riskDetailInfoVOList) ;
            }
            RiskDetailInfoVO riskDetailInfoVO = this.buildRiskDetailInfoVOByBO(riskDetailInfoBO) ;
            if (riskDetailInfoVO!=null) {
                riskDetailInfoVOList.add(riskDetailInfoVO);
            }
        }
        return riskTaskRiskDetailVO ;
    }

    private RiskDetailInfoVO buildRiskDetailInfoVOByBO(RiskDetailInfoBO riskDetailInfoBO){
        if (riskDetailInfoBO == null){
            return null ;
        }
        RiskDetailInfoVO riskDetailInfoVO = new RiskDetailInfoVO() ;
        riskDetailInfoVO.setRiskId(riskDetailInfoBO.getId());
        riskDetailInfoVO.setRuleId(riskDetailInfoBO.getRuleId());
        riskDetailInfoVO.setRiskTitle(riskDetailInfoBO.getRuleName());
        riskDetailInfoVO.setCurrentValue(riskDetailInfoBO.getCurrentResult());
        riskDetailInfoVO.setRiskPriority(riskDetailInfoBO.getRiskPriority());
        riskDetailInfoVO.setPassStander(riskDetailInfoBO.getRiskDetail());
        riskDetailInfoVO.setHasRisk(riskDetailInfoBO.isHasRisk());
        riskDetailInfoVO.setRiskType(riskDetailInfoBO.getRiskType());
        return riskDetailInfoVO ;
    }

    public boolean updateRiskTaskStatus(Long task , RiskTaskStatus status) throws RiskCheckException{
        if (task == null || status == null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        ClientRiskTaskDO clientRiskTaskDO = riskTaskDAO.getClientRiskTaskByTaskId(task) ;
        if (clientRiskTaskDO == null){
            throw new RiskCheckException(RiskCheckException.RISK_TASK_IS_NOT_EXIST_EXCEPTION) ;
        }
        clientRiskTaskDO.setTaskStatus(status.getCode());
        int count = riskTaskDAO.updateClientRiskTask(clientRiskTaskDO) ;
        if (count < 1){
            RISK_LOGGER.error("[RiskTaskService.updateRiskTaskStatus]update status exception");
            return  false ;
        }
        return riskManagerService.updateTaskStatus(task,status) ;
    }


    public void startCheckTaskRiskInfoAndData(Long task) throws RiskCheckException{
        if (task==null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        riskManagerService.checkTaskRiskInfoAndData(task);
    }


    public void startSyncTaskRiskData(Long task) throws  RiskCheckException{
        if (task == null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        riskManagerService.syncTaskRiskInfoData(task);
    }



}
