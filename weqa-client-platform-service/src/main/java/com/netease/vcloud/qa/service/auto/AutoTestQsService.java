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

    @Autowired
    private VcloudClientQsApiInfoDAO clientQsApiInfoDAO;

    @Autowired
    private VcloudClientQsTypicalSceneInfoDAO typicalSceneInfoDAO;

    public int addQsTask(VcloudClientQsTaskDO qsTaskDO, List<Long> deviceList){
        List<DeviceInfoVO> deviceInfoVOList = autoTestDeviceService.getDeviceInfoList(deviceList) ;
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
        this.patchInsertAutoScript(qsTaskDO, clientAutoTaskInfoDO.getId());
        this.insertQsRelationInfo(qsTaskDO, clientAutoTaskInfoDO.getId());
        autoTestTaskManagerService.setTaskReadySuccess(clientAutoTaskInfoDO.getId(),true);
        return (int) clientAutoTaskInfoDO.getId();
    }



    public void insertQsRelationInfo(VcloudClientQsTaskDO qsTaskDO, Long taskId){
        VcloudClientQsRelationDO qsRelationDO = new VcloudClientQsRelationDO();
        qsRelationDO.setQsTaskId(qsTaskDO.getId());
        qsRelationDO.setAutoTaskId(taskId);
        qsRelationInfoDAO.insert(qsRelationDO);
    }

    public void patchInsertAutoScript(VcloudClientQsTaskDO qsTaskDO, Long taskId){
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = new ArrayList<ClientAutoScriptRunInfoDO>();
        List<VcloudClientQsSceneDO> sceneDOList = sceneInfoDAO.queryAutoRandQsSceneInfo(qsTaskDO.getQsAppId(), qsTaskDO.getStartTime(), qsTaskDO.getEndTime(), qsTaskDO.getSampleNum());
        VcloudClientQsAppDO qsAppDO = appInfoDAO.selectByPrimaryKey(qsTaskDO.getQsAppId());
        List<VcloudClientQsTypicalSceneInfoDO> typicalSceneInfoDOList = this.queryAutoRandQsTypicalSceneInfo(qsTaskDO);

        for (VcloudClientQsSceneDO qsSceneDO : sceneDOList) {
            ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = this.createScriptRunInfoDO(taskId,
                                                                                    qsAppDO.getName(),
                                                                                    qsAppDO.getExeccClass(),
                                                                                    qsAppDO.getExeccMethod(),
                                                                                    qsSceneDO.getCid(),
                                                                                    qsSceneDO.getGmtCreate().getTime(),
                                                                                    qsAppDO.getTestAppKey());
            clientAutoScriptRunInfoDOList.add(clientAutoScriptRunInfoDO);
        }
        for (VcloudClientQsTypicalSceneInfoDO typicalSceneInfoDO:typicalSceneInfoDOList){
            ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = this.createScriptRunInfoDO(taskId,
                                                                                    qsAppDO.getName(),
                                                                                    qsAppDO.getExeccClass(),
                                                                                    qsAppDO.getExeccMethod(),
                                                                                    typicalSceneInfoDO.getCid(),
                                                                                    typicalSceneInfoDO.getGmtCreate().getTime(),
                                                                                    qsAppDO.getTestAppKey());
            clientAutoScriptRunInfoDOList.add(clientAutoScriptRunInfoDO);
        }
        tcInfoDAO.patchInsertAutoScript(clientAutoScriptRunInfoDOList) ;

    }

    public ClientAutoScriptRunInfoDO createScriptRunInfoDO(Long taskId, String name, String class1, String method1, String cid, long time1, String appKey){
        ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO = new ClientAutoScriptRunInfoDO();
        clientAutoScriptRunInfoDO.setTaskId(taskId);
        clientAutoScriptRunInfoDO.setScriptName(name + "-回放测试");
        clientAutoScriptRunInfoDO.setScriptDetail("cid: " + cid);
        clientAutoScriptRunInfoDO.setExecClass(class1);
        clientAutoScriptRunInfoDO.setExecMethod(method1);
        clientAutoScriptRunInfoDO.setExecParam("'" + cid + "'" +  "," + "'" + appKey + "'");
        clientAutoScriptRunInfoDO.setExecStatus(ScriptRunStatus.INIT.getCode());
        return clientAutoScriptRunInfoDO;
    }

    public int addQsStart(long id){
        VcloudClientQsTaskDO qsTaskDO = qsTaskInfoDAO.selectByPrimaryKey(id);
        VcloudClientQsAppDO qsAppDO = appInfoDAO.selectByPrimaryKey(qsTaskDO.getQsAppId());

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
        this.patchInsertAutoScript(qsTaskDO, clientAutoTaskInfoDO.getId());
        this.insertQsRelationInfo(qsTaskDO, clientAutoTaskInfoDO.getId());
        autoTestTaskManagerService.setTaskReadySuccess(clientAutoTaskInfoDO.getId(),true);
        return (int) clientAutoTaskInfoDO.getId();
    }

    public int addQsStart1(long id){
        VcloudClientQsTaskDO qsTaskDO = qsTaskInfoDAO.selectByPrimaryKey(id);
        this.queryAutoRandQsTypicalSceneInfo(qsTaskDO);
        return 1;
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

    public List<VcloudClientQsApiInfoDO> getApiInfo(String cid){
        return clientQsApiInfoDAO.selectByCid(cid);
    }

    public List<VcloudClientQsTypicalSceneInfoDO> queryAutoRandQsTypicalSceneInfo(VcloudClientQsTaskDO qsTaskDO){
        ArrayList<VcloudClientQsTypicalSceneInfoDO> typicalSceneInfoDOList = new ArrayList<>();

        if (qsTaskDO.getTypicalSceneNum() == 0){
            return typicalSceneInfoDOList;
        }
        List<Map<String, Long>> qsSceneCountList = typicalSceneInfoDAO.queryAutoRandQsSceneCount(qsTaskDO.getQsAppId(), qsTaskDO.getStartTime(), qsTaskDO.getEndTime());
        int count = typicalSceneInfoDAO.queryAutoQsSceneCount(qsTaskDO.getQsAppId(), qsTaskDO.getStartTime(), qsTaskDO.getEndTime());
//        System.out.println(qsSceneCountList.toString());

        for (int i = 0; i < qsSceneCountList.size()-1; i++){
            Map<String, Long> map = qsSceneCountList.get(i);
            int count1 = (int)(map.get("count1").floatValue() / Long.valueOf(count).floatValue() * qsTaskDO.getTypicalSceneNum());
            if (count1 > 0){
                List<VcloudClientQsTypicalSceneInfoDO> sceneInfoDOS= typicalSceneInfoDAO.queryAutoRandQsSceneInfo(qsTaskDO.getQsAppId(), qsTaskDO.getStartTime(), qsTaskDO.getEndTime(), count1, map.get("num").intValue());
                typicalSceneInfoDOList.addAll(sceneInfoDOS);
            }
        }
        int diff = qsTaskDO.getTypicalSceneNum()-typicalSceneInfoDOList.size();
        Map<String, Long> map = qsSceneCountList.get(qsSceneCountList.size()-1);
        if (diff > 0){
            List<VcloudClientQsTypicalSceneInfoDO> sceneInfoDOS= typicalSceneInfoDAO.queryAutoRandQsSceneInfo(qsTaskDO.getQsAppId(), qsTaskDO.getStartTime(), qsTaskDO.getEndTime(), diff, map.get("num").intValue());
            typicalSceneInfoDOList.addAll(sceneInfoDOS);
        }
//        System.out.println(typicalSceneInfoDOList.size());
        return typicalSceneInfoDOList;
    }

}
