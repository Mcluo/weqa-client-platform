package com.netease.vcloud.qa.service.risk;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/2/8 17:18
 */
public class RiskCheckException extends Exception{

    public static String RISK_CHECK_DEFAULT_EXCEPTION = "风险校验异常" ;

    public static String RISK_CHECK_PARAM_EXCEPTION = "参数异常" ;

    public static String RISK_PROJECT_IS_NOT_EXIST_EXCEPTION = "项目不存在" ;

    public static String RISK_TASK_IS_NOT_EXIST_EXCEPTION = "任务不存在" ;

    public static  String RISK_TASK_MANAGER_EXCEPTION = "任务操作异常" ;

    private String exceptionInfo ;

    public RiskCheckException() {
        exceptionInfo =  RISK_CHECK_DEFAULT_EXCEPTION;
    }

    public RiskCheckException(String message) {
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
