package com.netease.vcloud.qa.model;

import java.io.Serializable;
import java.util.Date;

public class VcloudClientQsAppDO implements Serializable {
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
     * 客户名
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 客户app_key
     *
     * @mbg.generated
     */
    private String appKey;

    /**
     * 客户appid
     *
     * @mbg.generated
     */
    private String appid;

    /**
     * 客户场景人数
     *
     * @mbg.generated
     */
    private Integer sceneNum;

    /**
     * 测试服app_key
     *
     * @mbg.generated
     */
    private String testAppKey;

    /**
     * 测试服appid
     *
     * @mbg.generated
     */
    private String testAppid;

    /**
     * 开启关闭标识，1为开启，默认为0
     *
     * @mbg.generated
     */
    private Byte isRun;

    private String ExeccMethod;

    private String ExeccClass;

    public String getExeccMethod() {
        return ExeccMethod;
    }

    public void setExeccMethod(String execcMethod) {
        ExeccMethod = execcMethod;
    }

    public String getExeccClass() {
        return ExeccClass;
    }

    public void setExeccClass(String execcClass) {
        ExeccClass = execcClass;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Integer getSceneNum() {
        return sceneNum;
    }

    public void setSceneNum(Integer sceneNum) {
        this.sceneNum = sceneNum;
    }

    public String getTestAppKey() {
        return testAppKey;
    }

    public void setTestAppKey(String testAppKey) {
        this.testAppKey = testAppKey;
    }

    public String getTestAppid() {
        return testAppid;
    }

    public void setTestAppid(String testAppid) {
        this.testAppid = testAppid;
    }

    public Byte getIsRun() {
        return isRun;
    }

    public void setIsRun(Byte isRun) {
        this.isRun = isRun;
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
        sb.append(", name=").append(name);
        sb.append(", appKey=").append(appKey);
        sb.append(", appid=").append(appid);
        sb.append(", sceneNum=").append(sceneNum);
        sb.append(", testAppKey=").append(testAppKey);
        sb.append(", testAppid=").append(testAppid);
        sb.append(", isRun=").append(isRun);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}