package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.dao.ClientAutoTestSuitBaseInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTestSuitRelationDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoPerfTaskDAO;
import com.netease.vcloud.qa.model.ClientAutoTaskExtendInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitBaseInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTestSuitRelationDO;
import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import com.netease.vcloud.qa.service.auto.AutoTestRunException;
import com.netease.vcloud.qa.service.auto.AutoTestTaskManagerService;
import com.netease.vcloud.qa.service.auto.data.AutoTestTaskInfoDTO;
import com.netease.vcloud.qa.service.auto.view.TestSuitBaseInfoVO;
import com.netease.vcloud.qa.service.perf.data.AutoPerfTaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 性能测试的运行服务
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/27 14:21
 */
@Service
public class AutoPerfRunService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    @Autowired
    private AutoTestTaskManagerService autoTestTaskManagerService ;

    @Autowired
    private VcloudClientAutoPerfTaskDAO clientAutoPerfTaskDAO;

    @Autowired
    private ClientAutoTestSuitBaseInfoDAO clientAutoTestSuitBaseInfoDAO ;

    @Autowired
    private ClientAutoTestSuitRelationDAO clientAutoTestSuitRelationDAO ;


    public Long createNewPerfTest(AutoPerfTaskDTO autoPerfTaskDTO,String operator)  throws AutoTestRunException {
        VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO = buildClientAutoPerfTaskDOByDTO(autoPerfTaskDTO) ;
        //任务落库
        if (clientAutoPerfTaskDO == null){
            TC_LOGGER.error("[AutoPerfRunService.createNewPerfTest] clientAutoPerfTaskDO is null");
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        int count = clientAutoPerfTaskDAO.insert(clientAutoPerfTaskDO);
        if (count <= 0 || clientAutoPerfTaskDO.getId() == null){
            TC_LOGGER.error("[AutoPerfRunService.createNewPerfTest] create perfTask Id is null ");
            return  null ;
        }
        //创建自动化任务
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = this.buildAutoTestTaskInfoDTOByAutoPerfTest(autoPerfTaskDTO,operator) ;
        Long autoTaskID = autoTestTaskManagerService.addNewTaskInfo(autoTestTaskInfoDTO) ;
        Integer id = clientAutoPerfTaskDO.getId() ;
        count = clientAutoPerfTaskDAO.updatePerfTestAutoCase(id,autoTaskID) ;
        if (count <= 0){
            TC_LOGGER.error("[AutoPerfRunService.createNewPerfTest] bind auto and perf fail");
        }
        return id.longValue() ;
    }

    private AutoTestTaskInfoDTO buildAutoTestTaskInfoDTOByAutoPerfTest(AutoPerfTaskDTO autoPerfTaskDTO,String operator) throws AutoTestRunException{
        Long  suit = autoPerfTaskDTO.getSuitId();
        if (suit == null){
            TC_LOGGER.error("[AutoPerfRunService.buildAutoTestTaskInfoDTOByAutoPerfTest] create testSuit is null ");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SUIT_IS_NOT_EXIST) ;
        }
        List<ClientAutoTestSuitRelationDO> clientAutoTestSuitRelationDOList = clientAutoTestSuitRelationDAO.getAutoTestSuitRelationListBySuit(suit) ;
        if (CollectionUtils.isEmpty(clientAutoTestSuitRelationDOList)){
            TC_LOGGER.error("[AutoPerfRunService.buildAutoTestTaskInfoDTOByAutoPerfTest] create testSuit is empty ");
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_SCRIPT_ID_EMPTY) ;
        }
        List<Long> scriptIdList = new ArrayList<Long>() ;
        for (ClientAutoTestSuitRelationDO clientAutoTestSuitRelationDO :clientAutoTestSuitRelationDOList ){
            scriptIdList.add(clientAutoTestSuitRelationDO.getScriptId()) ;
        }
        AutoTestTaskInfoDTO autoTestTaskInfoDTO = new AutoTestTaskInfoDTO() ;
        autoTestTaskInfoDTO.setTaskType("python");
        autoTestTaskInfoDTO.setTaskName(autoPerfTaskDTO.getName());
        autoTestTaskInfoDTO.setOperator(operator);
        autoTestTaskInfoDTO.setTestCaseScriptId(scriptIdList);
        autoTestTaskInfoDTO.setDeviceType((byte) 0);
        autoTestTaskInfoDTO.setGitInfo(autoPerfTaskDTO.getGitInfo());
        autoTestTaskInfoDTO.setGitBranch(autoPerfTaskDTO.getGitBranch());
        autoTestTaskInfoDTO.setDeviceList(autoPerfTaskDTO.getDeviceList());
        return autoTestTaskInfoDTO ;
    }


    private VcloudClientAutoPerfTaskDO buildClientAutoPerfTaskDOByDTO(AutoPerfTaskDTO autoPerfTaskDTO){
        if (autoPerfTaskDTO == null) {
            return null;
        }
        VcloudClientAutoPerfTaskDO vcloudClientAutoPerfTaskDO = new VcloudClientAutoPerfTaskDO() ;
        vcloudClientAutoPerfTaskDO.setName(autoPerfTaskDTO.getName());
        vcloudClientAutoPerfTaskDO.setUser(autoPerfTaskDTO.getUser());
        vcloudClientAutoPerfTaskDO.setDevicesplatform(autoPerfTaskDTO.getDevicesPlatform());
        vcloudClientAutoPerfTaskDO.setDevicesmodel(autoPerfTaskDTO.getDevicesModel());
        vcloudClientAutoPerfTaskDO.setDevicesversion(autoPerfTaskDTO.getDevicesVersion()) ;
        vcloudClientAutoPerfTaskDO.setCpuinfo(autoPerfTaskDTO.getCpuInfo());
        vcloudClientAutoPerfTaskDO.setSdkinfo(autoPerfTaskDTO.getSdkInfo());
        vcloudClientAutoPerfTaskDO.setSdkversion(autoPerfTaskDTO.getSdkVersion());
        return vcloudClientAutoPerfTaskDO ;
    }


    /**
     * 读取性能性能相关的执行集
     */
    public List<TestSuitBaseInfoVO> queryPerfTestSuitList(){
        List<TestSuitBaseInfoVO> testSuitBaseInfoVOList = new ArrayList<TestSuitBaseInfoVO>() ;
        List<ClientAutoTestSuitBaseInfoDO> clientAutoTestSuitBaseInfoDOList = clientAutoTestSuitBaseInfoDAO.queryAutoTestSuitByName(null,null) ;
        if (CollectionUtils.isEmpty(clientAutoTestSuitBaseInfoDOList)){
            return testSuitBaseInfoVOList ;
        }
        for (ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO : clientAutoTestSuitBaseInfoDOList){
            if(clientAutoTestSuitBaseInfoDO == null || clientAutoTestSuitBaseInfoDO.getTag() == null){
                continue;
            }
            long tag = clientAutoTestSuitBaseInfoDO.getTag();
            if ((tag & 1) > 0){
                //性能测试用例
                TestSuitBaseInfoVO testSuitBaseInfoVO = new TestSuitBaseInfoVO() ;
                testSuitBaseInfoVO.setId(clientAutoTestSuitBaseInfoDO.getId());
                testSuitBaseInfoVO.setName(clientAutoTestSuitBaseInfoDO.getSuitName());
                testSuitBaseInfoVOList.add(testSuitBaseInfoVO) ;
            }
        }
        return testSuitBaseInfoVOList ;
    }


    public boolean  markPerfTestSuitById(Long suitId){
        if (suitId == null){
            TC_LOGGER.error("[AutoPerfRunService.markPerfTestSuitById] suitId is null");
            return false ;
        }
        ClientAutoTestSuitBaseInfoDO clientAutoTestSuitBaseInfoDO = clientAutoTestSuitBaseInfoDAO.getAutoTestSuitById(suitId) ;
        if (clientAutoTestSuitBaseInfoDO == null){
            TC_LOGGER.error("[AutoPerfRunService.markPerfTestSuitById] suit is not exist");
            return false ;
        }
        long tag = clientAutoTestSuitBaseInfoDO.getTag() == null ?0L:clientAutoTestSuitBaseInfoDO.getTag();
        tag = tag | 1 ;
        clientAutoTestSuitBaseInfoDO.setTag(tag);
        int count = clientAutoTestSuitBaseInfoDAO.updateAutoTestSuit(clientAutoTestSuitBaseInfoDO) ;
        if (count>0){
            return true;
        }else {
            return false;
        }
    }
}
