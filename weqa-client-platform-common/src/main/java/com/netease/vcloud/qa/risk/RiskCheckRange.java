package com.netease.vcloud.qa.risk;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 16:38
 */
public enum RiskCheckRange {

    PROJECT("project",(byte) 0) ,

    TASK("task",(byte)1),

    ;

    private String name ;
    private byte code ;

    RiskCheckRange(String name , byte code) {
        this.name = name;
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

    public static RiskCheckRange getRiskCheckRageByName(String name){
        if (StringUtils.isBlank(name)){
            return null ;
        }
        for (RiskCheckRange riskCheckRange : RiskCheckRange.values()){
            if (StringUtils.equalsIgnoreCase(name,riskCheckRange.name)){
                return riskCheckRange ;
            }
        }
        return null ;
    }
}
