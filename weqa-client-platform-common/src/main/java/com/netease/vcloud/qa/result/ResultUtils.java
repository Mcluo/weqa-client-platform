package com.netease.vcloud.qa.result;

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
        ResultVO resultVO = new ResultVO() ;
        if (result){
            resultVO.setCode(200);
            resultVO.setMsg(SUCCESS_RESULT_MESSAGE);
        }else {
            resultVO.setCode(500);
            resultVO.setMsg(FAIL_RESULT_COMMON_MESSAGE);
        }
        if (data!=null){
            resultVO.setData(data);
        }
        return resultVO ;
    }
}
