package com.netease.vcloud.qa.risk;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:48
 */
public enum RiskProjectStatus implements RiskCheckStatus{

    /**
     * 进行中
     */
    PROGRESS("progress",(byte)5),

    /**
     * 已完成
     */
    FINISH("finish",(byte) 10),

    ;
    private String status ;

    private byte code ;

    RiskProjectStatus(String status, byte code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public byte getCode() {
        return code;
    }

    public static  RiskProjectStatus getStatusByName(String status){
        if (StringUtils.isBlank(status)){
            return null ;
        }
        for (RiskProjectStatus riskProjectStatus:RiskProjectStatus.values()){
            if (StringUtils.equalsIgnoreCase(riskProjectStatus.getStatus(),status)){
                return riskProjectStatus ;
            }
        }
        return null ;
    }

    public static  RiskProjectStatus getStatusByCode(Byte code){
        if (code == null){
            return null ;
        }
        for (RiskProjectStatus riskProjectStatus:RiskProjectStatus.values()){
            if (code.equals(riskProjectStatus.getCode())){
                return riskProjectStatus ;
            }
        }
        return null ;
    }
}
