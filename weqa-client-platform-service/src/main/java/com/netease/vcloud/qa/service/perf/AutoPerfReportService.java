package com.netease.vcloud.qa.service.perf;

import com.netease.vcloud.qa.dao.VcloudClientAutoAndroidPrefInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoIosPrefInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoIosPrefMemoryInfoDAO;
import com.netease.vcloud.qa.dao.VcloudClientAutoPerfTaskDAO;
import com.netease.vcloud.qa.model.VcloudClientAutoAndroidPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoIosPrefMemoryInfoDO;
import com.netease.vcloud.qa.model.VcloudClientAutoPerfTaskDO;
import com.netease.vcloud.qa.service.perf.data.PerfAndroidDataDTO;
import com.netease.vcloud.qa.service.perf.data.PerfIOSDataDTO;
import com.netease.vcloud.qa.service.perf.data.PerfIOSMemoryDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化统计覆盖服务
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/31 16:46
 */
@Service
public class AutoPerfReportService {

    private static final Logger TC_LOGGER = LoggerFactory.getLogger("perfLog");

    @Autowired
    private VcloudClientAutoIosPrefMemoryInfoDAO clientAutoIosPrefMemoryInfoDAO;

    @Autowired
    private VcloudClientAutoIosPrefInfoDAO clientAutoIosPrefInfoDAO;

    @Autowired
    private VcloudClientAutoAndroidPrefInfoDAO clientAutoAndroidPrefInfoDAO;

    @Autowired
    private VcloudClientAutoPerfTaskDAO clientAutoPerfTaskDAO;

    @Deprecated
    public void insertIosMemoryInfo(VcloudClientAutoIosPrefMemoryInfoDO clientAutoIosPrefMemoryInfoDO){
        clientAutoIosPrefMemoryInfoDAO.insert(clientAutoIosPrefMemoryInfoDO);
    }

    public boolean patchInsertIosMemoryInfo(Integer taskId ,List<PerfIOSMemoryDataDTO> perfIOSMemoryDataDTOList){
        //批量插入iOS内存信息
        if (CollectionUtils.isEmpty(perfIOSMemoryDataDTOList)){
            return false;
        }
        List<VcloudClientAutoIosPrefMemoryInfoDO> vcloudClientAutoIosPrefMemoryInfoDOList = this.buildPerfIOSMemoryDataByDTOList(taskId,perfIOSMemoryDataDTOList);
        int count = clientAutoIosPrefMemoryInfoDAO.patchInsert(vcloudClientAutoIosPrefMemoryInfoDOList) ;
        if (count >= vcloudClientAutoIosPrefMemoryInfoDOList.size()){
            return true ;
        }else{
            return false ;
        }
    }

    private List<VcloudClientAutoIosPrefMemoryInfoDO> buildPerfIOSMemoryDataByDTOList(Integer taskId ,List<PerfIOSMemoryDataDTO> perfIOSMemoryDataDTOList){
        if(CollectionUtils.isEmpty(perfIOSMemoryDataDTOList)){
            return null;
        }
        List<VcloudClientAutoIosPrefMemoryInfoDO> vcloudClientAutoIosPrefMemoryInfoDOList = new ArrayList<VcloudClientAutoIosPrefMemoryInfoDO>() ;
        for (PerfIOSMemoryDataDTO perfIOSMemoryDataDTO : perfIOSMemoryDataDTOList){
            VcloudClientAutoIosPrefMemoryInfoDO vcloudClientAutoIosPrefMemoryInfoDO = new VcloudClientAutoIosPrefMemoryInfoDO() ;
            vcloudClientAutoIosPrefMemoryInfoDO.setTaskid(taskId);
            vcloudClientAutoIosPrefMemoryInfoDO.setMemory(perfIOSMemoryDataDTO.getMemory());
            vcloudClientAutoIosPrefMemoryInfoDO.setTimes(perfIOSMemoryDataDTO.getTimes());
            vcloudClientAutoIosPrefMemoryInfoDO.setAppCpu(perfIOSMemoryDataDTO.getAppCpu());
            vcloudClientAutoIosPrefMemoryInfoDO.setSysCpu(perfIOSMemoryDataDTO.getSysCpu());
            vcloudClientAutoIosPrefMemoryInfoDO.setTilerGPU(perfIOSMemoryDataDTO.getTilerGPU());
            vcloudClientAutoIosPrefMemoryInfoDO.setDeviceGPU(perfIOSMemoryDataDTO.getDeviceGPU());
            vcloudClientAutoIosPrefMemoryInfoDO.setRenderGPU(perfIOSMemoryDataDTO.getRenderGPU());
            vcloudClientAutoIosPrefMemoryInfoDOList.add(vcloudClientAutoIosPrefMemoryInfoDO) ;
        }
        return vcloudClientAutoIosPrefMemoryInfoDOList ;
    }


    @Deprecated
    public void insertIosInfo(VcloudClientAutoIosPrefInfoDO clientAutoIosPrefInfoDO){
        clientAutoIosPrefInfoDAO.insert(clientAutoIosPrefInfoDO);
    }

    public boolean patchInsertIosInfo(Integer taskId , List<PerfIOSDataDTO> perfIOSDataDTOList){
        if (CollectionUtils.isEmpty(perfIOSDataDTOList)){
            return false ;
        }
        List<VcloudClientAutoIosPrefInfoDO> vcloudClientAutoIosPrefInfoDOList = this.buildPerfIOSDataByDTOList(taskId,perfIOSDataDTOList) ;
        int count = clientAutoIosPrefInfoDAO.patchInsert(vcloudClientAutoIosPrefInfoDOList) ;
        if (count >= vcloudClientAutoIosPrefInfoDOList.size()){
            return true ;
        }else{
            return false ;
        }
    }

    private List<VcloudClientAutoIosPrefInfoDO> buildPerfIOSDataByDTOList(Integer taskId,List<PerfIOSDataDTO> perfIOSDataDTOList){
        if(CollectionUtils.isEmpty(perfIOSDataDTOList)){
            return null;
        }
        List<VcloudClientAutoIosPrefInfoDO> vcloudClientAutoIosPrefInfoDOList = new ArrayList<VcloudClientAutoIosPrefInfoDO>() ;
        for (PerfIOSDataDTO perfIOSDataDTO : perfIOSDataDTOList) {
            VcloudClientAutoIosPrefInfoDO vcloudClientAutoIosPrefInfoDO = new VcloudClientAutoIosPrefInfoDO();
            vcloudClientAutoIosPrefInfoDO.setTaskid(taskId);
            vcloudClientAutoIosPrefInfoDO.setTimes(perfIOSDataDTO.getTimes());
            vcloudClientAutoIosPrefInfoDO.setInstantamperage(perfIOSDataDTO.getInstantamperage());
            vcloudClientAutoIosPrefInfoDO.setLevel(perfIOSDataDTO.getLevel());
            vcloudClientAutoIosPrefInfoDO.setPower(perfIOSDataDTO.getPower());
            vcloudClientAutoIosPrefInfoDO.setTemperature(perfIOSDataDTO.getTemperature());
            vcloudClientAutoIosPrefInfoDO.setVoltage(perfIOSDataDTO.getVoltage());
            vcloudClientAutoIosPrefInfoDOList.add(vcloudClientAutoIosPrefInfoDO);
        }
        return vcloudClientAutoIosPrefInfoDOList ;
    }


    /**
     * 效率太低，不建议使用
     * @param clientAutoAndroidPrefInfoDO
     */
    @Deprecated
    public void insertAndroidInfo(VcloudClientAutoAndroidPrefInfoDO clientAutoAndroidPrefInfoDO){
        clientAutoAndroidPrefInfoDAO.insert(clientAutoAndroidPrefInfoDO);
    }


    public boolean patchInsertAndroidInfo(Integer taskId ,List<PerfAndroidDataDTO> perfAndroidDataUploadDTOList) {
        //批量插入Android数据
        List<VcloudClientAutoAndroidPrefInfoDO> clientAutoAndroidPrefInfoDOList = this.buildPerfAndroidDataDTOList(taskId,perfAndroidDataUploadDTOList);
        if (CollectionUtils.isEmpty(clientAutoAndroidPrefInfoDOList)){
            return false ;
        }
        int count = clientAutoAndroidPrefInfoDAO.patchInsert(clientAutoAndroidPrefInfoDOList);
        if (count >= clientAutoAndroidPrefInfoDOList.size()){
            return true ;
        }else {
            return false ;
        }
    }

    private List<VcloudClientAutoAndroidPrefInfoDO> buildPerfAndroidDataDTOList(Integer taskId ,List<PerfAndroidDataDTO> perfAndroidDataUploadDTOList){
        if (CollectionUtils.isEmpty(perfAndroidDataUploadDTOList)){
            return  null ;
        }
        List<VcloudClientAutoAndroidPrefInfoDO> vcloudClientAutoAndroidPrefInfoDOList = new ArrayList<VcloudClientAutoAndroidPrefInfoDO>() ;
        for (PerfAndroidDataDTO perfAndroidDataDTO : perfAndroidDataUploadDTOList){
            if (perfAndroidDataDTO == null){
                continue;
            }
            VcloudClientAutoAndroidPrefInfoDO vcloudClientAutoAndroidPrefInfoDO = new VcloudClientAutoAndroidPrefInfoDO() ;
            vcloudClientAutoAndroidPrefInfoDO.setTaskid(taskId);
            vcloudClientAutoAndroidPrefInfoDO.setInstantamperage(perfAndroidDataDTO.getInstantamperage());
            vcloudClientAutoAndroidPrefInfoDO.setPower(perfAndroidDataDTO.getPower());
            vcloudClientAutoAndroidPrefInfoDO.setTemperature(perfAndroidDataDTO.getTemperature());
            vcloudClientAutoAndroidPrefInfoDO.setVoltage(perfAndroidDataDTO.getVoltage());
            vcloudClientAutoAndroidPrefInfoDO.setLevel(perfAndroidDataDTO.getLevel());
            vcloudClientAutoAndroidPrefInfoDO.setMemory(perfAndroidDataDTO.getMemory());
            vcloudClientAutoAndroidPrefInfoDO.setTimes(perfAndroidDataDTO.getTimes());
            vcloudClientAutoAndroidPrefInfoDO.setAppCpu(perfAndroidDataDTO.getApp_cpu());
            vcloudClientAutoAndroidPrefInfoDO.setCpu(perfAndroidDataDTO.getCpu());
            vcloudClientAutoAndroidPrefInfoDO.setGpu(perfAndroidDataDTO.getGpu()) ;
            vcloudClientAutoAndroidPrefInfoDOList.add(vcloudClientAutoAndroidPrefInfoDO) ;
        }
        return vcloudClientAutoAndroidPrefInfoDOList ;
    }


    public int insertPerfTask(VcloudClientAutoPerfTaskDO clientAutoPerfTaskDO){
        return clientAutoPerfTaskDAO.insert(clientAutoPerfTaskDO);
    }

    public Integer getTaskId(Long autoTaskId){
        VcloudClientAutoPerfTaskDO autoPerfTaskDO = clientAutoPerfTaskDAO.selectByPrimaryAuto(autoTaskId);
        return autoPerfTaskDO.getId();
    }
}
