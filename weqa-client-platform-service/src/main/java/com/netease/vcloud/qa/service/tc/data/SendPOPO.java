package com.netease.vcloud.qa.service.tc.data;

import lombok.Data;

@Data
public class SendPOPO {
    private  String to;
    private  String content;
    private  String ac_appName;
    private  String ac_timestamp;
    private  String ac_signature;
    private  String popoClient;
}
