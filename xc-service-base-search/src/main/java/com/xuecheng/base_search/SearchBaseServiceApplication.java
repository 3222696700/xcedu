package com.xuecheng.base_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther:ghost
 * @Date:2021/8/4
 * @Description:PACKAGE_NAME
 * @version:1.0
 */
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.search")//扫描实体类
@ComponentScan(basePackages={"com.xuecheng.api"})//扫描接口
@ComponentScan(basePackages={"com.xuecheng.base_search"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.xuecheng.framework"})//扫描common下的所有类
public class SearchBaseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchBaseServiceApplication.class, args);
    }
}
