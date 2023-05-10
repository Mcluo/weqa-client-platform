package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientPerfFirstFrameDataDAO;
import com.netease.vcloud.qa.dao.ClientPerfFirstFrameTaskDAO;
import com.netease.vcloud.qa.model.ClientPerfFirstFrameDataDO;
import com.netease.vcloud.qa.model.ClientPerfFirstFrameTaskDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.perf.data.FirstFrameDataDTO;
import com.netease.vcloud.qa.service.perf.data.FirstFrameTaskDTO;
import com.netease.vcloud.qa.service.perf.data.FirstFrameType;
import com.netease.vcloud.qa.service.perf.view.FirstFrameBaseInfoVO;
import com.netease.vcloud.qa.service.perf.view.FirstFrameListVO;
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
public class AutoPerfFirstFrameService {

    private static final Logger PERF_LOGGER = LoggerFactory.getLogger("perfLog");

    @Autowired
    private ClientPerfFirstFrameTaskDAO clientPerfFirstFrameTaskDAO ;

    @Autowired
    private ClientPerfFirstFrameDataDAO clientPerfFirstFrameDataDAO ;

    @Autowired
    private UserInfoService userInfoService ;

    /**
     * 新建一个任务
     * @param firstFrameTaskDTO
     * @return
     */
    public Long createNewFirstFrame(FirstFrameTaskDTO firstFrameTaskDTO){
        if (firstFrameTaskDTO==null) {
            PERF_LOGGER.error("[AutoPerfFirstFrameService.createNewFirstFrame]firstFrameTaskDTO is null");
            return null;
        }
        ClientPerfFirstFrameTaskDO clientPerfFirstFrameTaskDO = this.buildFirstFrameTaskDOByDTO(firstFrameTaskDTO) ;
        if (clientPerfFirstFrameTaskDO == null){
            PERF_LOGGER.error("[AutoPerfFirstFrameService.createNewFirstFrame]clientPerfFirstFrameTaskDO is null");
            return null ;
        }
        int count = clientPerfFirstFrameTaskDAO.insertFirstFrameTask(clientPerfFirstFrameTaskDO) ;
        if (count > 0){
            return clientPerfFirstFrameTaskDO.getId() ;
        }else {
            return null ;
        }
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


    public FirstFrameListVO queryFirstFrame(int current ,int size){
        FirstFrameListVO firstFrameListVO = new FirstFrameListVO() ;
        firstFrameListVO.setCurrent(current);
        firstFrameListVO.setSize(size);
        int start = (current - 1 ) * size ;
        List<ClientPerfFirstFrameTaskDO> clientPerfFirstFrameTaskDOList = clientPerfFirstFrameTaskDAO.queryClientPerfFirstFrameTask(start,size) ;
        int count = clientPerfFirstFrameTaskDAO.getClientPerfFirstFrameTaskCount() ;
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
            return null;
        }
        FirstFrameBaseInfoVO firstFrameBaseInfoVO = new FirstFrameBaseInfoVO() ;
        firstFrameBaseInfoVO.setId(clientPerfFirstFrameTaskDO.getId());
        if(clientPerfFirstFrameTaskDO.getGmtCreate()!=null) {
            firstFrameBaseInfoVO.setCreateTime(clientPerfFirstFrameTaskDO.getGmtCreate().getTime());
        }
        firstFrameBaseInfoVO.setTaskName(clientPerfFirstFrameTaskDO.getTaskName());
        firstFrameBaseInfoVO.setDeviceInfo(clientPerfFirstFrameTaskDO.getDeviceInfo());
        UserInfoBO userInfoBO = userInfoBOMap.get(clientPerfFirstFrameTaskDO.getOwner()) ;
        UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO) ;
        firstFrameBaseInfoVO.setOwner(userInfoVO);
        return firstFrameBaseInfoVO ;
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

}
