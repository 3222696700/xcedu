package com.xuecheng.filesystem.config;

import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/13
 * @Description:com.xuecheng.filesystem.config
 * @version:1.0
 */
@Configuration
public class FastFSConfig {

    @Value("${xuecheng.fastdfs.charset}")
    private String charSet;

    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private Integer connectTimeoutSeconds;

    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private Integer networkTimeoutSeconds;

    @Value("${xuecheng.fastdfs.tracker_servers}")
    private List<String> trackerServersList;


    @Bean
    public TrackerClient trackerClient(){

        ClientGlobal.setG_charset(charSet);
        ClientGlobal.setG_connect_timeout(connectTimeoutSeconds);
        ClientGlobal.setG_network_timeout(networkTimeoutSeconds);
        try {
            ClientGlobal.initByTrackers(trackerServersList.get(0));
        }catch (IOException | MyException e){
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_SYSTEM_INIT_ERROR);
        }
        return new TrackerClient();
    }

    @Bean
    @ConditionalOnBean(TrackerClient.class)
    public StorageClient1 storageClient1(@Qualifier(value = "trackerClient") TrackerClient trackerClient) {
        TrackerServer trackerServer=null;
        StorageServer storageServer=null;
        try {
             trackerServer = trackerClient.getConnection();
             storageServer = trackerClient.getStoreStorage(trackerServer);
        }catch (IOException e){
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_SYSTEM_INIT_ERROR);
        }
            return new StorageClient1(trackerServer, storageServer);

    }
}
