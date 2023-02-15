package com.netease.vcloud.qa.service.risk.manager;

import com.alibaba.fastjson.JSON;
import com.netease.vcloud.qa.dao.ClientRiskDetailDAO;
import com.netease.vcloud.qa.model.ClientRiskDetailDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.risk.RiskProjectStatus;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.manager.data.RiskDetailInfoBO;
import com.netease.vcloud.qa.service.risk.manager.data.RiskRuleInfoBO;
import com.netease.vcloud.qa.service.risk.source.RiskDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
        List<RiskDetailInfoBO> riskDetailInfoBOList = new ArrayList<RiskDetailInfoBO>() ;
        List<ClientRiskDetailDO>  riskDetailInfoDOList = riskDetailDAO.buildRiskListByRangeId(RiskCheckRange.TASK.getCode(), taskId) ;
        if (CollectionUtils.isEmpty(riskDetailInfoDOList)){
            return riskDetailInfoBOList ;
        }
        Set<Long>  ruleIdSet = new HashSet<Long>() ;
        for (ClientRiskDetailDO clientRiskDetailDO : riskDetailInfoDOList){
            ruleIdSet.add(clientRiskDetailDO.getRuleId()) ;
        }
        Map<Long, RiskRuleInfoBO> ruleInfoBOMap = riskRuleService.getRuleByIdSet(ruleIdSet) ;
        for (ClientRiskDetailDO clientRiskDetailDO : riskDetailInfoDOList){
            RiskDetailInfoBO riskDetailInfoBO = new RiskDetailInfoBO() ;
            riskDetailInfoBO.setId(clientRiskDetailDO.getId());
            riskDetailInfoBO.setRuleId(clientRiskDetailDO.getRuleId());
            riskDetailInfoBO.setRuleName(clientRiskDetailDO.getRuleName());
            riskDetailInfoBO.setCheckRage(RiskCheckRange.TASK);
            RiskTaskStatus riskTaskStatus = RiskTaskStatus.getRiskTaskStatusByCode(clientRiskDetailDO.getRangeType()) ;
            riskDetailInfoBO.setCheckStatus(riskTaskStatus);
            riskDetailInfoBO.setCurrentResult(clientRiskDetailDO.getCurrentResult());
            riskDetailInfoBO.setRangeId(taskId);
            riskDetailInfoBO.setHasRisk(clientRiskDetailDO.getHasRisk()==1?true:false);
            RiskRuleInfoBO riskRuleInfoBO = ruleInfoBOMap.get(riskDetailInfoBO.getRuleId()) ;
            if (riskRuleInfoBO != null){
                riskDetailInfoBO.setRiskPriority(riskRuleInfoBO.getPriority());
                //fixme 这个需要具体修改
                riskDetailInfoBO.setRiskDetail(JSON.toJSONString(riskRuleInfoBO.getCheckStander()));
            }
        }
        return riskDetailInfoBOList ;
    }


}
