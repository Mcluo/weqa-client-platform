package com.netease.vcloud.qa.risk;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 14:49
 */
public enum RiskTaskStatus implements RiskCheckStatus{
    /**
     * 开发中
     */
    IN_DEVELOP("develop",(byte) 3) ,
    /**
     * 测试中
     */
    IN_TEST("test", (byte) 5) ,
    /**
     * 已完成
     */
    FINISH("finish",(byte) 9) ,

    ;

    private String status ;

    private byte code ;

    RiskTaskStatus(String status, byte code) {
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

    public static RiskTaskStatus getRiskTaskStatusByCode(Byte code){
        if (code == null){
            return null ;
        }
        for (RiskTaskStatus riskTaskStatus : RiskTaskStatus.values()){
            if (code.equals(riskTaskStatus.code)){
                return riskTaskStatus ;
            }
        }
        return null ;
    }

    public static RiskTaskStatus getRiskTaskStatusByStatus(String status){
        if (StringUtils.isBlank(status)){
            return null ;
        }
        for (RiskTaskStatus riskTaskStatus : RiskTaskStatus.values()){
            if (StringUtils.equals(status,riskTaskStatus.status)){
                return riskTaskStatus ;
            }
        }
        return null ;
    }

}
