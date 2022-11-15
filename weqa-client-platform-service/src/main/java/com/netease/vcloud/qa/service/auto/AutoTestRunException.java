package com.netease.vcloud.qa.service.auto;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 21:01
 */
public class AutoTestRunException extends Exception{

    public static String AUTO_TEST_DEFAULT_EXCEPTION = "自动化测试异常" ;

    public static String AUTO_TEST_PARAM_EXCEPTION = "自动化触发参数异常" ;

    public static String AUTO_TEST_SCRIPT_IS_NULL = "自动化脚本为空" ;

    private String exceptionInfo ;

    public AutoTestRunException() {
        exceptionInfo =  AUTO_TEST_DEFAULT_EXCEPTION;
    }

    public AutoTestRunException(String message) {
        super(message);
        exceptionInfo = message ;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

}
