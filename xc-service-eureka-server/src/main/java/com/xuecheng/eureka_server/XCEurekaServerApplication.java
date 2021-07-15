package com.xuecheng.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Auther:ghost
 * @Date:2021/7/15
 * @Description:com.xuecheng.eureka_server
 * @version:1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class XCEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(XCEurekaServerApplication.class,args);
    }
}
