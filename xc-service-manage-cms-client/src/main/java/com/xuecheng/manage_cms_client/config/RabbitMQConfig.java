package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:ghost
 * @Date:2021/9/11
 * @Description:com.xuecheng.manage_cms.config
 * @version:1.0
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
