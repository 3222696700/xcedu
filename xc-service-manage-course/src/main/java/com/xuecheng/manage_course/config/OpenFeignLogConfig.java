package com.xuecheng.manage_course.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:ghost
 * @Date:2021/7/22
 * @Description:com.xuecheng.manage_course.config
 * @version:1.0
 */
//解决日志冲突
@Configuration
public class OpenFeignLogConfig {
    @Bean
    Logger.Level FeignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
