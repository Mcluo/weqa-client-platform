package com.netease.vcloud.qa.service.perf.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 15:41
 */
public class PerfBaseReportListVO {

    private List<PerfBaseReportVO> reportList;

    private int page ;

    private int total ;

    private int size ;

    public List<PerfBaseReportVO> getReportList() {
        return reportList;
    }

    public void setReportList(List<PerfBaseReportVO> reportList) {
        this.reportList = reportList;
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
