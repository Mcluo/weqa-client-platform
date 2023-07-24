package com.netease.vcloud.qa.service.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.auto.ScriptRunStatus;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientAutoScriptRunInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoScriptRunInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.result.view.DeviceInfoVO;
import com.netease.vcloud.qa.service.auto.data.PipeLineNotifyDTO;
import com.netease.vcloud.qa.service.auto.data.PipeLineNotifyTaskDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/19 10:54
 */

@Service
public class AutoPipeLineNotifyService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("TCLog");

    private static final String PIPELINE_URL = "http://10.246.152.73:8081/channel/qa/test/state" ;

    private static final String WEQA_TASK_DETAIL_URL = "http://weqa.netease.com/#/client-g2/detail/" ;

    @Autowired
    private ClientAutoScriptRunInfoDAO clientAutoScriptRunInfoDAO ;

    public void notifyPipeline(ClientAutoTaskInfoDO clientAutoTaskInfoDO) {
        if (clientAutoTaskInfoDO == null|| StringUtils.isBlank(clientAutoTaskInfoDO.getBuildGroupId())){
            return;
        }
        PipeLineNotifyDTO pipeLineNotifyDTO = new PipeLineNotifyDTO() ;
        pipeLineNotifyDTO.setBuildGroupId(clientAutoTaskInfoDO.getBuildGroupId());
        PipeLineNotifyTaskDTO pipeLineNotifyTaskDTO = new PipeLineNotifyTaskDTO() ;
        Map<ScriptRunStatus,Integer> statusStatisticMap = new HashMap<ScriptRunStatus,Integer>() ;
        List<ClientAutoScriptRunInfoDO> clientAutoScriptRunInfoDOList = clientAutoScriptRunInfoDAO.getClientAutoScriptRunInfoByTaskId(clientAutoTaskInfoDO.getId());
        if (!CollectionUtils.isEmpty(clientAutoScriptRunInfoDOList)){
            for (ClientAutoScriptRunInfoDO clientAutoScriptRunInfoDO : clientAutoScriptRunInfoDOList){
                ScriptRunStatus scriptRunStatus = ScriptRunStatus.getStatusByCode(clientAutoScriptRunInfoDO.getExecStatus()) ;
                if (scriptRunStatus!=null) {
                    Integer scriptCount = statusStatisticMap.get(scriptRunStatus) ;
                    if (scriptCount == null){
                        scriptCount = 1 ;
                    }else {
                        scriptCount = scriptCount + 1 ;
                    }
                    statusStatisticMap.put(scriptRunStatus , scriptCount) ;
                }
            }
        }
        pipeLineNotifyTaskDTO.setCasesNum(clientAutoScriptRunInfoDOList.size());
        Integer successNumber = statusStatisticMap.get(ScriptRunStatus.SUCCESS) ;
        pipeLineNotifyTaskDTO.setSuccNum(successNumber == null ? 0 : successNumber);
        pipeLineNotifyTaskDTO.setFailNum(statusStatisticMap.get(ScriptRunStatus.FAIL) == null? 0 : statusStatisticMap.get(ScriptRunStatus.FAIL));
        pipeLineNotifyTaskDTO.setCancelNum(statusStatisticMap.get(ScriptRunStatus.CANCEL) == null? 0 : statusStatisticMap.get(ScriptRunStatus.CANCEL));
        pipeLineNotifyTaskDTO.setTaskurl(WEQA_TASK_DETAIL_URL+clientAutoTaskInfoDO.getId());
        String devicesInfo = clientAutoTaskInfoDO.getDeviceInfo() ;
        List<DeviceInfoVO> deviceInfoVOList = JSONArray.parseArray(devicesInfo,DeviceInfoVO.class) ;
        if (deviceInfoVOList!=null && deviceInfoVOList.size()>0){
            DeviceInfoVO deviceInfoVO = deviceInfoVOList.get(0) ;
            if (deviceInfoVO!=null){
                if (StringUtils.equalsIgnoreCase(deviceInfoVO.getPlatform(),"android")) {
                    pipeLineNotifyTaskDTO.setPlatform(3);
                }else if(StringUtils.equalsIgnoreCase(deviceInfoVO.getDeviceId(),"ios")){
                    pipeLineNotifyTaskDTO.setPlatform(2);
                }else if(StringUtils.equalsIgnoreCase(deviceInfoVO.getDeviceId(),"windows")){
                    pipeLineNotifyTaskDTO.setPlatform(0);
                }else if(StringUtils.equalsIgnoreCase(deviceInfoVO.getDeviceId(),"mac")){
                    pipeLineNotifyTaskDTO.setPlatform(1);
                }else {
                    pipeLineNotifyTaskDTO.setPlatform(5);
                }
            }
        }
        List<PipeLineNotifyTaskDTO> pipeLineNotifyDTOList = new ArrayList<PipeLineNotifyTaskDTO>() ;
        pipeLineNotifyDTOList.add(pipeLineNotifyTaskDTO) ;
        pipeLineNotifyDTO.setResultList(pipeLineNotifyDTOList);
        boolean flag = this.sendPipeLineNotify(pipeLineNotifyDTO) ;
        if (!flag){
            TC_LOGGER.error("[AutoPipeLineNotifyService.notifyPipeline]seng pipeLineNotify fail");
        }
    }

    private boolean sendPipeLineNotify(PipeLineNotifyDTO pipeLineNotifyDTO){
        String jsonStr = JSONObject.toJSONString(pipeLineNotifyDTO) ;
        System.out.println(jsonStr);
        JSONObject jsonObject = HttpUtils.getInstance().jsonPost(PIPELINE_URL, jsonStr) ;
        if (jsonObject!=null ){
            System.out.println(jsonObject.toJSONString());
            return true ;
        }else {
            return false ;
        }
    }

}
