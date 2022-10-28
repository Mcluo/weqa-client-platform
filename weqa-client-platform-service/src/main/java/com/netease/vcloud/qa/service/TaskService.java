package com.netease.vcloud.qa.service;

import com.netease.vcloud.qa.CommonData;
import com.netease.vcloud.qa.service.tc.ClientTcAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时任务类
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 16:43
 */
@Service
public class TaskService {

    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("CommonLog");

    @Autowired
    private ClientTcAsyncService clientTcAsyncService ;

    /**
     * 同步TC任务
     */
    @Scheduled(cron = "0 0 4 1/1 * ?  ")
//    @Scheduled(cron = "0/10 * * * * ? ")
    public void asyncTCTask(){
        long finish = System.currentTimeMillis() ;
        long start = finish - CommonData.ONE_DAY ;

        try {
            System.out.println("start TC sync");
            COMMON_LOGGER.info("[TaskService.asyncTCTask] start tc sync");
            boolean flag = clientTcAsyncService.asyncProjectTestCase(start, finish);
            COMMON_LOGGER.info("[TaskService.asyncTCTask] finish tc sync");
            if (!flag){
                COMMON_LOGGER.info("[TaskService.asyncTCTask] finish tc sync");
            }
        }catch (Throwable e){
            COMMON_LOGGER.error("[TaskService.codeSyncTask]sync code task exception",e);
        }
    }
}
