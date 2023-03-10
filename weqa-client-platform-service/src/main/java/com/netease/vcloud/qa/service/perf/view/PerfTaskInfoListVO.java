package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/7 14:24
 */
public class PerfTaskInfoListVO {

    private List<PerfTaskInfoVO> list ;

    private int total ;

    private int page ;

    private int size ;

    public List<PerfTaskInfoVO> getList() {
        return list;
    }

    public void setList(List<PerfTaskInfoVO> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
