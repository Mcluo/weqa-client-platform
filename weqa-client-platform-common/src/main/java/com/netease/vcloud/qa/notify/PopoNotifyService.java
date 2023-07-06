package com.netease.vcloud.qa.notify;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.common.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2023/7/6 15:27
 */
@Service
public class PopoNotifyService {
    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("CommonLog");

    private static final String POPO_APP_NAME = "weqa";

    private static final String POPO_APP_SECRET = "2037a10f-6143-4e94-8cd2-7227e921682b";

    private static final String POPO_SEND_URL = "http://alarm.netease.com/api/sendPOPO" ;


    public boolean sendPOPOMessage(String userEmail, String message)  {
        PopoSendData sendPOPO =new PopoSendData();
        sendPOPO.setTo(userEmail);
        sendPOPO.setContent(message);
        sendPOPO.setAc_appName(POPO_APP_NAME);
        sendPOPO.setPopoClient(POPO_APP_NAME);
        long timeStamp = System.currentTimeMillis()/1000;
        sendPOPO.setAc_timestamp(String.valueOf((timeStamp)));
        try {
            sendPOPO.setAc_signature(MD5Util.generateSignature(POPO_APP_SECRET, timeStamp));
        }catch (NoSuchAlgorithmException e){
            COMMON_LOGGER.error("[PopoNotifyService.sendPOPOMessage]generate signature exception",e);
            return  false;
        }
        JSONObject result = HttpUtils.getInstance().jsonPost(POPO_SEND_URL, JSONObject.toJSONString(sendPOPO));
        if (result.getInteger("code") != 200){
            COMMON_LOGGER.error("[PopoNotifyService.sendPOPOMessage] send popo message failed");
            return false;
        }else {
            return true ;
        }
    }

}
