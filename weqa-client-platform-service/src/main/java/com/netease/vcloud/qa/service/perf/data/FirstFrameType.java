package com.netease.vcloud.qa.service.perf.data;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/10 10:03
 */
public enum FirstFrameType {
    LOG("log",(byte)0) ,

    SCREEN("screen",(byte)1),

    ;
    private  String type ;

    private byte code ;

    FirstFrameType(String type, byte code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public byte getCode() {
        return code;
    }

    public static FirstFrameType getFirstFrameByName(String type){
        if (StringUtils.isBlank(type)){
            return null ;
        }
        for (FirstFrameType firstFrameType : FirstFrameType.values()){
            if (StringUtils.equalsIgnoreCase(firstFrameType.type,type)){
                return firstFrameType ;
            }
        }
        return null ;
    }

    public static FirstFrameType getFirstFrameByCode(Byte code) {
        if (code == null) {
            return null;
        }
        for (FirstFrameType firstFrameType : FirstFrameType.values()) {
            if (code == firstFrameType.code){
                return firstFrameType ;
            }
        }
        return null ;
    }
}
