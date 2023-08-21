package com.netease.vcloud.qa.service.tag;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 16:07
 */
public class AutoTestTagException extends RuntimeException {

    public static final String AUTO_TAG_DEFAULT_EXCEPTION = "AutoTagRunException" ;

    public static final String AUTO_TAG_PARAM_EXCEPTION = "参数错误" ;

    public static final String TAG_NOT_EXIST_EXCEPTION = "标签不存在" ;



    public AutoTestTagException() {
        this.exceptionInfo = AUTO_TAG_DEFAULT_EXCEPTION;
    }

    public AutoTestTagException(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    private String exceptionInfo;

    public String getExceptionInfo() {
        return exceptionInfo;
    }


    @Override
    public String getMessage() {
        return getExceptionInfo() ;
    }
}
