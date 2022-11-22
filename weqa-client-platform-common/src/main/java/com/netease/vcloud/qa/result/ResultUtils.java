package com.netease.vcloud.qa.result;

import org.springframework.util.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/31 14:20
 */
public class ResultUtils {

    private static final String SUCCESS_RESULT_MESSAGE = "success" ;

    private static final String FAIL_RESULT_COMMON_MESSAGE = "fail" ;


    public static ResultVO build(boolean result) {
        return build(result,null) ;
    }

    public static ResultVO build(boolean result , Object data) {
        if (result){
            return build(true , SUCCESS_RESULT_MESSAGE , data) ;
        }else {
           return build(false, FAIL_RESULT_COMMON_MESSAGE , data) ;
        }
    }

    public static ResultVO build(boolean result ,String message, Object data) {
        ResultVO resultVO = new ResultVO() ;
        if (result){
            resultVO.setCode(200);
            resultVO.setMsg(message);
        }else {
            resultVO.setCode(500);
            resultVO.setMsg(message);
        }
        if (data!=null){
            resultVO.setData(data);
        }
        return resultVO ;
    }


    public static ResultVO buildFail(){
        return buildFail(FAIL_RESULT_COMMON_MESSAGE) ;
    }
    public static ResultVO buildFail( String errorMessage) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(500);
        resultVO.setMsg(StringUtils.isEmpty(errorMessage)?FAIL_RESULT_COMMON_MESSAGE:errorMessage);
        return resultVO;
    }


    public static ResultVO buildSuccess() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg(SUCCESS_RESULT_MESSAGE);
        return resultVO;
    }
    public static ResultVO buildSuccess(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg(SUCCESS_RESULT_MESSAGE);
        resultVO.setData(data);
        return resultVO;
    }


}
