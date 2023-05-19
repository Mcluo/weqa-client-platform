package com.netease.vcloud.qa.service.perf;

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
}
