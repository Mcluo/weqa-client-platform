package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSONArray;
import com.netease.vcloud.qa.auto.ScriptRunStatus;
import com.netease.vcloud.qa.auto.TaskRunStatus;
import com.netease.vcloud.qa.dao.*;
import com.netease.vcloud.qa.model.*;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import com.netease.vcloud.qa.service.auto.view.QsTaskInfoListVO;
import com.netease.vcloud.qa.service.auto.view.TaskInfoListVO;
import com.netease.vcloud.qa.service.auto.view.VcloudClientQsTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutoTestQsService {

    @Autowired
    private VcloudClientQsSceneInfoDAO sceneInfoDAO ;

    @Autowired
    private VcloudClientQsAppInfoDAO appInfoDAO ;

    @Autowired
    private VcloudClientQsTaskInfoDAO qsTaskInfoDAO;

    @Autowired
    private VcloudClientQsRelationInfoDAO qsRelationInfoDAO;

    @Autowired
    private AutoTestDeviceService autoTestDeviceService ;

    @Autowired
    private ClientAutoScriptRunInfoDAO tcInfoDAO;
    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO ;

    @Autowired
    private AutoTestTaskManagerService autoTestTaskManagerService;


    public int addQsTask(VcloudClientQsTaskDO qsTaskDO, List<Long> deviceList){
        List<DeviceInfoVO> deviceInfoVOList = autoTestDeviceService.getDeviceInfoList(deviceList) ;
        VcloudClientQsAppDO qsAppDO = appInfoDAO.selectByPrimaryKey(qsTaskDO.getQsAppId());
        if (deviceInfoVOList!=null) {
            //设备从DB读取后，需要重新排序
            Map<Long,DeviceInfoVO> deviceMap = new HashMap<Long,DeviceInfoVO>();
            List<DeviceInfoVO> saveDeviceInfoVOList = new ArrayList<DeviceInfoVO>() ;
            for (DeviceInfoVO deviceInfoVO:deviceInfoVOList){
                deviceMap.put(deviceInfoVO.getId(),deviceInfoVO) ;
            }
            for (Long deviceId : deviceList){
                DeviceInfoVO deviceInfo = deviceMap.get(deviceId) ;
                if (deviceInfo!=null) {
                    saveDeviceInfoVOList.add(deviceInfo);
                }
            }
            qsTaskDO.setDeviceInfo(JSONArray.toJSONString(saveDeviceInfoVOList));
        }
        qsTaskInfoDAO.insert(qsTaskDO);

        ClientAutoTaskInfoDO clientAutoTaskInfoDO = new ClientAutoTaskInfoDO();
        clientAutoTaskInfoDO.setTaskName(qsTaskDO.getTaskName());
        clientAutoTaskInfoDO.setTaskType("python");
        clientAutoTaskInfoDO.setOperator(qsTaskDO.getOperator());
        clientAutoTaskInfoDO.setDeviceType(qsTaskDO.getDeviceType());
        clientAutoTaskInfoDO.setDeviceInfo(qsTaskDO.getDeviceInfo());
        clientAutoTaskInfoDO.setGitInfo(qsTaskDO.getGitInfo());
        clientAutoTaskInfoDO.setGitBranch(qsTaskDO.getGitBranch());
        clientAutoTaskInfoDO.setTaskStatus(TaskRunStatus.INIT.getCode());
        clientAutoTaskInfoDAO.insertNewClientAutoTask(clientAutoTaskInfoDO);
        //
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = new ArrayList<ClientAutoScriptRunInfoDO>();
        List<VcloudClientQsSceneDO> sceneDOList = sceneInfoDAO.queryAutoRandQsSceneInfo(qsTaskDO.getQsAppId(), qsTaskDO.getStartTime(), qsTaskDO.getEndTime(), qsTaskDO.getSampleNum());
        if (sceneDOList.size() > 0){
            for (VcloudClientQsSceneDO qsSceneDO:sceneDOList){
                ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = new ClientAutoScriptRunInfoDO();
                clientAutoScriptRunInfoDO.setTaskId(clientAutoTaskInfoDO.getId());
                clientAutoScriptRunInfoDO.setScriptName(qsAppDO.getName() + "-回放测试");
                clientAutoScriptRunInfoDO.setScriptDetail("cid: " + qsSceneDO.getCid());
                clientAutoScriptRunInfoDO.setExecClass(qsAppDO.getExeccClass());
                clientAutoScriptRunInfoDO.setExecMethod(qsAppDO.getExeccMethod());
                clientAutoScriptRunInfoDO.setExecParam("'" + qsSceneDO.getCid() + "'" +  "," + qsSceneDO.getGmtCreate().getTime() + "," + "'" + qsAppDO.getTestAppKey() + "'");
                clientAutoScriptRunInfoDO.setExecStatus(ScriptRunStatus.INIT.getCode());
                clientAutoScriptRunInfoDOList.add(clientAutoScriptRunInfoDO);
            }
            tcInfoDAO.patchInsertAutoScript(clientAutoScriptRunInfoDOList) ;
        }
        VcloudClientQsRelationDO qsRelationDO = new VcloudClientQsRelationDO();
        qsRelationDO.setQsTaskId(qsTaskDO.getId());
        qsRelationDO.setAutoTaskId(clientAutoTaskInfoDO.getId());
        qsRelationInfoDAO.insert(qsRelationDO);
        autoTestTaskManagerService.setTaskReadySuccess(clientAutoTaskInfoDO.getId(),true);
        return (int) clientAutoTaskInfoDO.getId();
    }

    public VcloudClientQsTaskVO getTask(Long id1){
        VcloudClientQsTaskDO qsTaskDO = qsTaskInfoDAO.selectByPrimaryKey(id1);
        List<VcloudClientQsRelationDO> qsRelationDOList = qsRelationInfoDAO.selectByQsId(qsTaskDO.getId());
        VcloudClientQsTaskVO qsTaskVO =  new VcloudClientQsTaskVO();
        qsTaskVO.setQsTaskDO(qsTaskDO);
        qsTaskVO.setQsRelationDOList(qsRelationDOList);
        return qsTaskVO;
    }

    public List<VcloudClientQsSceneDO> getQsSceneInfo(Long appId, Date startTime, Date endTime){
        return sceneInfoDAO.queryAutoQsSceneInfo(appId, startTime, endTime);
    }

    public int getQsSceneCount(Long appId, Date startTime, Date endTime){
        return sceneInfoDAO.queryAutoQsSceneCount(appId, startTime, endTime);
    }

    public List<VcloudClientQsAppDO> getAppInfo(){
        return appInfoDAO.getAll();
    }

    public QsTaskInfoListVO getList(String owner, int pageSize , int pageNo){
        int start = (pageNo-1) * pageSize ;
        List<VcloudClientQsTaskDO> qsTaskDOList = qsTaskInfoDAO.queryAutoTaskInfo(owner, start, pageSize);
        int count = qsTaskInfoDAO.queryAutoTaskInfoCount(owner);
        QsTaskInfoListVO qsTaskInfoListVO = new QsTaskInfoListVO();
        qsTaskInfoListVO.setCurrent(pageNo);
        qsTaskInfoListVO.setSize(pageSize);
        qsTaskInfoListVO.setTotal(count);
        qsTaskInfoListVO.setTaskInfoList(qsTaskDOList);
        return qsTaskInfoListVO;
    }

}
