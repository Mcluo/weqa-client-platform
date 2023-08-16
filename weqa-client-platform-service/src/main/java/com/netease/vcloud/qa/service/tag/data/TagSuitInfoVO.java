package com.netease.vcloud.qa.service.tag.data;

import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.auto.view.AutoScriptInfoVO;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/16 14:44
 */
public class TagSuitInfoVO {
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
     * 脚本信息
     */
    private List<AutoScriptInfoVO> scripts ;

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

    public List<AutoScriptInfoVO> getScripts() {
        return scripts;
    }

    public void setScripts(List<AutoScriptInfoVO> scripts) {
        this.scripts = scripts;
    }
}
