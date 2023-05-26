package com.netease.vcloud.qa.service.perf.report;

import com.netease.vcloud.qa.service.perf.AutoPerfFirstFrameService;
import com.netease.vcloud.qa.service.perf.AutoPerfResultService;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 17:03
 */
@Service
public class AutoPerfTypeServiceManager {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("perfLog");

    @Autowired
    private AutoPerfFirstFrameService autoPerfFirstFrameService ;

    @Autowired
    private AutoPerfResultService  autoPerfResultService ;

    private Map<AutoPerfType,AutoPerfBaseReportInterface>  baseReportServiceMap ;

    @PostConstruct
    public void init(){
        baseReportServiceMap = new HashMap<AutoPerfType, AutoPerfBaseReportInterface>() ;
        baseReportServiceMap.put(AutoPerfType.NORMAL,autoPerfResultService) ;
        baseReportServiceMap.put(AutoPerfType.FIRST_FRAME,autoPerfFirstFrameService) ;
    }

    public AutoPerfBaseReportInterface getAutoPerfBaseReportByType(AutoPerfType autoPerfType){
        if (baseReportServiceMap == null){
            return null ;
        }
        return baseReportServiceMap.get(autoPerfType) ;
    }

}
