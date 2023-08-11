package com.netease.vcloud.qa.service.auto.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.tag.data.TagVO;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/10 10:38
 */
public class TestSuitBaseInfoVO {
    /**
     * 用例ID
     */
    private Long id ;
    /**
     * 测试集名
     */
    private String Name ;
    /**
     * 测试集归属人
     */
    private UserInfoVO owner ;
    /**
     * 标签信息
     */
    private List<TagVO> tags ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public UserInfoVO getOwner() {
        return owner;
    }

    public void setOwner(UserInfoVO owner) {
        this.owner = owner;
    }

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }
}
