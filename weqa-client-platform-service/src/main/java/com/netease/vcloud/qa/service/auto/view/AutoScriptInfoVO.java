package com.netease.vcloud.qa.service.auto.view;

import com.netease.vcloud.qa.result.view.UserInfoVO;
import com.netease.vcloud.qa.service.tag.data.TagVO;

import java.util.List;
import java.util.Objects;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 15:08
 */
public class AutoScriptInfoVO implements Comparable<AutoScriptInfoVO>{

    private Long id  ;

    private String name ;

    private String detail ;

    private String execClass ;

    private String execMethod ;

    private String execParam ;

    private Long tcId ;

    private UserInfoVO userInfo;

    private List<TagVO> tags;


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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExecClass() {
        return execClass;
    }

    public void setExecClass(String execClass) {
        this.execClass = execClass;
    }

    public String getExecMethod() {
        return execMethod;
    }

    public void setExecMethod(String execMethod) {
        this.execMethod = execMethod;
    }

    public String getExecParam() {
        return execParam;
    }

    public void setExecParam(String execParam) {
        this.execParam = execParam;
    }

    public Long getTcId() {
        return tcId;
    }

    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

    public List<TagVO> getTags() {
        return tags;
    }

    public void setTags(List<TagVO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutoScriptInfoVO that = (AutoScriptInfoVO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(AutoScriptInfoVO o) {
        return (int)(this.id - o.id);
    }
}
