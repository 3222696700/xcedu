package com.xuecheng.filesystem.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.config
 * @version:1.0
 */
@Configuration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@Data
public class FastFSConfig {

    @Value("${xuecheng.fastdfs.charset}")
    private String charSet;

    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private Integer connectTimeoutSeconds;

    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private Integer networkTimeoutSeconds;

    @Value("${xuecheng.fastdfs.tracker_servers}")
    private List<String> trackerServersList;




}
