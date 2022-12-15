package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTestStatisticErrorInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestStatisticRunInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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
    ClientAutoTestStatisticRunInfoDO countAllSummerRunInfo(@Param("start") Date startTime , @Param("finish") Date finishTime) ;

    List<ClientAutoTestStatisticRunInfoDO> countSummerRunInfoGroupByRunInfo(@Param("start") Date startTime , @Param("finish") Date finishTime) ;


    /**
     *
     * @param startTime
     * @param finishTime
     * @return
     */
    List<ClientAutoTestStatisticErrorInfoDO> countSummerErrorInfo(@Param("runInfo") String runInfo, @Param("start") Date startTime , @Param("finish") Date finishTime) ;

}
