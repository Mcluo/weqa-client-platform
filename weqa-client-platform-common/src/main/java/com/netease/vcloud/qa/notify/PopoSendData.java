package com.netease.vcloud.qa.notify;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/6 15:43
 */
public class PopoSendData {
    private  String to;
    private  String content;
    private  String ac_appName;
    private  String ac_timestamp;
    private  String ac_signature;
    private  String popoClient;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAc_appName() {
        return ac_appName;
    }

    public void setAc_appName(String ac_appName) {
        this.ac_appName = ac_appName;
    }

    public String getAc_timestamp() {
        return ac_timestamp;
    }

    public void setAc_timestamp(String ac_timestamp) {
        this.ac_timestamp = ac_timestamp;
    }

    public String getAc_signature() {
        return ac_signature;
    }

    public void setAc_signature(String ac_signature) {
        this.ac_signature = ac_signature;
    }

    public String getPopoClient() {
        return popoClient;
    }

    public void setPopoClient(String popoClient) {
        this.popoClient = popoClient;
    }
}
