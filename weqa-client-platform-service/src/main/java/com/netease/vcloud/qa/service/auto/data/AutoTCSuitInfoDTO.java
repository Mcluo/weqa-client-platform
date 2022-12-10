package com.netease.vcloud.qa.service.auto.data;

import java.util.List;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/12/10 16:02
 */
public class AutoTCSuitInfoDTO {

    private String suitName ;

    private List<AutoTCScriptInfoDTO> scriptList;

    public String getSuitName() {
        return suitName;
    }

    public void setSuitName(String suitName) {
        this.suitName = suitName;
    }

    public List<AutoTCScriptInfoDTO> getScriptList() {
        return scriptList;
    }

    public void setScriptList(List<AutoTCScriptInfoDTO> scriptList) {
        this.scriptList = scriptList;
    }
}
