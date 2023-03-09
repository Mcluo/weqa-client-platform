package com.netease.vcloud.qa.service.risk.process.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 21:54
 */
public class RiskProjectListVO {

    private List<RiskProjectVO> projectList ;

    private int size ;

    private int page ;

    private int total ;

    public List<RiskProjectVO> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<RiskProjectVO> projectList) {
        this.projectList = projectList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
}
