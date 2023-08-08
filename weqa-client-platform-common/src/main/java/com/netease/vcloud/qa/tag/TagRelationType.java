package com.netease.vcloud.qa.tag;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/8/8 10:47
 */
public enum TagRelationType {

    /**
     * 用例
     */
    TEST_CASE("testCase",(byte)1),

    /**
     * 用例集
     */
    TEST_SUITE("testSuite",(byte)2),

    ;

    private String type ;

    private byte code ;

    TagRelationType(String type, byte code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public byte getCode() {
        return code;
    }
}
