package com.netease.vcloud.qa.model;

import java.io.Serializable;

public class VcloudClientAutoTaskUrlDO implements Serializable {
    /**
     * 唯一ID
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 平台
     *
     * @mbg.generated
     */
    private Byte platform;

    /**
     * 任务ID
     *
     * @mbg.generated
     */
    private Long taskId;

    /**
     * 包下载地址
     *
     * @mbg.generated
     */
    private String url;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getPlatform() {
        return platform;
    }

    public void setPlatform(Byte platform) {
        this.platform = platform;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", platform=").append(platform);
        sb.append(", taskId=").append(taskId);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}