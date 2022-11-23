package com.netease.vcloud.qa.service.auto.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 14:52
 */
public class TaskInfoListVO {

    private List<TaskBaseInfoVO> taskInfoList ;

    private int size ;

    private int current ;

    private int total ;

    public List<TaskBaseInfoVO> getTaskInfoList() {
        return taskInfoList;
    }

    public void setTaskInfoList(List<TaskBaseInfoVO> taskInfoList) {
        this.taskInfoList = taskInfoList;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
