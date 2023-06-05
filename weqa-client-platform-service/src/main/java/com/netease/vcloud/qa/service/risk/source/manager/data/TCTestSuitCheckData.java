package com.netease.vcloud.qa.service.risk.source.manager.data;

import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/6/1 17:50
 */
public class TCTestSuitCheckData implements CheckInfoStructInterface {

    /**
     * 用例通过占比
     */
    private Double passPercent ;

    /**
     * 自动化占比
     */
    private Double autoPercent ;

    public Double getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Double passPercent) {
        this.passPercent = passPercent;
    }

    public Double getAutoPercent() {
        return autoPercent;
    }

    public void setAutoPercent(Double autoPercent) {
        this.autoPercent = autoPercent;
    }
}
