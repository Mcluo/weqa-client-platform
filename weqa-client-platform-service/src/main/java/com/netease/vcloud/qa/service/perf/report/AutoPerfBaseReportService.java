package com.netease.vcloud.qa.service.perf.report;

import com.netease.vcloud.qa.dao.ClientPerfBaseLineDAO;
import com.netease.vcloud.qa.dao.ClientPerfReportDAO;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import com.netease.vcloud.qa.service.perf.data.PerfReportDataDTO;
import com.netease.vcloud.qa.service.perf.view.PerfBasePerfTaskInfoVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportDetailVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:58
 */
@Service
public class AutoPerfBaseReportService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("perfLog");

    @Autowired
    private ClientPerfBaseLineDAO clientPerfBaseLineDAO ;

    @Autowired
    private ClientPerfReportDAO clientPerfReportDAO ;
    @Autowired
    private AutoPerfTypeServiceManager autoPerfTypeServiceManager ;


    public List<PerfBasePerfTaskInfoVO> getBasePerfTask(AutoPerfType autoPerfType, String key, int page, int size){
        if (autoPerfType == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.getBasePerfTask] autoPerfType is null");
            return null ;
        }
        AutoPerfBaseReportInterface autoPerfBaseReportService = autoPerfTypeServiceManager.getAutoPerfBaseReportByType(autoPerfType) ;
        if(autoPerfBaseReportService == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.getBasePerfTask] autoPerfBaseReportService is null");
            return null ;
        }
        int start = (page -1) * size ;
        List<PerfBasePerfTaskInfoVO> perfBasePerfTaskInfoVOList = autoPerfBaseReportService.getBaseTaskInfoList(key,start,size) ;
        return perfBasePerfTaskInfoVOList ;
    }

    public PerfBaseReportListVO getPerfBaseReportList(AutoPerfType autoPerfType, int page, int size) {
        return null ;
    }

    public Long createNewPerfReport(PerfReportDataDTO perfReportDataDTO){
        return null ;
    }


    public PerfBaseReportDetailVO getPerfBaseReportDetail(Long reportId){
        return null ;
    }
}
