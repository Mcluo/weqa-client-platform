package com.netease.vcloud.qa.auto;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/10 11:36
 */
public enum TaskRunStatus {

    INIT("init",(byte)0) ,

    READY("ready",(byte)1) ,

    RUNNING("running",(byte) 2) ,

    FINISH("finish" ,(byte) 4) ,

    ERROR("error" ,(byte) -1) ,

    CANCEL("cancel", (byte) 9) ,
    ;
    private String status ;

    private byte code ;

    TaskRunStatus(String status, byte code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public static TaskRunStatus getTaskRunStatusByCode(Byte code){
        if (code == null){
            return null ;
        }
        for (TaskRunStatus taskRunStatus : values()){
            if (taskRunStatus.code == code){
                return taskRunStatus ;
            }
        }
        return null ;
    }

    public static boolean isTaskFinish(byte code){
        if (code == FINISH.code || code == ERROR.code || code == CANCEL.code){
            return true ;
        }else {
            return false ;
        }
    }
}
