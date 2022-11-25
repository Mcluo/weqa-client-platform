package com.netease.vcloud.qa.service.auto.data;

import java.util.Map;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/25 14:37
 */
public class TCCoveredInfoDTO {

    private Map<Long,Boolean> tcResult ;

    public Map<Long, Boolean> getTcResult() {
        return tcResult;
    }

    public void setTcResult(Map<Long, Boolean> tcResult) {
        this.tcResult = tcResult;
    }
}
