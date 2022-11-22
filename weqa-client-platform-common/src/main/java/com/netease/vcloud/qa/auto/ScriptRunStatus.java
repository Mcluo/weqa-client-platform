package com.netease.vcloud.qa.auto;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/10 11:52
 */
public enum ScriptRunStatus {

    INIT("init",(byte)0) ,

    RUNNING("running",(byte) 2) ,

    FAIL("fail",(byte)4) ,

    SUCCESS("success" ,(byte) 6) ,

    ;
    private String status ;

    private byte code ;

    ScriptRunStatus(String status, byte code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public byte getCode() {
        return code;
    }

    public static ScriptRunStatus getStatusByName(String status){
        if (StringUtils.isBlank(status)){
            return null;
        }
        for (ScriptRunStatus scriptRunStatus : ScriptRunStatus.values()){
            if (StringUtils.equalsIgnoreCase(scriptRunStatus.getStatus(),status)){
                return scriptRunStatus ;
            }
        }
        return null ;
    }

    public static ScriptRunStatus getStatusByCode(Byte code){
        if (code == null){
            return null ;
        }
        for (ScriptRunStatus scriptRunStatus : ScriptRunStatus.values()){
            if (code == scriptRunStatus.code){
                return scriptRunStatus ;
            }
        }
        return null ;
    }

}
