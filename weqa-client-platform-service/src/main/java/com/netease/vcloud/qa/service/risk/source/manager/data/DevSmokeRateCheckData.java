package com.netease.vcloud.qa.service.risk.source.manager.data;

import com.netease.vcloud.qa.service.risk.source.struct.CheckInfoStructInterface;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/3/15 21:37
 */
public class DevSmokeRateCheckData  implements CheckInfoStructInterface {

    private Double passPercent ;

    public Double getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Double passPercent) {
        this.passPercent = passPercent;
    }
}
