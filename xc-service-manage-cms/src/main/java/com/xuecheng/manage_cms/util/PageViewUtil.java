package com.xuecheng.manage_cms.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther:ghost
 * @Date:2021/7/31
 * @Description:com.xuecheng.manage_cms.util
 * @version:1.0
 */
public class PageViewUtil {
    /**
     * @param dataUrl 页面静态化模型数据的dataURL
     * @return Map : 根据dataUrl获取的页面数据
     * @Description: 获取模型数据
     * @Author: ghost
     * @Date:2021/6/22
     */
    public static Map getModelByDataUrl(String dataUrl) {
        if (StringUtils.isEmpty(dataUrl)) {
            return new ConcurrentHashMap();
        }
        ResponseEntity<Map> responseEntity =new RestTemplate().getForEntity(dataUrl,Map.class);

        Map map=responseEntity.getBody();

        if(CollectionUtils.isEmpty(map)){
            return new ConcurrentHashMap();
        }
        return map;
    }
}
