package com.netease.vcloud.qa.dao;

import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/9 22:25
 */
@Mapper
public interface ClientAutoTaskInfoDAO {

    /**
     * 根据ID，获取自动化测试任务信息
     * @param taskId
     * @return
     */
    ClientAutoTaskInfoDO getClientAutoTaskInfoById(@Param("id") long taskId) ;

    /**
     * 更新任务状态
     * @param taskId
     * @param statusCode
     * @return
     */
    int updateClientAutoTaskStatus(@Param("id")long taskId , @Param("status") byte statusCode) ;

    /**
     *根据任务状态，查询任务列表
     * 结果以时间顺序排序
     * @return
     */
    List<ClientAutoTaskInfoDO> queryClientAutoTaskInfoByStatus(@Param("status") byte statusCode) ;



    /*****************以下生产者使用*****************/

    /**
     * 插入一个新的任务
     * @param clientAutoTaskInfoDO
     * @return
     */
    int insertNewClientAutoTask(@Param("taskInfo") ClientAutoTaskInfoDO clientAutoTaskInfoDO) ;

    /**
     * @param start
     * @param size
     * @return
     */
    List<ClientAutoTaskInfoDO> queryAutoTaskInfo(@Param("owner") String owner,@Param("start") int start , @Param("size") int size) ;

    /**
     *
     * @return
     */
    int queryAutoTaskInfoCount(@Param("owner") String owner) ;

}
