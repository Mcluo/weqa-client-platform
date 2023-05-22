package com.netease.vcloud.qa.service.auto;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/15 21:01
 */
public class AutoTestRunException extends Exception{

    public static String AUTO_TEST_DEFAULT_EXCEPTION = "自动化测试异常" ;

    public static String AUTO_TEST_PARAM_EXCEPTION = "自动化触发参数异常" ;

    public static String AUTO_TEST_DB_EXCEPTION = "操作数据库异常" ;

    public static String AUTO_TEST_SCRIPT_IS_NULL = "自动化脚本为空" ;

    public static String AUTO_TEST_SUIT_IS_NOT_EXIST = "用例集不存在" ;

    public static String AUTO_TEST_TASK_IS_NOT_EXIST = "自动化任务不存在" ;

    public static String AUTO_TEST_SCRIPT_ID_EMPTY = "自动化脚本信息为空" ;

    public static String AUTO_TEST_TASK_STATUS_EXCEPTION = "任务状态不支持该操作" ;

    public static String DEVICE_PARAM_EXCEPTION = "设备参数信息错误" ;

    public static String DEVICE_IS_OFFLINE = "全部设备下线" ;

    public static String PRIVATE_ADDRESS_IS_NOT_EXIST = "私有地址不存在" ;

    public static String BUILD_REPORT_RESULT_FAIL = "构建测试报告失败" ;

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


    @Override
    public String getMessage() {
        return getExceptionInfo() ;
    }
}
