package com.netease.vcloud.qa.service.perf.report;

import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientPerfBaseLineDAO;
import com.netease.vcloud.qa.dao.ClientPerfReportDAO;
import com.netease.vcloud.qa.model.ClientPerfBaseLineDO;
import com.netease.vcloud.qa.model.ClientPerfReportDO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.perf.AutoPerfType;
import com.netease.vcloud.qa.service.perf.data.AutoPerfBaseReportResultDataInterface;
import com.netease.vcloud.qa.service.perf.data.PerfReportDataDTO;
import com.netease.vcloud.qa.service.perf.view.PerfBasePerfTaskInfoVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportDetailVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportListVO;
import com.netease.vcloud.qa.service.perf.view.PerfBaseReportVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private UserInfoService userInfoService ;
    /**
     * 根据类型，获取基本的任务信息
     * @param autoPerfType
     * @param key
     * @param page
     * @param size
     * @return
     */
    public List<PerfBasePerfTaskInfoVO> getBasePerfTask(AutoPerfType autoPerfType, String key, int page, int size)throws AutoTestRunException{
        if (autoPerfType == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.getBasePerfTask] autoPerfType is null");
//            return null ;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        AutoPerfBaseReportInterface autoPerfBaseReportService = autoPerfTypeServiceManager.getAutoPerfBaseReportByType(autoPerfType) ;
        if(autoPerfBaseReportService == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.getBasePerfTask] autoPerfBaseReportService is null");
//            return null ;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        int start = (page -1) * size ;
        List<PerfBasePerfTaskInfoVO> perfBasePerfTaskInfoVOList = autoPerfBaseReportService.getBaseTaskInfoList(key,start,size) ;
        return perfBasePerfTaskInfoVOList ;
    }

    /**
     * 获取报告列表
     * @param autoPerfType
     * @param page
     * @param size
     * @return
     */
    public PerfBaseReportListVO getPerfBaseReportList(AutoPerfType autoPerfType, int page, int size) throws AutoTestRunException {
//        if(autoPerfType == null){
//            TC_LOGGER.error("[AutoPerfBaseReportService.getPerfBaseReportList] autoPerfType is null");
//            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
//        }
        int start = (page - 1) * size ;
        List<ClientPerfReportDO> clientPerfReportDOList = null ;
        int total = 0 ;
        if (autoPerfType != null) {
            clientPerfReportDOList = clientPerfReportDAO.queryClientPerfReportList(autoPerfType.getCode(), start, size);
            total = clientPerfReportDAO.countClientPerfReportList(autoPerfType.getCode());
        }else {
            clientPerfReportDOList = clientPerfReportDAO.queryClientPerfReportList(null, start, size);
            total = clientPerfReportDAO.countClientPerfReportList(null);
        }
        PerfBaseReportListVO perfBaseReportListVO = new PerfBaseReportListVO() ;
        perfBaseReportListVO.setPage(page);
        perfBaseReportListVO.setSize(size);
        perfBaseReportListVO.setTotal(total);
        List<PerfBaseReportVO> perfBaseReportVOList = new ArrayList<PerfBaseReportVO>() ;
        perfBaseReportListVO.setReportList(perfBaseReportVOList);
        if (!CollectionUtils.isEmpty(clientPerfReportDOList)){
            Set<String> employeeSet = new HashSet<String>() ;
            for(ClientPerfReportDO clientPerfReportDO : clientPerfReportDOList){
                if (clientPerfReportDO == null&& StringUtils.isNotBlank(clientPerfReportDO.getOwner())){
                    employeeSet.add(clientPerfReportDO.getOwner()) ;
                }
            }
            Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(employeeSet) ;
            for(ClientPerfReportDO clientPerfReportDO : clientPerfReportDOList){
                if (clientPerfReportDO == null){
                    continue;
                }
                PerfBaseReportVO perfBaseReportVO = new PerfBaseReportVO() ;

                perfBaseReportVO.setId(clientPerfReportDO.getId());
                perfBaseReportVO.setReportName(clientPerfReportDO.getReportName());
                perfBaseReportVO.setCreateTime(clientPerfReportDO.getGmtCreate().getTime());
                AutoPerfType reportType = AutoPerfType.getAutoPerfTypeByCode(clientPerfReportDO.getReportType()) ;
                if (reportType!=null) {
                    perfBaseReportVO.setReportType(reportType.getName());
                }
                UserInfoBO userInfoBO = userInfoBOMap.get(clientPerfReportDO.getOwner()) ;
                if (userInfoBO != null){
                    perfBaseReportVO.setOperator(CommonUtils.buildUserInfoVOByBO(userInfoBO));
                }
                perfBaseReportVOList.add(perfBaseReportVO) ;
            }
        }
        return perfBaseReportListVO ;
    }

    public Long createNewPerfReport(PerfReportDataDTO perfReportDataDTO) throws AutoTestRunException{
        if (perfReportDataDTO == null||CollectionUtils.isEmpty(perfReportDataDTO.getTaskList())){
            TC_LOGGER.error("[AutoPerfBaseReportService.createNewPerfReport] perf ReportDataDTO is null");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        String type = perfReportDataDTO.getType() ;
        AutoPerfType autoPerfType = AutoPerfType.getAutoPerfTypeByName(type) ;
        if (autoPerfType == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.createNewPerfReport]autoPerfType is null");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        AutoPerfBaseReportInterface autoPerfBaseReportService = autoPerfTypeServiceManager.getAutoPerfBaseReportByType(autoPerfType) ;
        if (autoPerfBaseReportService == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.createNewPerfReport]autoPerfBaseReportService is null");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        Long baseLineId = perfReportDataDTO.getBaseLine() ;
        String baseLineData = null ;
        if (baseLineId != null) {
            ClientPerfBaseLineDO clientPerfBaseLineDO = clientPerfBaseLineDAO.getClientPerfBaseLineDOById(baseLineId) ;
            if (clientPerfBaseLineDO != null && clientPerfBaseLineDO.getBaseLineType()==autoPerfType.getCode()){
                baseLineData = clientPerfBaseLineDO.getResultData() ;
            }
        }
        AutoPerfBaseReportResultDataInterface autoPerfBaseReportResultData =autoPerfBaseReportService.buildAutoPerfBaseReportResultData(perfReportDataDTO.getTaskList(),baseLineData) ;
        if (autoPerfBaseReportResultData == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.createNewPerfReport]autoPerfBaseReportResultData build fail");
            throw  new AutoTestRunException(AutoTestRunException.BUILD_REPORT_RESULT_FAIL) ;
        }
        ClientPerfReportDO clientPerfReportDO = new ClientPerfReportDO() ;
        clientPerfReportDO.setReportName(perfReportDataDTO.getName());
        clientPerfReportDO.setReportType(autoPerfType.getCode());
        clientPerfReportDO.setOwner(perfReportDataDTO.getOwner());
        clientPerfReportDO.setRelationBase(perfReportDataDTO.getBaseLine());
        StringBuilder stringBuilder = new StringBuilder() ;
        for (Long taskId : perfReportDataDTO.getTaskList()){
            stringBuilder.append(taskId).append("|") ;
        }
        clientPerfReportDO.setRelationTask(stringBuilder.toString());
        clientPerfReportDO.setResultData(JSONObject.toJSONString(autoPerfBaseReportResultData));
        int count = clientPerfReportDAO.insertClientPerfReport(clientPerfReportDO) ;
        if (count > 0){
            return clientPerfReportDO.getId() ;
        }else {
            return null;
        }
    }

    public PerfBaseReportDetailVO getPerfBaseReportDetail(Long reportId) throws AutoTestRunException{
        if (reportId == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.getPerfBaseReportDetail] param exception");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientPerfReportDO clientPerfReportDO = clientPerfReportDAO.getClientPerfReportDOById(reportId) ;
        if (clientPerfReportDO == null){
            TC_LOGGER.error("[AutoPerfBaseReportService.getPerfBaseReportDetail] clientPerfReportDO is not exist");
            throw new AutoTestRunException(AutoTestRunException.TEST_REPORT_IS_NOT_EXIST) ;
        }
        PerfBaseReportDetailVO perfBaseReportDetailVO = new PerfBaseReportDetailVO() ;
        perfBaseReportDetailVO.setName(clientPerfReportDO.getReportName());
        perfBaseReportDetailVO.setTime(clientPerfReportDO.getGmtCreate().getTime());
        AutoPerfType autoPerfType = AutoPerfType.getAutoPerfTypeByCode(clientPerfReportDO.getReportType()) ;
        if (autoPerfType!=null) {
            perfBaseReportDetailVO.setType(autoPerfType.getName());
        }
        UserInfoBO userInfoBO = userInfoService.getUserInfoByEmail(clientPerfReportDO.getOwner()) ;
        if (userInfoBO != null) {
            perfBaseReportDetailVO.setOwner(CommonUtils.buildUserInfoVOByBO(userInfoBO));
        }
        AutoPerfBaseReportInterface autoPerfBaseReportService = autoPerfTypeServiceManager.getAutoPerfBaseReportByType(autoPerfType) ;
        if (autoPerfBaseReportService!=null) {
            AutoPerfBaseReportResultDataInterface autoPerfBaseReportResultData = autoPerfBaseReportService.buildResultVO(clientPerfReportDO.getResultData());
            perfBaseReportDetailVO.setResult(autoPerfBaseReportResultData);
        }
        return perfBaseReportDetailVO ;
    }
}
