package com.netease.vcloud.qa.risk;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/10 15:47
 */
public enum RiskPersonType {

    NORMAL_NUMBER("normal") ,

    ;
    private String type ;

    RiskPersonType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
