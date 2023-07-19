package com.netease.vcloud.qa.service.auto.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/19 11:04
 */
public class PipeLineNotifyDTO {

    private String buildGroupId ;

    private List<PipeLineNotifyTaskDTO> resultList ;

    public String getBuildGroupId() {
        return buildGroupId;
    }

    public void setBuildGroupId(String buildGroupId) {
        this.buildGroupId = buildGroupId;
    }

    public List<PipeLineNotifyTaskDTO> getResultList() {
        return resultList;
    }

    public void setResultList(List<PipeLineNotifyTaskDTO> resultList) {
        this.resultList = resultList;
    }
}
