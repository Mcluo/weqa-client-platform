package com.netease.vcloud.qa.service.risk.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.dao.ClientRiskDetailDAO;
import com.netease.vcloud.qa.model.ClientRiskDetailDO;
import com.netease.vcloud.qa.risk.RiskCheckRange;
import com.netease.vcloud.qa.risk.RiskProjectStatus;
import com.netease.vcloud.qa.risk.RiskTaskStatus;
import com.netease.vcloud.qa.service.risk.RiskCheckException;
import com.netease.vcloud.qa.service.risk.manager.data.RiskDetailInfoBO;
import com.netease.vcloud.qa.service.risk.manager.data.RiskRuleInfoBO;
import com.netease.vcloud.qa.service.risk.manager.view.RiskDetailInfoVO;
import com.netease.vcloud.qa.service.risk.manager.view.RiskDetailWithDataInfoVO;
import com.netease.vcloud.qa.service.risk.source.RiskDataService;
import com.netease.vcloud.qa.service.risk.source.struct.RiskCheckStander;
import com.netease.vcloud.qa.service.risk.source.struct.view.CheckDataVOInterface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 11:53
 */
@Service
public class RiskManagerService {

    private static final Logger RISK_LOGGER = LoggerFactory.getLogger("RiskLog");

    @Autowired
    private RiskRuleService riskRuleService ;

    @Autowired
    private RiskDataService riskDataService ;

    @Autowired
    private ClientRiskDetailDAO riskDetailDAO ;

    private ThreadPoolExecutor executor ;

    @PostConstruct
    public void  init(){
        executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }
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
    public boolean createTaskRiskInfo(long taskId, RiskTaskStatus taskStatus) throws RiskCheckException{
        if (taskStatus == null){
            taskStatus = RiskTaskStatus.IN_DEVELOP ;
        }
        List<RiskRuleInfoBO> riskRuleInfoBOList = riskRuleService.getRuleByTypeAndStage(RiskCheckRange.TASK,taskStatus) ;
        if (CollectionUtils.isEmpty(riskRuleInfoBOList)){
            return true ;
        }
        boolean flag = true ;
        List<ClientRiskDetailDO> clientRiskDetailDOList = new ArrayList<ClientRiskDetailDO>() ;
        for (RiskRuleInfoBO riskRuleInfoBO :riskRuleInfoBOList){
            if (riskRuleInfoBO == null){
                continue;
            }
            ClientRiskDetailDO clientRiskDetailDO = this.buildRiskByRuleBO(RiskCheckRange.TASK, taskId, riskRuleInfoBO) ;
            if (clientRiskDetailDO != null){
                clientRiskDetailDOList.add(clientRiskDetailDO) ;
            }
        }
        if (CollectionUtils.isEmpty(clientRiskDetailDOList)){
            //这一步为空即为异常
            return false ;
        }
        int count = riskDetailDAO.patchInsertClientRiskDetailInfo(clientRiskDetailDOList) ;
        if (count < clientRiskDetailDOList.size() ){
            flag = false ;
        }
        //和数据源进行对比，确定风险本身
        this.checkTaskRiskInfoAndData(taskId) ;
        return flag ;
    }

    private ClientRiskDetailDO buildRiskByRuleBO(RiskCheckRange riskCheckRange, Long riskRangeId, RiskRuleInfoBO riskRuleInfoBO){
        if (riskRuleInfoBO == null){
            return null ;
        }
        ClientRiskDetailDO clientRiskDetailDO = new ClientRiskDetailDO() ;
        clientRiskDetailDO.setRuleId(riskRuleInfoBO.getId());
        clientRiskDetailDO.setRuleName(riskRuleInfoBO.getRuleName());
        clientRiskDetailDO.setRangeType(riskCheckRange.getCode());
        clientRiskDetailDO.setRangeId(riskRangeId);
        clientRiskDetailDO.setRiskDetail(JSONObject.toJSONString(riskRuleInfoBO.getCheckStander()));
        //未校验的默认值
        clientRiskDetailDO.setHasRisk((byte)0);
        clientRiskDetailDO.setCurrentResult("");
        return clientRiskDetailDO ;
    }

    /**
     * 更新任务数据
     */
    public boolean updateTaskStatus(long taskId, RiskTaskStatus taskStatus) throws RiskCheckException{
        //根据状态变化情况， 新增相关测试用例
        List<RiskRuleInfoBO> riskRuleInfoBOList = riskRuleService.getRuleByTypeAndStage(RiskCheckRange.TASK,taskStatus) ;
        if (CollectionUtils.isEmpty(riskRuleInfoBOList)){
            //没有什么要更新的，返回成功
            return true ;
        }
        //操作风险信息
        Set<Long> ruleIdSet = new HashSet<Long>() ;
        //1.将已有风险信息删除
        for (RiskRuleInfoBO riskRuleInfoBO : riskRuleInfoBOList){
            ruleIdSet.add(riskRuleInfoBO.getId()) ;
        }
        riskDetailDAO.deleteRiskByRangeAndRule(RiskCheckRange.TASK.getCode(), taskId,ruleIdSet) ;
        //2.重新新增风险信息并进行校验
        List<ClientRiskDetailDO> clientRiskDetailDOList = new ArrayList<ClientRiskDetailDO>() ;
        for (RiskRuleInfoBO riskRuleInfoBO : riskRuleInfoBOList){
            if (riskRuleInfoBO == null){
                continue;
            }
            ClientRiskDetailDO clientRiskDetailDO = this.buildRiskByRuleBO(RiskCheckRange.TASK, taskId, riskRuleInfoBO) ;
            if (clientRiskDetailDO != null){
                clientRiskDetailDOList.add(clientRiskDetailDO) ;
            }
        }
        //不存在就不需要
        if (CollectionUtils.isEmpty(clientRiskDetailDOList)){
            //这一步为空即为异常
            return true ;
        }
        boolean flag = true ;
        int count = riskDetailDAO.patchInsertClientRiskDetailInfo(clientRiskDetailDOList) ;
        if (count < clientRiskDetailDOList.size() ){
            flag = false ;
        }
        //和数据源进行对比，确定风险本身
        this.checkTaskRiskInfoAndData(taskId) ;
        return flag ;
    }


    //为了保证方法，这两步可以做成异步的

    /**
     * 检测项目是否有风险（异步）
     * @param projectId
     * @return
     */
    public void checkProjectRiskInfoAndData(long projectId) throws RiskCheckException{

    }

    /**
     * 检测任务是否有风险(异步)
     * @param taskId
     * @return
     */
    public void checkTaskRiskInfoAndData(Long taskId) throws RiskCheckException {
        if (taskId == null ){
            RISK_LOGGER.error("[RiskManagerService.checkTaskRiskInfoAndData] some param is null");
            return;
        }
        executor.execute(() -> {
            try{
                List<ClientRiskDetailDO> clientRiskDetailDOList = riskDetailDAO.getRiskListByRangeId(RiskCheckRange.TASK.getCode(), taskId) ;
                for (ClientRiskDetailDO clientRiskDetailDO : clientRiskDetailDOList){
                    boolean flag = checkRiskInfoAndData(clientRiskDetailDO) ;
                    if (!flag){
                        RISK_LOGGER.error("[RiskManagerService.checkTaskRiskInfoAndData] check risk false");
                    }
                }
            }catch (Exception e){
                RISK_LOGGER.error("[RiskManagerService.checkTaskRiskInfoAndData] update exception",e);
            }
        });
    }

    public boolean checkRiskInfoAndData(Long riskId) throws RiskCheckException {
        if (riskId == null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        ClientRiskDetailDO clientRiskDetailDO = riskDetailDAO.getRiskByID(riskId);
        return this.checkRiskInfoAndData(clientRiskDetailDO);
    }

    private boolean checkRiskInfoAndData(ClientRiskDetailDO clientRiskDetailDO) throws RiskCheckException{

        if (clientRiskDetailDO == null || StringUtils.isBlank(clientRiskDetailDO.getRiskDetail())) {
            return false;
        }
        RiskCheckStander riskCheckStander = JSONObject.parseObject(clientRiskDetailDO.getRiskDetail(), RiskCheckStander.class);
        if (riskCheckStander == null || StringUtils.isBlank(riskCheckStander.getType())) {
            return false;
        }
        RiskCheckRange checkRange = RiskCheckRange.getRiskCheckRangeByCode(clientRiskDetailDO.getRangeType()) ;
        String currentData = riskDataService.getCurrentDate(riskCheckStander.getType(), checkRange, clientRiskDetailDO.getRangeId());
        boolean flag = riskDataService.hasRisk(riskCheckStander.getType(), riskCheckStander.getCheckInfoDetail(), currentData);
        clientRiskDetailDO.setCurrentResult(currentData);
        clientRiskDetailDO.setHasRisk(flag ? (byte) 1 : (byte) 0);
        int count = riskDetailDAO.updateRiskDetailInfo(clientRiskDetailDO);
        if (count < 1) {
            RISK_LOGGER.error("[RiskManagerService.checkRiskInfoAndData] update Risk detail fail");
            return false ;
        }else {
            return true ;
        }
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
        List<ClientRiskDetailDO>  riskDetailInfoDOList = riskDetailDAO.getRiskListByRangeId(RiskCheckRange.TASK.getCode(), taskId) ;
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
//            RiskTaskStatus riskTaskStatus = RiskTaskStatus.getRiskTaskStatusByCode(clientRiskDetailDO.getRangeType()) ;
//            riskDetailInfoBO.setCheckStatus(riskTaskStatus);
            riskDetailInfoBO.setCurrentResult(clientRiskDetailDO.getCurrentResult());
            riskDetailInfoBO.setRangeId(taskId);
            riskDetailInfoBO.setHasRisk(clientRiskDetailDO.getHasRisk()==1?true:false);
            RiskRuleInfoBO riskRuleInfoBO = ruleInfoBOMap.get(riskDetailInfoBO.getRuleId()) ;
            if (riskRuleInfoBO != null){
                if(riskRuleInfoBO.getCheckStander()!=null) {
                    riskDetailInfoBO.setRiskType(riskRuleInfoBO.getCheckStander().getType());
                }
                RiskTaskStatus riskTaskStatus = RiskTaskStatus.getRiskTaskStatusByCode(riskRuleInfoBO.getStage()) ;
                riskDetailInfoBO.setCheckStatus(riskTaskStatus);
                riskDetailInfoBO.setRiskPriority(riskRuleInfoBO.getPriority());
                riskDetailInfoBO.setRiskDetail(this.buildRiskDetail(riskRuleInfoBO.getCheckStander()));
            }
            riskDetailInfoBOList.add(riskDetailInfoBO) ;
        }
        return riskDetailInfoBOList ;
    }

    /**
     * 根据风险ID,展示风险详情信息
     * @param riskId
     */
    public RiskDetailWithDataInfoVO getRiskDetailInfoVO(Long riskId) throws RiskCheckException{
        if (riskId == null){
            throw new RiskCheckException(RiskCheckException.RISK_CHECK_PARAM_EXCEPTION) ;
        }
        ClientRiskDetailDO riskDetailDO = riskDetailDAO.getRiskByID(riskId) ;
        if (riskDetailDO == null){
            throw new RiskCheckException(RiskCheckException.RISK_DATA_IS_NULL_EXCEPTION) ;
        }
        Set<Long>  ruleIdSet = new HashSet<Long>() ;
        ruleIdSet.add(riskDetailDO.getRuleId()) ;
        Map<Long, RiskRuleInfoBO> ruleInfoBOMap = riskRuleService.getRuleByIdSet(ruleIdSet) ;
        RiskRuleInfoBO riskRuleInfoBO = ruleInfoBOMap.get(riskDetailDO.getRuleId()) ;
        RiskDetailWithDataInfoVO riskDetailWithDataInfoVO = new RiskDetailWithDataInfoVO() ;
        RiskDetailInfoVO riskDetailInfoVO = new RiskDetailInfoVO() ;
        riskDetailInfoVO.setRiskId(riskDetailDO.getId());
        riskDetailInfoVO.setRuleId(riskDetailDO.getRuleId());
        riskDetailInfoVO.setCurrentValue(riskDetailDO.getCurrentResult());
        riskDetailInfoVO.setHasRisk(riskDetailDO.getHasRisk()==(byte)0?false:true);
        RiskCheckRange riskCheckRange = RiskCheckRange.getRiskCheckRangeByCode(riskDetailDO.getRangeType()) ;
        if (riskRuleInfoBO != null) {
            riskDetailInfoVO.setRiskTitle(riskRuleInfoBO.getRuleName());
            riskDetailInfoVO.setRiskPriority(riskRuleInfoBO.getPriority());
            riskDetailInfoVO.setRiskType(riskRuleInfoBO.getCheckStander().getType());
            RiskCheckStander riskCheckStander = JSONObject.parseObject(riskDetailDO.getRiskDetail(),RiskCheckStander.class) ;
            if (riskCheckStander != null) {
                riskDetailInfoVO.setPassStander(riskDataService.getPassStandard(riskRuleInfoBO.getCheckStander().getType(),riskCheckStander.getCheckInfoDetail()));
            }
            CheckDataVOInterface checkDataVO = riskDataService.getCheckData(riskRuleInfoBO.getCheckStander().getType(),riskCheckRange, riskDetailDO.getRangeId());
            riskDetailWithDataInfoVO.setCheckData(checkDataVO); ;
        }
        riskDetailWithDataInfoVO.setDetailInfo(riskDetailInfoVO);
        return  riskDetailWithDataInfoVO ;
    }

    /**
     * 具体通过标准构建
     * @param riskCheckStander
     * @return
     */
    private String buildRiskDetail(RiskCheckStander riskCheckStander){
        if (riskCheckStander == null){
            return null ;
        }

       String passStandard = null ;
        try {
            passStandard = riskDataService.getPassStandard(riskCheckStander.getType(), riskCheckStander.getCheckInfoDetail());
        }catch (RiskCheckException e){
            RISK_LOGGER.error("[RiskManagerService.buildRiskDetail]  build getPassStandard",e);
        }
        return passStandard ;
    }


    public void syncProjectRiskInfoData(long projectId) throws RiskCheckException{
        this.syncRiskInfoData(RiskCheckRange.PROJECT,projectId);
    }

    public void syncTaskRiskInfoData(long taskId) throws RiskCheckException{
        this.syncRiskInfoData(RiskCheckRange.TASK,taskId);
    }

    private void syncRiskInfoData(RiskCheckRange riskCheckRange , long rangeId) throws RiskCheckException{
        List<ClientRiskDetailDO> clientRiskDetailDOList = riskDetailDAO.getRiskListByRangeId(riskCheckRange.getCode(),rangeId) ;
        if (CollectionUtils.isEmpty(clientRiskDetailDOList)){
            return;
        }
        executor.execute(()->{
            try {
                for (ClientRiskDetailDO clientRiskDetailDO : clientRiskDetailDOList) {
                    if (clientRiskDetailDO == null || StringUtils.isBlank(clientRiskDetailDO.getRiskDetail())) {
                        continue;
                    }
                    RiskCheckStander riskCheckStander = JSONObject.parseObject(clientRiskDetailDO.getRiskDetail(), RiskCheckStander.class);
                    if (riskCheckStander == null || StringUtils.isBlank(riskCheckStander.getType())) {
                        continue;
                    }
                    riskDataService.asyncDate(riskCheckStander.getType(), riskCheckRange, rangeId);
                }
            }catch (Exception e){
                RISK_LOGGER.error("[RiskManagerService.asyncRiskInfoData] asyncRiskInfoData exception",e);
            }
        });
    }
}
