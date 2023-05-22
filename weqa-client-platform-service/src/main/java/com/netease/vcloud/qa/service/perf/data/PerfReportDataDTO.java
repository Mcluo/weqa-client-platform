package com.netease.vcloud.qa.service.perf.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 15:10
 */
public class PerfReportDataDTO {

    private String name ;

    private String type ;

    private List<Long> taskList ;

    private Long baseLine ;

    private String owner ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Long> taskList) {
        this.taskList = taskList;
    }

    public Long getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(Long baseLine) {
        this.baseLine = baseLine;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
