package com.netease.vcloud.qa.service;

import com.netease.vcloud.qa.service.tc.ClientTcAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务类
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/26 16:43
 */
@Service
public class TaskService {

    @Autowired
    private ClientTcAsyncService clientTcAsyncService ;
}
