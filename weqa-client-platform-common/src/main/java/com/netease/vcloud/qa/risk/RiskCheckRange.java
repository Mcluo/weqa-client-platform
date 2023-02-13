package com.netease.vcloud.qa.risk;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:38
 */
public enum RiskCheckRange {

    PROJECT((byte) 0) ,

    TASK((byte)1),

    ;


    private byte code ;

    RiskCheckRange(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static RiskCheckRange getRiskCheckRangeByCode(byte code){
        for (RiskCheckRange riskCheckRange : RiskCheckRange.values()){
            if(code == riskCheckRange.getCode() ){
                return riskCheckRange ;
            }
        }
        return null ;
    }
}
