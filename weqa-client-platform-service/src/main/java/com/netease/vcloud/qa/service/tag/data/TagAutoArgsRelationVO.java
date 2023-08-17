package com.netease.vcloud.qa.service.tag.data;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/17 11:05
 */
public class TagAutoArgsRelationVO {

    private long id ;

    private long createTime ;

    private String buildArgs ;

    private String argsCondition ;

    private long tagId ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getBuildArgs() {
        return buildArgs;
    }

    public void setBuildArgs(String buildArgs) {
        this.buildArgs = buildArgs;
    }

    public String getArgsCondition() {
        return argsCondition;
    }

    public void setArgsCondition(String argsCondition) {
        this.argsCondition = argsCondition;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}
