package com.netease.vcloud.qa.service.risk.manager;

import com.netease.vcloud.qa.dao.ClientRiskDetailDAO;
import com.netease.vcloud.qa.risk.RiskProjectStatus;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.manager.data.RiskDetailInfoBO;
import com.netease.vcloud.qa.service.risk.manager.view.RiskBaseInfoVO;
import com.netease.vcloud.qa.service.risk.source.RiskDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:53
 */
@Service
public class RiskManagerService {
    @Autowired
    private RiskRuleService riskRuleService ;

    @Autowired
    private RiskDataService riskDataService ;

    @Autowired
    private ClientRiskDetailDAO riskDetailDAO ;


    /**
     * 根据项目ID创建项目的风险列表信息
     * @param projectId
     * @return
     */
    public boolean createProjectRiskInfo(long projectId, RiskProjectStatus projectStatus){
        //暂时不需要，返回为true
        return true ;
    }

    /**
     * 根据任务id，创建任务的风险列表信息
     * @param taskId
     * @return
     */
    public boolean createTaskRiskInfo(long taskId, RiskTaskStatus taskStatus){
        return false ;
    }

    /**
     * 获取项目的风险
     * @param projectId
     * @return
     */
    public List<RiskDetailInfoBO> getProjectRiskInfo(long projectId){
        return  null ;
    }

    /**
     * 获取任务的风险
     * @param taskId
     * @return
     */
    public List<RiskDetailInfoBO> getTaskRiskInfo(long taskId){
        return null ;
    }

//    public List<RiskBaseInfoVO>  getTaskRiskList(Long taskId){
//        return null ;
//    }

}
