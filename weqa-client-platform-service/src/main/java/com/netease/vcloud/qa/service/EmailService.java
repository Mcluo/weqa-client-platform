package com.netease.vcloud.qa.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.vcloud.qa.PropertiesConfig;
import com.netease.vcloud.qa.common.HttpUtils;
import com.netease.vcloud.qa.dao.ClientAutoDeviceInfoDAO;
import com.netease.vcloud.qa.dao.ClientAutoTaskInfoDAO;
import com.netease.vcloud.qa.model.ClientAutoDeviceInfoDO;
import com.netease.vcloud.qa.model.ClientAutoTaskInfoDO;
import com.netease.vcloud.qa.common.MD5Util;
import com.netease.vcloud.qa.service.auto.AutoTestService;
import com.netease.vcloud.qa.service.tc.data.SendPOPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPoolExecutor executor;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClientAutoTaskInfoDAO clientAutoTaskInfoDAO;

    // 配置文件中我的qq邮箱
//    @Value("${spring.mail.from}")
    private String from = "yunxin_test0308@163.com";

    @Autowired
    private ClientAutoDeviceInfoDAO clientAutoDeviceInfoDAO ;

    @Autowired
    private AutoTestService autoTestService;

    @Autowired
    private PropertiesConfig propertiesConfig ;

    private static final Logger COMMON_LOGGER = LoggerFactory.getLogger("EmailService");


    @PostConstruct
    public void init(){
        executor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(100));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void sendEmail(){
        if ("local".equals(propertiesConfig.getEnv())){
            return;
        }
        List<ClientAutoTaskInfoDO> infoDOList =  clientAutoTaskInfoDAO.queryAutoTaskInfo1(0);
        for (ClientAutoTaskInfoDO infoDO: infoDOList){
            String emailText= "<a href=\"http://weqa.netease.com/#/client-g2/detail/" + infoDO.getId() +"\">自动化测试已完成，点击查看详情!</a>";
            String popoText = "RTC自动化测试执行完成\n  点击查看详情:\n  http://weqa.netease.com/#/client-g2/detail/" + infoDO.getId();
            clientAutoTaskInfoDAO.updateClientAutoTaskEmail(infoDO.getId(), 1);
            if (infoDO.getDeviceType() == 1){
                String DeviceInfo = infoDO.getDeviceInfo();
                JSONArray array = JSON.parseArray(DeviceInfo);
                ArrayList<Long> list = new ArrayList();
                for(int i=0; i<array.size(); i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    if (jsonObject != null){
                        list.add(jsonObject.getLong("id"));
                    }
                }
                if (list.size() > 0 ){
                    List<ClientAutoDeviceInfoDO> deviceInfoDOList = clientAutoDeviceInfoDAO.getClientAutoDeviceByIds(list);
                    for (ClientAutoDeviceInfoDO deviceInfoDO : deviceInfoDOList) {
                        deviceInfoDO.setRun((byte) 0);
                        clientAutoDeviceInfoDAO.updateDeviceInfo(deviceInfoDO);
                    }
                }
            }
            executor.execute(() -> {
                try {
                    this.sendHtmlMail(infoDO.getOperator(), "Rtc自动化测试", emailText);
                    this.sendPOPO1(infoDO.getOperator(), popoText);
                    this.sendPipeLine(infoDO.getId());
                }catch (Exception e){
                    COMMON_LOGGER.error("[sendEmail.execute] start task exception", e);
                }
            }) ;

        }
    }

    public void sendPOPO1(String to, String content) throws NoSuchAlgorithmException {
        SendPOPO sendPOPO =new SendPOPO();
        sendPOPO.setTo(to);
        sendPOPO.setContent(content);
        sendPOPO.setAc_appName("weqa");
        sendPOPO.setPopoClient("weqa");
        long timeStamp = new Date().getTime()/1000;
        sendPOPO.setAc_timestamp(String.valueOf((timeStamp)));
        sendPOPO.setAc_signature(MD5Util.generateSignature("2037a10f-6143-4e94-8cd2-7227e921682b",timeStamp));
        String url = "http://alarm.netease.com/api/sendPOPO";
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(sendPOPO);
        JSONObject jsonObject1 = HttpUtils.getInstance().jsonPost(url,jsonObject.toJSONString());
        if (jsonObject1.getInteger("code") != 200){
            COMMON_LOGGER.error("发送popo消息失败");
        }
    }


    /**
     * 简单文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

    public void sendHtmlMail(String to,String subject,String content){
        //获取MimeMessage
        //面向对象的多态，javaMailSender.createMimeMessage()，多用途的网际邮件扩充协议
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper=new MimeMessageHelper(message,true);
            //邮件发送人
            mimeMessageHelper.setFrom(from);
            //邮件接收人
            mimeMessageHelper.setTo(to);
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容,HTML格式
            mimeMessageHelper.setText(content, true);
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPipeLine(long taskId){
        autoTestService.onTaskFinish(taskId);
    }
}


