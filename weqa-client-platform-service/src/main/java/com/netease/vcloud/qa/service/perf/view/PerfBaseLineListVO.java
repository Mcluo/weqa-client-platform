package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/22 16:43
 */
public class PerfBaseLineListVO {

    private List<PerfBaseLineVO> baseLineList;

    private int page ;

    private int total ;

    private int size ;

    public List<PerfBaseLineVO> getBaseLineList() {
        return baseLineList;
    }

    public void setBaseLineList(List<PerfBaseLineVO> baseLineList) {
        this.baseLineList = baseLineList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
}
