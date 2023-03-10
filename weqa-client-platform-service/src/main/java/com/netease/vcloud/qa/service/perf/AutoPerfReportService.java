package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.dao.VcloudClientAutoAndroidPrefInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoIosPrefInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoIosPrefMemoryInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoPerfTaskDAO;
import com.netease.vcloud.qa.model.VcloudClientAutoAndroidPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefMemoryInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 自动化统计覆盖服务
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/31 16:46
 */
@Service
public class AutoPerfReportService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    @Autowired
    private VcloudClientAutoIosPrefMemoryInfoDAO clientAutoIosPrefMemoryInfoDAO;

    @Autowired
    private VcloudClientAutoIosPrefInfoDAO clientAutoIosPrefInfoDAO;

    @Autowired
    private VcloudClientAutoAndroidPrefInfoDAO clientAutoAndroidPrefInfoDAO;

    @Autowired
    private VcloudClientAutoPerfTaskDAO clientAutoPerfTaskDAO;

    public void insertIosMemoryInfo(VcloudClientAutoIosPrefMemoryInfoDO clientAutoIosPrefMemoryInfoDO){
        clientAutoIosPrefMemoryInfoDAO.insert(clientAutoIosPrefMemoryInfoDO);
    }

    public void insertIosInfo(VcloudClientAutoIosPrefInfoDO clientAutoIosPrefInfoDO){
        clientAutoIosPrefInfoDAO.insert(clientAutoIosPrefInfoDO);
    }

    public void insertAndroidInfo(VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO){
        clientAutoAndroidPrefInfoDAO.insert(clientAutoAndroidPrefInfoDO);
    }

    public int insertPerfTask(VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO){
        return clientAutoPerfTaskDAO.insert(clientAutoPerfTaskDO);
    }
}
