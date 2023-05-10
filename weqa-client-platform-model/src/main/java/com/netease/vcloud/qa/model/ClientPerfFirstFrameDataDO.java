package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/9 17:41
 */
public class ClientPerfFirstFrameDataDO {

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
     * 对应任务ID
     */
    private Long taskId ;

    /**
     * 数据类型
     */
    private byte type ;

    /**
     * 首帧具体数据
     */
    private Long firstFrameData ;

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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Long getFirstFrameData() {
        return firstFrameData;
    }

    public void setFirstFrameData(Long firstFrameData) {
        this.firstFrameData = firstFrameData;
    }
}
