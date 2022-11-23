package com.netease.vcloud.qa.service.auto.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 15:07
 */
public class AutoScriptListVO {

    private List<AutoScriptInfoVO> autoScriptInfoVOList ;

    private Integer size ;

    private Integer current ;

    private Integer total ;

    public List<AutoScriptInfoVO> getAutoScriptInfoVOList() {
        return autoScriptInfoVOList;
    }

    public void setAutoScriptInfoVOList(List<AutoScriptInfoVO> autoScriptInfoVOList) {
        this.autoScriptInfoVOList = autoScriptInfoVOList;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
