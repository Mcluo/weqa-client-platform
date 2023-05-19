package com.netease.vcloud.qa.service.perf.report;

import com.netease.vcloud.qa.dao.ClientPerfBaseLineDAO;
import com.netease.vcloud.qa.dao.ClientPerfReportDAO;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import com.netease.vcloud.qa.service.perf.data.PerfBaseLineDTO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseLineDetailVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseLineVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:59
 */
@Service
public class AutoPerfBaseLineService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("perfLog");

    @Autowired
    private ClientPerfReportDAO clientPerfReportDAO ;

    @Autowired
    private ClientPerfBaseLineDAO clientPerfBaseLineDAO ;

    @Autowired
    private AutoPerfTypeServiceManager autoPerfTypeServiceManager ;
    /**
     * 根据自动化类型，获取对应的基线列表
     * @param autoPerfType
     * @return
     */
    public List<PerfBaseLineVO> getPerfBaseLineList(AutoPerfType autoPerfType){
        return null ;
    }

    /**
     * 创建一个新的基线
     * @param perfBaseLineDTO
     * @return
     */
    public Long insertNewPerfBaseLine(PerfBaseLineDTO perfBaseLineDTO){
        return null ;
    }

    public PerfBaseLineDetailVO getPerfBaselineDetail(Long baseLineId){
        return null ;
    }

}
