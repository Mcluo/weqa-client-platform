package com.netease.vcloud.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/11 21:29
 */
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class WeqaClientApplication  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WeqaClientApplication.class);
        application.run(args);
    }
}
