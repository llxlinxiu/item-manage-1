package com.manage.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author lyy
 * @date 2021/12/9
 */
@SpringBootApplication
@MapperScan("com.manage.service.mapper")
@ComponentScan({"com.manage.service.config"})
@EnableScheduling
@EnableTransactionManagement
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }
}
