package com.netease.vcloud.qa.tag;

import org.apache.commons.lang3.StringUtils;

/**
 * 标签类型
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 14:24
 */
public enum TagType {

        DEFAULT("无分类","default"),

        TEST_TYPE("测试类型","test_type") ,
        TEST_SCENE("测试场景","test_SCENE") ,
        MODEL_TYPE("模块划分","model_type"),
        ROLE_PROCESS("角色流程","role_process") ,
        COOPERATION_METHOD("配合方式","cooperation_method") ,
        BASE_CASE("基础用例","base_case");
     ;
    private String name ;

    private String code ;

    TagType(String name,String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static TagType getTagTypeByCode(String code) {
        if(StringUtils.isBlank(code)){
            return DEFAULT;
        }
        for(TagType tagType : TagType.values()){
            if (StringUtils.equalsIgnoreCase(code,tagType.code)){
                return tagType ;
            }
        }
        return DEFAULT ;
    }
}
