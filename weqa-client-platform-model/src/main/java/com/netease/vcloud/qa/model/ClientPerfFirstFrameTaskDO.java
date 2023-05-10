package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * 首帧
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/9 16:43
 */
public class ClientPerfFirstFrameTaskDO {
    /**
     * 主键KEY，可以自动生成
     */
    private Long id ;

    /**
     *创建时间
     */
    private Date gmtCreate ;

    /**
     *最后修改时间
     */
    private Date gmtUpdate ;

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
    private String owner ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
