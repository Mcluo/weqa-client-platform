package com.netease.vcloud.qa.service.perf.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 15:18
 */
public class PerfBaseLineVO {

    private Long id ;

    private String name ;

    private Long createTime ;

    private UserInfoVO owner ;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public UserInfoVO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoVO owner) {
        this.owner = owner;
    }
}
