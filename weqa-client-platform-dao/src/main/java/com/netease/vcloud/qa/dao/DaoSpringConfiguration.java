package com.netease.vcloud.qa.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
/**
 * Created by luqiuwei@corp.netease.com
 * on 2022/10/12 14:25
 */
@Lazy(false)
@Configuration
@ImportResource(value = {"classpath:spring/spring-db.xml"})
@ComponentScan(basePackages = "com.netease.vcloud.qa.dao")
public class DaoSpringConfiguration {
}
