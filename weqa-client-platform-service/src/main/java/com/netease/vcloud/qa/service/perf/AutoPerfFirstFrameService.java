package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTestSuitRelationDAO;
import com.netease.vcloud.qa.dao.ClientPerfFirstFrameDataDAO;
import com.netease.vcloud.qa.dao.ClientPerfFirstFrameTaskDAO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitRelationDO;
import com.netease.vcloud.qa.model.ClientPerfFirstFrameDataDO;
import com.netease.vcloud.qa.model.ClientPerfFirstFrameTaskDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.AutoTestTaskManagerService;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.perf.data.*;
import com.netease.vcloud.qa.service.perf.report.AutoPerfBaseReportInterface;
import com.netease.vcloud.qa.service.perf.view.*;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/9 15:32
 */
@Service
public class AutoPerfFirstFrameService  implements AutoPerfBaseReportInterface {

    private static final Logger PERF_LOGGER = LoggerFactory.getLogger("perfLog");

    @Autowired
    private ClientPerfFirstFrameTaskDAO clientPerfFirstFrameTaskDAO ;

    @Autowired
    private ClientPerfFirstFrameDataDAO clientPerfFirstFrameDataDAO ;

    @Autowired
    private UserInfoService userInfoService ;

    @Autowired
    private AutoTestTaskManagerService autoTestTaskManagerService ;

    @Autowired
    private ClientAutoTestSuitRelationDAO clientAutoTestSuitRelationDAO ;

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;
    /**
     * 新建一个任务
     * @param firstFrameTaskDTO
     * @return
     */
    public Long createNewFirstFrame(FirstFrameTaskDTO firstFrameTaskDTO)throws AutoTestRunException {
        if (firstFrameTaskDTO==null) {
            PERF_LOGGER.error("[AutoPerfFirstFrameService.createNewFirstFrame]firstFrameTaskDTO is null");
//            return null;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO = this.buildFirstFrameTaskDOByDTO(firstFrameTaskDTO) ;
        if (clientPerfFirstFrameTaskDO == null){
            PERF_LOGGER.error("[AutoPerfFirstFrameService.createNewFirstFrame]clientPerfFirstFrameTaskDO is null");
//            return null ;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        int count = clientPerfFirstFrameTaskDAO.insertFirstFrameTask(clientPerfFirstFrameTaskDO) ;
        if (count <= 0){
//            return null ;
            PERF_LOGGER.error("[AutoPerfFirstFrameService.createNewFirstFrame]clientPerfFirstFrameTaskDO is null");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION) ;
        }
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = buildAutoTestTaskInfoDTOByAutoPerfTest(firstFrameTaskDTO) ;
        Long autoTaskID = autoTestTaskManagerService.addNewTaskInfo(autoTestTaskInfoDTO) ;
        if (autoTaskID !=null){
            //直接置为ready触发调度
            autoTestTaskManagerService.setTaskReadySuccess(autoTaskID,true);
        }
        Long id = clientPerfFirstFrameTaskDO.getId() ;
        clientPerfFirstFrameTaskDO.setAutoTaskId(autoTaskID);
        count = clientPerfFirstFrameTaskDAO.updateFirstFrameTaskInfo(clientPerfFirstFrameTaskDO) ;
        if (count<=0){
            PERF_LOGGER.error("[AutoPerfFirstFrameService.createNewFirstFrame]update clientPerfFirstFrameTaskDO is null");
        }
        return id ;
    }

    private AutoTestTaskInfoDTO buildAutoTestTaskInfoDTOByAutoPerfTest(FirstFrameTaskDTO firstFrameTaskDTO) throws AutoTestRunException {
        Long  suit = firstFrameTaskDTO.getSuitId();
        if (suit == null){
            PERF_LOGGER.error("[AutoPerfRunService.buildAutoTestTaskInfoDTOByAutoPerfTest] create testSuit is null ");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SUIT_IS_NOT_EXIST) ;
        }
        List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList = clientAutoTestSuitRelationDAO.getAutoTestSuitRelationListBySuit(suit) ;
        if (CollectionUtils.isEmpty(clientAutoTestSuitRelationDOList)){
            PERF_LOGGER.error("[AutoPerfRunService.buildAutoTestTaskInfoDTOByAutoPerfTest] create testSuit is empty ");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SCRIPT_ID_EMPTY) ;
        }
        List<Long> scriptIdList = new ArrayList<Long>() ;
        for (ClientAutoTestSuitRelationDO clientAutoTestSuitRelationDO :clientAutoTestSuitRelationDOList ){
            scriptIdList.add(clientAutoTestSuitRelationDO.getScriptId()) ;
        }
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setTaskName(firstFrameTaskDTO.getTaskName());
        autoTestTaskInfoDTO.setOperator(firstFrameTaskDTO.getOperator());
        autoTestTaskInfoDTO.setTestCaseScriptId(scriptIdList);
        autoTestTaskInfoDTO.setDeviceType((byte) 0);
        autoTestTaskInfoDTO.setGitInfo(firstFrameTaskDTO.getGitInfo());
        autoTestTaskInfoDTO.setGitBranch(firstFrameTaskDTO.getGitBranch());
        autoTestTaskInfoDTO.setDeviceList(firstFrameTaskDTO.getDeviceList());
        return autoTestTaskInfoDTO ;
    }

    private ClientPerfFirstFrameTaskDO buildFirstFrameTaskDOByDTO(FirstFrameTaskDTO firstFrameTaskDTO){
        ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO = new ClientPerfFirstFrameTaskDO() ;
        if (firstFrameTaskDTO.getId()!=null) {
            clientPerfFirstFrameTaskDO.setId(firstFrameTaskDTO.getId());
        }
        clientPerfFirstFrameTaskDO.setTaskName(firstFrameTaskDTO.getTaskName());
        clientPerfFirstFrameTaskDO.setOwner(firstFrameTaskDTO.getOperator());
        clientPerfFirstFrameTaskDO.setDeviceInfo(firstFrameTaskDTO.getDeviceInfo());
        return clientPerfFirstFrameTaskDO ;
    }


    public FirstFrameListVO queryFirstFrame(String owner , int current , int size){
        FirstFrameListVO firstFrameListVO = new FirstFrameListVO() ;
        firstFrameListVO.setCurrent(current);
        firstFrameListVO.setSize(size);
        int start = (current - 1 ) * size ;
        List<ClientPerfFirstFrameTaskDO> clientPerfFirstFrameTaskDOList = clientPerfFirstFrameTaskDAO.queryClientPerfFirstFrameTask(owner,start,size) ;
        int count = clientPerfFirstFrameTaskDAO.getClientPerfFirstFrameTaskCount(owner) ;
        List<FirstFrameBaseInfoVO> firstFrameBaseInfoVOList = new ArrayList<>() ;
        if (!CollectionUtils.isEmpty(clientPerfFirstFrameTaskDOList)){
            Set<String> userSet = new HashSet<String>() ;
            for (ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO : clientPerfFirstFrameTaskDOList){
                userSet.add(clientPerfFirstFrameTaskDO.getOwner()) ;
            }
            Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(userSet) ;
            for (ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO : clientPerfFirstFrameTaskDOList){
                FirstFrameBaseInfoVO firstFrameBaseInfoVO = this.buildFirstFrameBaseInfoVOByDO(clientPerfFirstFrameTaskDO ,userInfoBOMap);
                if (firstFrameBaseInfoVO==null){
                    continue;
                }
                firstFrameBaseInfoVOList.add(firstFrameBaseInfoVO) ;
            }
        }
        firstFrameListVO.setTotal(count);
        firstFrameListVO.setList(firstFrameBaseInfoVOList);
        return firstFrameListVO ;
    }


    private FirstFrameBaseInfoVO buildFirstFrameBaseInfoVOByDO(ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO , Map<String, UserInfoBO> userInfoBOMap){
        if(clientPerfFirstFrameTaskDO == null) {
            PERF_LOGGER.error("[AutoPerfFirstFrameService.buildFirstFrameBaseInfoVOByDO]clientPerfFirstFrameTaskDO is null");
            return null;
        }
        FirstFrameBaseInfoVO firstFrameBaseInfoVO = new FirstFrameBaseInfoVO() ;
        firstFrameBaseInfoVO.setId(clientPerfFirstFrameTaskDO.getId());
        if(clientPerfFirstFrameTaskDO.getGmtCreate()!=null) {
            firstFrameBaseInfoVO.setCreateTime(clientPerfFirstFrameTaskDO.getGmtCreate().getTime());
        }
        firstFrameBaseInfoVO.setTaskName(clientPerfFirstFrameTaskDO.getTaskName());
        firstFrameBaseInfoVO.setDeviceInfo(clientPerfFirstFrameTaskDO.getDeviceInfo());
        if (userInfoBOMap != null) {
            UserInfoBO userInfoBO = userInfoBOMap.get(clientPerfFirstFrameTaskDO.getOwner());
            UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO);
            firstFrameBaseInfoVO.setOwner(userInfoVO);
        }
        return firstFrameBaseInfoVO ;
    }


    /**
     * 获取详情
     * @param id
     * @return
     */
    public FirstFrameDetailInfoVO getFirstFrameDetailInfoVO(Long id){
        ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO = clientPerfFirstFrameTaskDAO.getClientPerfFirstFrameTaskById(id) ;
        if (clientPerfFirstFrameTaskDO == null){
            PERF_LOGGER.error("[AutoPerfFirstFrameService.getFirstFrameDetailInfoVO]clientPerfFirstFrameTaskDO is null");
            return null ;
        }
        FirstFrameDetailInfoVO firstFrameDetailInfoVO = new FirstFrameDetailInfoVO() ;
        //基础信息
        FirstFrameBaseInfoVO firstFrameBaseInfoVO = this.buildFirstFrameBaseInfoVOByDO(clientPerfFirstFrameTaskDO , null) ;
        UserInfoBO userInfoBO = userInfoService.getUserInfoByEmail(clientPerfFirstFrameTaskDO.getOwner()) ;
        if (userInfoBO != null){
            UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO) ;
            firstFrameBaseInfoVO.setOwner(userInfoVO);
        }
        firstFrameDetailInfoVO.setBaseInfo(firstFrameBaseInfoVO);
        //数据信息
        List<ClientPerfFirstFrameDataDO> clientPerfFirstFrameDataDOList = clientPerfFirstFrameDataDAO.getTaskFirstFrameData(id) ;
        List<FirstFrameDataInfoVO> firstFrameDataInfoVOList = this.buildFirstFrameDataInfoVOByDO(clientPerfFirstFrameDataDOList) ;
        firstFrameDetailInfoVO.setDataInfo(firstFrameDataInfoVOList);
        //自动化信息
        if (clientPerfFirstFrameTaskDO.getAutoTaskId() != null){
            firstFrameDetailInfoVO.setAutoId(clientPerfFirstFrameTaskDO.getAutoTaskId());
            ClientAutoTaskInfoDO clientAutoTaskInfoDO = clientAutoTaskInfoDAO.getClientAutoTaskInfoById(clientPerfFirstFrameTaskDO.getAutoTaskId()) ;
            if (clientAutoTaskInfoDO != null){
                TaskRunStatus taskRunStatus = TaskRunStatus.getTaskRunStatusByCode(clientAutoTaskInfoDO.getTaskStatus()) ;
                if (taskRunStatus != null){
                    firstFrameDetailInfoVO.setAutoStatus(taskRunStatus.getStatus());
                }
            }
        }
        return  firstFrameDetailInfoVO ;
    }

    private  List<FirstFrameDataInfoVO> buildFirstFrameDataInfoVOByDO( List<ClientPerfFirstFrameDataDO> clientPerfFirstFrameDataDOList){
        List<FirstFrameDataInfoVO> firstFrameDataInfoVOList = new ArrayList<FirstFrameDataInfoVO>() ;
        if (CollectionUtils.isEmpty(clientPerfFirstFrameDataDOList)) {
            return  firstFrameDataInfoVOList ;
        }
        Map<FirstFrameType,List<Long>> firstFrameMap = new HashMap<FirstFrameType, List<Long>>() ;
        for (ClientPerfFirstFrameDataDO clientPerfFirstFrameDataDO : clientPerfFirstFrameDataDOList){
            FirstFrameType  firstFrameType = FirstFrameType.getFirstFrameByCode(clientPerfFirstFrameDataDO.getType()) ;
            List<Long> dataList = firstFrameMap.get(firstFrameType) ;
            if (dataList == null){
                dataList = new ArrayList<Long>() ;
                firstFrameMap.put(firstFrameType,dataList) ;
            }
            dataList.add(clientPerfFirstFrameDataDO.getFirstFrameData()) ;
        }
        for (Map.Entry<FirstFrameType,List<Long>> entry : firstFrameMap.entrySet()){
            FirstFrameType firstFrameType = entry.getKey() ;
            List<Long> dataList = entry.getValue() ;
            int count = dataList.size()  ;
            Long total = 0L ;
            for (Long data : dataList){
                total += data ;
            }
            Long avg = total / count ;
            FirstFrameDataInfoVO firstFrameDataInfoVO = new FirstFrameDataInfoVO() ;
            firstFrameDataInfoVO.setType(firstFrameType.getType());
            firstFrameDataInfoVO.setDetail(dataList);
            firstFrameDataInfoVO.setCount(count);
            firstFrameDataInfoVO.setAvg(avg);
            firstFrameDataInfoVOList.add(firstFrameDataInfoVO) ;
        }
        return firstFrameDataInfoVOList ;
    }

    /**
     * 添加一个数据
     * @param firstFrameDataDTO
     * @return
     */
    public boolean addFirstFrameTaskData(FirstFrameDataDTO firstFrameDataDTO){
        if (firstFrameDataDTO == null) {
            PERF_LOGGER.error("[AutoPerfFirstFrameService.addFirstFrameTaskData]firstFrameDataDTO is null");
            return false;
        }
        List<ClientPerfFirstFrameDataDO> clientPerfFirstFrameDataDOList = new ArrayList<ClientPerfFirstFrameDataDO>() ;
        List<Long> dataList = firstFrameDataDTO.getData() ;
        FirstFrameType frameType = FirstFrameType.getFirstFrameByName(firstFrameDataDTO.getType()) ;
        if (frameType == null){
            PERF_LOGGER.error("[AutoPerfFirstFrameService.addFirstFrameTaskData]frameType is null");
            return false ;
        }
        for (Long data : dataList){
            ClientPerfFirstFrameDataDO clientPerfFirstFrameDataDO = new ClientPerfFirstFrameDataDO() ;
            clientPerfFirstFrameDataDO.setTaskId(firstFrameDataDTO.getTaskId());
            clientPerfFirstFrameDataDO.setType(frameType.getCode());
            clientPerfFirstFrameDataDO.setFirstFrameData(data);
            clientPerfFirstFrameDataDOList.add(clientPerfFirstFrameDataDO) ;
        }
        int count = clientPerfFirstFrameDataDAO.patchInsertFirstFrameData(clientPerfFirstFrameDataDOList) ;
        if (count >= clientPerfFirstFrameDataDOList.size()){
            return true ;
        }else {
            return false;
        }
    }

    @Override
    public List<PerfBasePerfTaskInfoVO> getBaseTaskInfoList(String query, int start, int size) {
        List<ClientPerfFirstFrameTaskDO> clientPerfFirstFrameTaskDOList = clientPerfFirstFrameTaskDAO.queryClientPerfFirstFrameTaskByKey(query,start,size) ;
        List<PerfBasePerfTaskInfoVO> perfBasePerfTaskInfoVOS = new ArrayList<PerfBasePerfTaskInfoVO>() ;
        if (clientPerfFirstFrameTaskDOList != null){
            for (ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO :clientPerfFirstFrameTaskDOList){
                if (clientPerfFirstFrameTaskDO == null){
                    continue;
                }
                PerfBasePerfTaskInfoVO perfTaskInfoVO = new PerfBasePerfTaskInfoVO() ;
                perfTaskInfoVO.setId(clientPerfFirstFrameTaskDO.getId());
                perfTaskInfoVO.setName(clientPerfFirstFrameTaskDO.getTaskName());
                perfTaskInfoVO.setType(AutoPerfType.FIRST_FRAME.getName());
                perfBasePerfTaskInfoVOS.add(perfTaskInfoVO);
            }
        }
        return perfBasePerfTaskInfoVOS;
    }

    @Override
    public AutoPerfBaseReportResultDataInterface buildAutoPerfBaseReportResultData(List<Long> taskIdList, String baselineResultData) {
        return null;
    }
}
