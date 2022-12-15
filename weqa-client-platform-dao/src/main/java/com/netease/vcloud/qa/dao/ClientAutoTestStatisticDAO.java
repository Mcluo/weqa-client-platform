package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTestStatisticErrorInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestStatisticRunInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/15 17:35
 */
@Mapper
public interface ClientAutoTestStatisticDAO {

    /**
     * 根据时间读取
     * @param startTime
     * @param finishTime
     * @return
     */
    ClientAutoTestStatisticRunInfoDO countSummerRunInfo(@Param("start") Long startTime , @Param("finish") Long finishTime) ;

    /**
     *
     * @param startTime
     * @param finishTime
     * @return
     */
    ClientAutoTestStatisticErrorInfoDO countSummerErrorInfo(@Param("runInfo") String runInfo, @Param("start") Long startTime , @Param("finish") Long finishTime) ;

}
