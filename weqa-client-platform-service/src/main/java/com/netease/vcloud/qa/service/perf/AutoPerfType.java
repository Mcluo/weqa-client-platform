package com.netease.vcloud.qa.service.perf;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/5/19 14:46
 */
public enum AutoPerfType {

    NORMAL("normal",(byte)0) ,

    FIRST_FRAME("firstFrame",(byte)1) ,

    ;

    private String name ;

    private byte code ;

    AutoPerfType(String name, byte code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public byte getCode() {
        return code;
    }

    public static AutoPerfType getAutoPerfTypeByName(String name) {
        if (StringUtils.isBlank(name)){
            return null ;
        }
        for (AutoPerfType autoPerfType : AutoPerfType.values()){
            if (StringUtils.equalsIgnoreCase(autoPerfType.name,name)){
                return autoPerfType ;
            }
        }
        return null ;
    }


    public static AutoPerfType getAutoPerfTypeByCode(Byte code) {
        if (code == null){
            return null ;
        }
        for (AutoPerfType autoPerfType : AutoPerfType.values()){
            if (code.equals(autoPerfType.code)){
                return autoPerfType ;
            }
        }
        return null ;
    }
}
