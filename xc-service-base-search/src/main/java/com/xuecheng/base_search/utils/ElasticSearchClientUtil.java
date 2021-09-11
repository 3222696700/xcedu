package com.xuecheng.base_search.utils;

import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/8/5
 * @Description:com.xuecheng.base_search.utils
 * @version:1.0
 */
@Component
public class ElasticSearchClientUtil{

    @Resource
   static RestHighLevelClient restHighLevelClient;

    @Resource
    static RestClient restClient;

    //创建索引库
    public static boolean createIndexDataBase(String indexName, Map<String,Object> properties,String typeName,Map typeInfo) throws IOException {
        if(StringUtils.isEmpty(indexName)
                ||isIndexExist(indexName)
                || CollectionUtils.isEmpty(properties)
                ||StringUtils.isEmpty(typeName)
                ||CollectionUtils.isEmpty(typeInfo)){
            return false;
        }
        CreateIndexRequest createIndexRequest=new CreateIndexRequest(indexName);
//        createIndexRequest.source(properties);
        createIndexRequest.mapping(typeName,typeInfo);
        return restHighLevelClient.indices().create(createIndexRequest).isAcknowledged();
    }

    //根据索引名称删除索引库。
    public static boolean deleteIndexDataBase(String indexName) throws IOException {
        DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest(indexName);
        return restHighLevelClient.indices().delete(deleteIndexRequest).isAcknowledged();
    }


    //添加文档
    public static DocWriteResponse.Result addDocument(String indexName,String type,Map map) throws IOException {
        IndexRequest indexRequest=new IndexRequest(indexName,type);
        indexRequest.source(map);
        return restHighLevelClient.index(indexRequest).getResult();
    }

    public static boolean isIndexExist(String indexName) throws IOException {
        GetRequest getRequest=new GetRequest(indexName);
        return  restHighLevelClient.get(getRequest, new BufferedHeader(new CharArrayBuffer(1024))).isExists();

    }

}
