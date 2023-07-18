package com.netease.vcloud.qa.service.api;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/14 14:25
 */
public class JenkinsBuildDTO {

    private String android ;

    private String ios ;
//
//    private String windows ;
//
//    private String mac ;

    private String mac_x64 ;

    private String mac_arm64 ;

    private String pc_x86 ;

    private String pc_x64 ;

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getMac_x64() {
        return mac_x64;
    }

    public void setMac_x64(String mac_x64) {
        this.mac_x64 = mac_x64;
    }

    public String getMac_arm64() {
        return mac_arm64;
    }

    public void setMac_arm64(String mac_arm64) {
        this.mac_arm64 = mac_arm64;
    }

    public String getPc_x86() {
        return pc_x86;
    }

    public void setPc_x86(String pc_x86) {
        this.pc_x86 = pc_x86;
    }

    public String getPc_x64() {
        return pc_x64;
    }

    public void setPc_x64(String pc_x64) {
        this.pc_x64 = pc_x64;
    }
}
