package com.netease.vcloud.qa.auto;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/11/23 11:53
 */
public enum DevicePlatform {
    ANDROID("android",(byte)1),

    IOS("ios",(byte)2),

    PC("windows",(byte)3),

    MAC("mac",(byte)4),

    LINUX("linux",(byte)5),

    ELECTRON("electron",(byte)6),

    FLUTTER("flutter",(byte) 7),

    UNIAPP("uniapp",(byte)8),
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
