package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.dao.ClientAutoTestStatisticDAO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunResultStatisticDetailVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunResultStatisticInfoVO;
import com.netease.vcloud.qa.service.auto.data.statistic.RunSummerInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 14:58
 */
@Service
public class AutoTestResultStatisticService {

    private ClientAutoTestStatisticDAO clientAutoTestStatisticDAO ;

    /**
     * 运行数据汇总
     */
    public RunSummerInfoVO getGroupRunSummer(){

        return null ;
    }

    /**
     * 列表的展示
     * @param startTime
     * @param finishTime
     * @return
     */
    public List<RunResultStatisticInfoVO> queryRunResultStatistic(Long startTime , Long finishTime){

        return null ;
    }

    /**
     *
     * @param startTime
     * @param finishTime
     * @param runInfo
     * @return
     */
    public RunResultStatisticDetailVO queryRunResultDetail(Long startTime , Long finishTime , String runInfo){
        return null ;
    }
}
