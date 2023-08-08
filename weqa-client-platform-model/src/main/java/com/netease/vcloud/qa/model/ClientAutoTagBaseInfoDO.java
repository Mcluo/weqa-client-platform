package com.netease.vcloud.qa.model;

import java.util.Date;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:10
 */
public class ClientAutoTagBaseInfoDO {
    private long id ;

    private Date gmtCreate ;

    private Date gmtUpdate ;

    private String  tagName  ;

    private String tagType ;

    private String creator ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
