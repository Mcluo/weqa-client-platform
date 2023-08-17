package com.netease.vcloud.qa.service.tag;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/17 11:59
 */
public enum ArgsType {
    /**
     * boolean类型
     */
    BOOLEAN("boolean"),
    /**
     * 数字类型
     */
    NUMBER("number"),
    /**
     * 字符串类型
     */
    STRING("string"),

    ;

    private String type ;

    ArgsType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ArgsType getType(String type) {
        if (StringUtils.isBlank(type)){
            return BOOLEAN ;
        }
        for (ArgsType argsType : ArgsType.values()){
            if (type.equalsIgnoreCase(argsType.getType())){
                return argsType ;
            }
        }
        return BOOLEAN ;
    }
}
