package com.netease.vcloud.qa.service.tag.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/14 15:22
 */
public class TagSelectVO {

    private String label ;

    private String value ;

    private List<TagSelectVO> children ;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TagSelectVO> getChildren() {
        return children;
    }

    public void setChildren(List<TagSelectVO> children) {
        this.children = children;
    }
}
