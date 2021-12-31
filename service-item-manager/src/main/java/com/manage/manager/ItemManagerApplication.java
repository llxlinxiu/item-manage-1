package com.manage.manager;

import org.springframework.boot.SpringApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lyy
 * @date 2021/12/9
 */
@SpringBootApplication
//@MapperScan("com.forum.common.mapper")
//@ComponentScan({"com.manage.manager.config"})
////@ComponentScan({"com.forum.manager.util.**"})
//@EnableScheduling
//@EnableTransactionManagement
public class ItemManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemManagerApplication.class,args);
    }
}
