package com.netease.vcloud.qa.service.auto.view;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/22 16:22
 */
public class TaskDetailInfoVO {

    private TaskBaseInfoVO baseInfo ;

    private List<TaskUrlInfoVO> packageInfo ;

    private List<TaskRunScriptInfoVO> scriptList ;

    public TaskBaseInfoVO getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(TaskBaseInfoVO baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<TaskUrlInfoVO> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<TaskUrlInfoVO> packageInfo) {
        this.packageInfo = packageInfo;
    }

    public List<TaskRunScriptInfoVO> getScriptList() {
        return scriptList;
    }

    public void setScriptList(List<TaskRunScriptInfoVO> scriptList) {
        this.scriptList = scriptList;
    }
}
