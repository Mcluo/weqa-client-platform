package com.netease.vcloud.qa.service.risk.process;

import com.netease.vcloud.qa.dao.ClientRiskProjectDAO;
import com.netease.vcloud.qa.model.ClientRiskProjectDO;
import com.netease.vcloud.qa.risk.RiskProjectStatus;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.RiskManagerService;
import com.netease.vcloud.qa.service.risk.process.dto.RiskProjectDTO;
import com.netease.vcloud.qa.service.risk.process.view.RiskProjectListVO;
import com.netease.vcloud.qa.service.risk.process.view.RiskProjectVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:51
 */
@Service
public class RiskProjectService {

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private RiskManagerService riskManagerService ;

    @Autowired
    private ClientRiskProjectDAO  riskProjectDAO ;


    public Long createNewProject(RiskProjectDTO riskProjectDTO) throws RiskCheckException {
        if (riskProjectDTO == null || StringUtils.isBlank(riskProjectDTO.getName())){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        ClientRiskProjectDO clientRiskProjectDO = new ClientRiskProjectDO() ;
        clientRiskProjectDO.setProjectName(riskProjectDTO.getName());
        clientRiskProjectDO.setCreator(riskProjectDTO.getCreator());
        clientRiskProjectDO.setStartTime(new Date(System.currentTimeMillis()));
        clientRiskProjectDO.setProjectStatus(RiskProjectStatus.PROGRESS.getCode());
        int count = riskProjectDAO.createProject(clientRiskProjectDO) ;
        if (count>0){
            return clientRiskProjectDO.getId();
        }else {
            RISK_LOGGER.error("[RiskProjectService.createNewProject] save project to db fail");
            return null;
        }
    }

    public boolean updateProjectStatus(Long projectId , String status) throws RiskCheckException{
        if (projectId == null || status == null){
            throw  new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        RiskProjectStatus projectStatus = RiskProjectStatus.getStatusByName(status) ;
        if (projectStatus == null){
            throw  new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        ClientRiskProjectDO clientRiskProjectDO = riskProjectDAO.getRiskProjectById(projectId) ;
        if (clientRiskProjectDO == null){
            throw new RiskCheckException(RiskCheckException.RISK_PROJECT_IS_NOT_EXIST_EXCEPTION) ;
        }
        int count = riskProjectDAO.updateProjectStatus(projectId,projectStatus.getCode()) ;
        if (count >0){
            return true ;
        }else {
            RISK_LOGGER.error("[RiskProjectService.updateProjectStatus] save project status fail");
            return false;
        }
    }

    public RiskProjectListVO geProjectList(int size , int current){
        RiskProjectListVO riskProjectListVO = new RiskProjectListVO() ;
        int start = (current - 1 ) * size ;
        List<ClientRiskProjectDO> clientRiskProjectDOList = riskProjectDAO.queryRiskProject(start,size) ;
        int count = riskProjectDAO.countRiskProject() ;
        List<RiskProjectVO> riskProjectVOList = new ArrayList<RiskProjectVO>() ;
        for (ClientRiskProjectDO clientRiskProjectDO : clientRiskProjectDOList){
            RiskProjectVO riskProjectVO = this.buildRiskProjectVOByDO(clientRiskProjectDO) ;
            if (riskProjectVO != null){
                riskProjectVOList.add(riskProjectVO) ;
            }
        }
        riskProjectListVO.setProjectList(riskProjectVOList);
        riskProjectListVO.setPage(current);
        riskProjectListVO.setSize(size);
        riskProjectListVO.setTotal(count);
        riskProjectListVO.setProjectList(riskProjectVOList);
        return riskProjectListVO ;
    }

    private RiskProjectVO buildRiskProjectVOByDO(ClientRiskProjectDO clientRiskProjectDO){
        if (clientRiskProjectDO == null){
            return null ;
        }
        RiskProjectVO riskProjectVO = new RiskProjectVO() ;
        riskProjectVO.setProjectName(clientRiskProjectDO.getProjectName());
        RiskProjectStatus riskProjectStatus = RiskProjectStatus.getStatusByCode(clientRiskProjectDO.getProjectStatus()) ;
        if (riskProjectStatus!=null){
            riskProjectVO.setStatus(riskProjectStatus.getStatus());
        }
        riskProjectVO.setStartTime(clientRiskProjectDO.getStartTime() == null?null : clientRiskProjectDO.getStartTime().getTime());
        riskProjectVO.setFinishTime(clientRiskProjectDO.getFinishTime() == null?null : clientRiskProjectDO.getFinishTime().getTime());
        return riskProjectVO ;
    }

}
