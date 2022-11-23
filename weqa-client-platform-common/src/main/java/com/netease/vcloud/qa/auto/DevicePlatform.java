package com.netease.vcloud.qa.auto;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/23 11:53
 */
public enum DevicePlatform {
    ANDROID("Android",(byte)1),

    IOS("iOS",(byte)2),

    PC("PC",(byte)3),

    MAC("MAC",(byte)4),

    LINUX("linux",(byte)5)
    ;

    private String platform ;

    private byte code ;

    DevicePlatform(String platform, byte code) {
        this.platform = platform;
        this.code = code;
    }

    public String getPlatform() {
        return platform;
    }

    public byte getCode() {
        return code;
    }

    public static DevicePlatform getDevicePlatformByCode(Byte code){
        if (code == null){
            return null ;
        }
        for (DevicePlatform devicePlatform : values()){
            if (code == devicePlatform.code){
                return devicePlatform ;
            }
        }
        return null ;
    }

    public static DevicePlatform getDevicePlatformByName(String name){
        if (StringUtils.isBlank(name)){
            return null ;
        }
        for (DevicePlatform devicePlatform : values()){
            if (StringUtils.equalsIgnoreCase(devicePlatform.platform,name)){
                return devicePlatform ;
            }
        }
        return null ;
    }

}
