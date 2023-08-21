package com.netease.vcloud.qa.service.tag.data;

import com.netease.vcloud.qa.result.view.UserInfoVO;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/16 16:03
 */
public class TagDetailVO {

    private Long id ;

    private String name ;

    private String type ;

    private UserInfoVO owner ;

    private Long createTime ; ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserInfoVO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoVO owner) {
        this.owner = owner;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
