package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.dao.ClientAutoTestStatisticDAO;
import com.netease.vcloud.qa.date.DateUtils;
import com.netease.vcloud.qa.model.ClientAutoTestStatisticErrorInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestStatisticRunInfoDO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunResultStatisticDetailVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunResultStatisticInfoVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunStatisticVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunSummerInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 14:58
 */
@Service
public class AutoTestResultStatisticService {

    @Autowired
    private ClientAutoTestStatisticDAO clientAutoTestStatisticDAO ;

    /**
     * 运行数据汇总
     */
    public RunSummerInfoVO getGroupRunSummer(){
        long current = System.currentTimeMillis() ;
        Date todayZeroTime  =DateUtils.getFirstTimeOfWeek(current) ;
        Date firstTimeOfMonth = DateUtils.getFirstTimeOfMonth(current) ;
        ClientAutoTestStatisticRunInfoDO weekClientAutoTestStatisticRunInfoDO = clientAutoTestStatisticDAO.countAllSummerRunInfo(todayZeroTime,firstTimeOfMonth) ;
        ClientAutoTestStatisticRunInfoDO monthClientAutoTestStatisticRunInfoDO = clientAutoTestStatisticDAO.countAllSummerRunInfo(todayZeroTime,firstTimeOfMonth) ;
        RunSummerInfoVO runSummerInfoVO = new RunSummerInfoVO() ;
        runSummerInfoVO.setMonth(buildRunStatisticVO(monthClientAutoTestStatisticRunInfoDO));
        runSummerInfoVO.setWeek(buildRunStatisticVO(weekClientAutoTestStatisticRunInfoDO));
        return runSummerInfoVO ;
    }

    private RunStatisticVO buildRunStatisticVO(ClientAutoTestStatisticRunInfoDO clientAutoTestStatisticRunInfoDO){
        if (clientAutoTestStatisticRunInfoDO == null){
            return null ;
        }
        RunStatisticVO runStatisticVO = new RunStatisticVO() ;
        runStatisticVO.setRunningInfo(clientAutoTestStatisticRunInfoDO.getRunInfo());
        int successNumber = clientAutoTestStatisticRunInfoDO.getSuccess() ;
        int failNumber = clientAutoTestStatisticRunInfoDO.getFail();
        runStatisticVO.setSuccess(successNumber);
        runStatisticVO.setFail(failNumber);
        int total = successNumber + failNumber ;
        runStatisticVO.setTotal(total);
        double rate = (double) successNumber / (double) total * 100 ;
        runStatisticVO.setSuccessRate(rate);
        return runStatisticVO ;
    }

    /**
     * 列表的展示
     * @param startTime
     * @param finishTime
     * @return
     */
    public RunResultStatisticInfoVO queryRunResultStatistic(Long startTime , Long finishTime){
        List<ClientAutoTestStatisticRunInfoDO> clientAutoTestStatisticRunInfoDOList = clientAutoTestStatisticDAO.countSummerRunInfoGroupByRunInfo(new Date(startTime),new Date(finishTime)) ;
        RunResultStatisticInfoVO runResultStatisticInfoVO = new RunResultStatisticInfoVO() ;
        runResultStatisticInfoVO.setStartTime(startTime);
        runResultStatisticInfoVO.setFinishTime(finishTime);
        if (CollectionUtils.isEmpty(clientAutoTestStatisticRunInfoDOList)) {
            return runResultStatisticInfoVO ;
        }
        List<RunStatisticVO> runStatisticVOList = new ArrayList<RunStatisticVO>() ;
        runResultStatisticInfoVO.setRunStatisticList(runStatisticVOList);
        for (ClientAutoTestStatisticRunInfoDO clientAutoTestStatisticRunInfoDO : clientAutoTestStatisticRunInfoDOList){
            RunStatisticVO runStatisticVO = this.buildRunStatisticVO(clientAutoTestStatisticRunInfoDO) ;
            if (runStatisticVO!=null){
                runStatisticVOList.add(runStatisticVO) ;
            }
        }
        return runResultStatisticInfoVO ;
    }

    /**
     *
     * @param startTime
     * @param finishTime
     * @param runInfo
     * @return
     */
    public RunResultStatisticDetailVO queryRunResultDetail(Long startTime , Long finishTime , String runInfo){
        List<ClientAutoTestStatisticErrorInfoDO> clientAutoTestStatisticErrorInfoDOList = clientAutoTestStatisticDAO.countSummerErrorInfo(runInfo,new Date(startTime),new Date(finishTime)) ;
        RunResultStatisticDetailVO runResultStatisticDetailVO = new RunResultStatisticDetailVO() ;
        runResultStatisticDetailVO.setRunningInfo(runInfo);
        runResultStatisticDetailVO.setStartTime(startTime);
        runResultStatisticDetailVO.setFinishTime(finishTime);
        if (CollectionUtils.isEmpty(clientAutoTestStatisticErrorInfoDOList)){
            return runResultStatisticDetailVO ;
        }
        Map<String , Long> errorInfoCountMap = new LinkedHashMap<>() ;
        for (ClientAutoTestStatisticErrorInfoDO clientAutoTestStatisticErrorInfoDO : clientAutoTestStatisticErrorInfoDOList){
            errorInfoCountMap.put(clientAutoTestStatisticErrorInfoDO.getErrorInfo(),(long)clientAutoTestStatisticErrorInfoDO.getCount()) ;
        }
        runResultStatisticDetailVO.setErrorInfoCountMap(errorInfoCountMap);
        return runResultStatisticDetailVO ;
    }
}
