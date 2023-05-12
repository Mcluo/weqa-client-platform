package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/10 17:46
 */
public class FirstFrameListVO {

    private List<FirstFrameBaseInfoVO> list ;

    private int total ;

    private int size ;

    private int current ;


    public List<FirstFrameBaseInfoVO> getList() {
        return list;
    }

    public void setList(List<FirstFrameBaseInfoVO> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
