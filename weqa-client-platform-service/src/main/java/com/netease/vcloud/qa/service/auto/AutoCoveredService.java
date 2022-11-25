package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.service.tc.ClientTcAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自动化统计覆盖服务
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/31 16:46
 */
@Service
public class AutoCoveredService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    @Autowired
    private ClientTcAsyncService clientTcAsyncService ;

    public boolean checkAndMarkTestCase(Long testCase){
        if (testCase == null){
            TC_LOGGER.error("[AutoCoveredService.checkAndMarkTestCase] mark test case ,but tcId is null");
            return false ;
        }
        boolean flag = clientTcAsyncService.asyncAutoCoverInfo(testCase,true) ;
        return flag ;
    }

    /**
     * 批量更新tc的覆盖情况
     * 注意不是批量落库的，速度较慢
     * @param coveredInfoMap
     * @return
     */
    public boolean patchUpdateCoveredStatus(Map<Long,Boolean> coveredInfoMap){
        if (coveredInfoMap == null){
            return false ;
        }
        boolean flag = true ;
        for (Map.Entry<Long,Boolean> entry : coveredInfoMap.entrySet()){
            if (entry!=null){
                flag = clientTcAsyncService.asyncAutoCoverInfo(entry.getKey(),entry.getValue()) && flag;
            }
        }
        return flag ;
    }
}
