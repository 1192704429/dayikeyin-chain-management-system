package com.dake.catering;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wuming
 * @description: 大可以餐饮连锁信息管理系统的设计与实现服务端启动入口
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.dake.catering.**.mapper")
@EnableScheduling
public class ChainCateringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChainCateringApplication.class, args);
        log.info("JAVA后端服务启动成功！！！！！！");
    }

}
