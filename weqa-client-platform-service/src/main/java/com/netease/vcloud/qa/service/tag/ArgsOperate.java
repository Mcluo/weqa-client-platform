package com.netease.vcloud.qa.service.tag;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/17 14:07
 */
public enum ArgsOperate {

    EQUALS("equals") ,

    GREATER_THAN("greater") ,

    LESS_THAN("less") ,

    ;

    private String operate ;

    ArgsOperate(String operate) {
        this.operate = operate;
    }

    public String getOperate() {
        return operate;
    }

    public static ArgsOperate getOperate(String operate) {
        if (StringUtils.isBlank(operate)){
            return EQUALS ;
        }
        for (ArgsOperate argsOperate : ArgsOperate.values()){
            if (argsOperate.getOperate().equalsIgnoreCase(operate)){
                return argsOperate ;
            }
        }
        return EQUALS ;
    }
}
