package com.netease.vcloud.qa.service.auto;

import com.netease.vcloud.qa.CommonUtils;
import com.netease.vcloud.qa.UserInfoBO;
import com.netease.vcloud.qa.UserInfoService;
import com.netease.vcloud.qa.dao.ClientScriptTcInfoDAO;
import com.netease.vcloud.qa.model.ClientScriptTcInfoDO;
import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.auto.data.AutoTCScriptInfoDTO;
import com.netease.vcloud.qa.service.auto.data.TaskScriptRunInfoBO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptListVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/16 17:01
 */
@Service
public class AutoTcScriptService {

    private static final Logger AUTO_LOGGER = LoggerFactory.getLogger("autoLog");

    @Autowired
    private ClientScriptTcInfoDAO clientScriptTcInfoDAO ;

    @Autowired
    private UserInfoService userInfoService ;
    /**
     * 根据ID，获取对应的脚本信息
     * @param scriptIdSet
     * @return
     */
    public List<TaskScriptRunInfoBO> getScriptByIdSet(List<Long> scriptIdSet){
        if (CollectionUtils.isEmpty(scriptIdSet)){
            AUTO_LOGGER.error("[AutoTcScriptService.getScriptByIdSet] scriptIdSet is empty");
            return null ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScriptSet(scriptIdSet) ;
        List<TaskScriptRunInfoBO> taskScriptRunInfoBOList = new ArrayList<TaskScriptRunInfoBO>() ;
        if (CollectionUtils.isEmpty(clientScriptTcInfoDOList)){
            AUTO_LOGGER.warn("[AutoTcScriptService.getScriptByIdSet] clientScriptTcInfoDOList is empty");
            return taskScriptRunInfoBOList ;
        }
        for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
            TaskScriptRunInfoBO taskScriptRunInfoBO = AutoTestUtils.buildTaskScriptBOByScriptTCDO(clientScriptTcInfoDO) ;
            if (taskScriptRunInfoBO != null){
                taskScriptRunInfoBOList.add(taskScriptRunInfoBO) ;
            }
        }
        return taskScriptRunInfoBOList ;
    }

    /**
     * 根据批量设置脚本信息
     * @param autoTCScriptInfoDTOList
     * @return
     */
    public List<Long> addScriptInfo(List<AutoTCScriptInfoDTO> autoTCScriptInfoDTOList) throws AutoTestRunException{
        if (CollectionUtils.isEmpty(autoTCScriptInfoDTOList)){
//            return false ;
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = new ArrayList<ClientScriptTcInfoDO>() ;
        for (AutoTCScriptInfoDTO autoTCScriptInfoDTO : autoTCScriptInfoDTOList){
            ClientScriptTcInfoDO clientScriptTcInfoDO = AutoTestUtils.buildClientScriptTcInfoDOByScriptTcDTO(autoTCScriptInfoDTO) ;
            if (clientScriptTcInfoDO != null) {
                clientScriptTcInfoDOList.add(clientScriptTcInfoDO);
            }
        }
        int count = clientScriptTcInfoDAO.patchInsertClientScript(clientScriptTcInfoDOList) ;
        if (count < clientScriptTcInfoDOList.size()){
            AUTO_LOGGER.error("[AutoTcScriptService.setScriptInfo] add data fail");
//            return false ;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION);
        }else {
            List<Long> idList = new ArrayList<Long>() ;
            for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
                if (clientScriptTcInfoDO != null){
                    idList.add(clientScriptTcInfoDO.getId()) ;
                }
            }
            return idList ;
        }
    }

    public boolean updateScriptInfo(Long id, AutoTCScriptInfoDTO autoTCScriptInfoDTO) throws AutoTestRunException{
        if (id == null || autoTCScriptInfoDTO == null){
            throw  new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        ClientScriptTcInfoDO clientScriptTcInfoDO = AutoTestUtils.buildClientScriptTcInfoDOByScriptTcDTO(autoTCScriptInfoDTO) ;
        if (clientScriptTcInfoDO == null){
            AUTO_LOGGER.error("[AutoTcScriptService.updateScriptInfo] build clientScriptTcInfoDO fail");
            return false ;
        }
        clientScriptTcInfoDO.setId(id);
        int count = clientScriptTcInfoDAO.updateClientScript(clientScriptTcInfoDO) ;
        if (count < 1){
            AUTO_LOGGER.error("[AutoTcScriptService.updateScriptInfo] update data fail");
//            return false ;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION);
        }else {
            return true ;
        }
    }

    /**
     * 删除脚本
     * @param id
     * @return
     * @throws AutoTestRunException
     */
    public boolean deleteScript(Long id ) throws AutoTestRunException{
        if (id == null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        int count = clientScriptTcInfoDAO.deleteClientScript(id) ;
        if (count < 1){
            AUTO_LOGGER.error("[AutoTcScriptService.deleteScript] delete data fail");
//            return false ;
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_DB_EXCEPTION);
        }else {
            return true ;
        }
    }

    /**
     * 获取全量脚本（带分页）
     * @param pageNo
     * @param pageSize
     * @return
     * @throws AutoTestRunException
     */
    public AutoScriptListVO getAllScript(Integer pageNo , Integer pageSize) throws AutoTestRunException{
        AutoScriptListVO autoScriptListVO = new AutoScriptListVO() ;
        int start = pageNo == null ? 0 : (pageNo - 1) * pageSize ;
        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.getClientScript(start,pageSize) ;
        int total = clientScriptTcInfoDAO.getClientScriptCount() ;
        autoScriptListVO.setCurrent(pageNo);
        autoScriptListVO.setSize(pageSize);
        autoScriptListVO.setTotal(total);
//        if (!CollectionUtils.isEmpty(clientScriptTcInfoDOList)){
//            List<AutoScriptInfoVO> autoScriptInfoVOList = new ArrayList<AutoScriptInfoVO>() ;
//            Set<String> userInfoSet = new HashSet<String>() ;
//            for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
//                if (clientScriptTcInfoDO!=null && StringUtils.isNotBlank(clientScriptTcInfoDO.getScriptOwner())){
//                    userInfoSet.add(clientScriptTcInfoDO.getScriptOwner()) ;
//                }
//            }
//            Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(userInfoSet) ;
//            for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOList){
//                AutoScriptInfoVO autoScriptInfoVO = this.buildScriptListVOByDO(clientScriptTcInfoDO,userInfoBOMap);
//                if (autoScriptInfoVO!=null){
//                    autoScriptInfoVOList.add(autoScriptInfoVO) ;
//                }
//            }
//            autoScriptListVO.setAutoScriptInfoVOList(autoScriptInfoVOList);
//        }
        List<AutoScriptInfoVO> autoScriptInfoVOList = this.buildAutoScriptInfoVOByDOList(clientScriptTcInfoDOList) ;
        autoScriptListVO.setAutoScriptInfoVOList(autoScriptInfoVOList);
        return autoScriptListVO ;
    }

    public  List<AutoScriptInfoVO> buildAutoScriptInfoVOByDOList( Collection<ClientScriptTcInfoDO> clientScriptTcInfoDOCollection) {
        if (CollectionUtils.isEmpty(clientScriptTcInfoDOCollection)) {
            return null;
        }
        List<AutoScriptInfoVO> autoScriptInfoVOList = new ArrayList<AutoScriptInfoVO>();
        Set<String> userInfoSet = new HashSet<String>();
        for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOCollection) {
            if (clientScriptTcInfoDO != null && StringUtils.isNotBlank(clientScriptTcInfoDO.getScriptOwner())) {
                userInfoSet.add(clientScriptTcInfoDO.getScriptOwner());
            }
        }
        Map<String, UserInfoBO> userInfoBOMap = userInfoService.queryUserInfoBOMap(userInfoSet);
        for (ClientScriptTcInfoDO clientScriptTcInfoDO : clientScriptTcInfoDOCollection) {
            AutoScriptInfoVO autoScriptInfoVO = this.buildScriptListVOByDO(clientScriptTcInfoDO, userInfoBOMap);
            if (autoScriptInfoVO != null) {
                autoScriptInfoVOList.add(autoScriptInfoVO);
            }
        }
        return autoScriptInfoVOList;
    }


    private AutoScriptInfoVO buildScriptListVOByDO(ClientScriptTcInfoDO clientScriptTcInfoDO ,  Map<String, UserInfoBO> userInfoBOMap){
        if (clientScriptTcInfoDO == null){
            return null ;
        }
        AutoScriptInfoVO autoScriptInfoVO = new AutoScriptInfoVO() ;
        autoScriptInfoVO.setId(clientScriptTcInfoDO.getId());
        autoScriptInfoVO.setName(clientScriptTcInfoDO.getScriptName());
        autoScriptInfoVO.setDetail(clientScriptTcInfoDO.getScriptDetail());
        autoScriptInfoVO.setExecClass(clientScriptTcInfoDO.getExecClass());
        autoScriptInfoVO.setExecMethod(clientScriptTcInfoDO.getExecMethod());
        autoScriptInfoVO.setExecParam(clientScriptTcInfoDO.getExecParam());
        autoScriptInfoVO.setTcId(clientScriptTcInfoDO.getTcId());
        if (StringUtils.isNotBlank(clientScriptTcInfoDO.getScriptOwner()) && !CollectionUtils.isEmpty(userInfoBOMap)){
            UserInfoBO userInfoBO = userInfoBOMap.get(clientScriptTcInfoDO.getScriptOwner()) ;
            if (userInfoBO != null){
                UserInfoVO userInfoVO = CommonUtils.buildUserInfoVOByBO(userInfoBO) ;
                autoScriptInfoVO.setUserInfo(userInfoVO);
            }
        }
        return autoScriptInfoVO ;
    }


    /**
     * 根据关键字查询具体的脚本
     * @param key
     * @param pageNo
     * @param pageSize
     * @return
     * @throws AutoTestRunException
     */
    public AutoScriptListVO queryScriptList(String key , Integer pageNo , Integer pageSize)throws AutoTestRunException{
        if (StringUtils.isBlank(key)||pageNo == null || pageSize == null){
            throw new AutoTestRunException(AutoTestRunException.AUTO_TEST_PARAM_EXCEPTION) ;
        }
        int start = (pageNo - 1) * pageSize ;
        AutoScriptListVO autoScriptListVO = new AutoScriptListVO() ;
        autoScriptListVO.setCurrent(pageNo);
        autoScriptListVO.setSize(pageSize);

        List<ClientScriptTcInfoDO> clientScriptTcInfoDOList = clientScriptTcInfoDAO.queryClientScript(key,start,pageSize) ;
        int count = clientScriptTcInfoDAO.queryClientScriptCount(key) ;
        autoScriptListVO.setTotal(count);
        List<AutoScriptInfoVO> autoScriptInfoVOList = this.buildAutoScriptInfoVOByDOList(clientScriptTcInfoDOList) ;
        autoScriptListVO.setAutoScriptInfoVOList(autoScriptInfoVOList);
        return autoScriptListVO;
    }
}
