package com.xuecheng.manage_cms.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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
public class RabbitMQComfig {
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //    消息腹部返回回调处理类
    @Bean
    public RabbitTemplate.ReturnCallback returnCallback(){
        return new PublicReturnCallBackImpl();
    }

    //    消息发布确认回调处理类
    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback(){
        return new MessagePublishCallBackImpl();
    }

}
