package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientQsApiInfoDO implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date gmtCreate;

    /**
     * 最后修改时间
     *
     * @mbg.generated
     */
    private Date gmtUpdate;

    /**
     * 执行时间
     *
     * @mbg.generated
     */
    private Long runTime;

    /**
     * 房间cid
     *
     * @mbg.generated
     */
    private String cid;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private String uid;

    /**
     * 平台
     *
     * @mbg.generated
     */
    private String platform;

    /**
     * api参数
     *
     * @mbg.generated
     */
    private String param;

    /**
     * 方法名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 返回状态
     *
     * @mbg.generated
     */
    private String code;

    private static final long serialVersionUID = 1L;

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

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", runTime=").append(runTime);
        sb.append(", cid=").append(cid);
        sb.append(", uid=").append(uid);
        sb.append(", platform=").append(platform);
        sb.append(", param=").append(param);
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}