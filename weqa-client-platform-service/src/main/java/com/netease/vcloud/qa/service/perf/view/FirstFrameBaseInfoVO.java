package com.netease.vcloud.qa.service.perf.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/10 17:46
 */
public class FirstFrameBaseInfoVO {
    private Long id ;

    /**
     *创建时间
     */
    private Long createTime ;

    /**
     * 性能任务名
     */
    private String taskName;
    /**
     * 对应设备信息
     */
    private String deviceInfo ;
    /**
     * 任务所有人
     */
    private UserInfoVO owner ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public UserInfoVO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoVO owner) {
        this.owner = owner;
    }
}
