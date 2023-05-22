package com.netease.vcloud.qa.service.perf.report;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientPerfBaseLineDAO;
import com.netease.vcloud.qa.dao.ClientPerfReportDAO;
import com.netease.vcloud.qa.model.ClientPerfBaseLineDO;
import com.netease.vcloud.qa.model.ClientPerfReportDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import com.netease.vcloud.qa.service.perf.data.PerfBaseLineDTO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseLineDetailVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseLineListVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseLineVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private UserInfoService userInfoService ;
    /**
     * 根据自动化类型，获取对应的基线列表
     * @param autoPerfType
     * @return
     */
    public PerfBaseLineListVO getPerfBaseLineList(AutoPerfType autoPerfType,int current,int size) throws AutoTestRunException {
        if(autoPerfType == null){
            TC_LOGGER.error("[AutoPerfBaseLineService.getPerfBaseLineList] autoPerfType is null");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        int start = (current - 1 ) * size ;
        List<ClientPerfBaseLineDO> clientPerfBaseLineDOList = clientPerfBaseLineDAO.queryClientPerfBaseLineList(autoPerfType.getCode(),start,size) ;
        int total = clientPerfBaseLineDAO.countClientPerfBaseLineDOList(autoPerfType.getCode()) ;
        PerfBaseLineListVO perfBaseLineListVO = new PerfBaseLineListVO() ;
        perfBaseLineListVO.setPage(current);
        perfBaseLineListVO.setSize(size);
        perfBaseLineListVO.setTotal(total);
        List<PerfBaseLineVO> baseLineList = new ArrayList<PerfBaseLineVO>() ;
        perfBaseLineListVO.setBaseLineList(baseLineList);
        if (!CollectionUtils.isEmpty(clientPerfBaseLineDOList)){
            Set<String> employeeSet = new HashSet<String>() ;
            for (ClientPerfBaseLineDO clientPerfBaseLineDO : clientPerfBaseLineDOList){
                if (clientPerfBaseLineDO!= null && StringUtils.isNotBlank(clientPerfBaseLineDO.getOwner())) {
                    employeeSet.add(clientPerfBaseLineDO.getOwner());
                }
            }
            Map<String , UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(employeeSet) ;
            for (ClientPerfBaseLineDO clientPerfBaseLineDO : clientPerfBaseLineDOList){
                PerfBaseLineVO perfBaseLineVO = new PerfBaseLineVO() ;
                perfBaseLineVO.setId(clientPerfBaseLineDO.getId());
                perfBaseLineVO.setName(clientPerfBaseLineDO.getBaseLineName());
                perfBaseLineVO.setCreateTime(clientPerfBaseLineDO.getGmtCreate().getTime());
                UserInfoBO userInfoBO = userInfoBOMap.get(clientPerfBaseLineDO.getOwner()) ;
                if (userInfoBO != null) {
                    perfBaseLineVO.setOwner(CommonUtils.buildUserInfoVOByBO(userInfoBO));
                }
                baseLineList.add(perfBaseLineVO) ;
            }
        }
        return perfBaseLineListVO ;
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
