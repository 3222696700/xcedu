package com.xuecheng.manage_cms_client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.xuecheng.manage_cms_client.util.GridFSUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * @Auther:ghost
 * @Date:2021/7/4
 * @Description:com.xuecheng.manage_cms.config
 * @version:1.0
 */
@Configuration
public class MongoDBConfig {
    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient, MongoProperties mongoProperties){
        MongoDatabase mongoDatabase=mongoClient.getDatabase(mongoProperties.getDatabase());
        return GridFSBuckets.create(mongoDatabase);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDbFactory dbFactory, MongoConverter converter){
        return new GridFsTemplate(dbFactory,converter);
    }

    @Bean
    public GridFSUtils gridFSUtils(@Qualifier("gridFSBucket") GridFSBucket gridFSBucket, @Qualifier("gridFsTemplate") GridFsTemplate gridFsTemplate){
        return  new GridFSUtils(gridFsTemplate,gridFSBucket);
    }
}
