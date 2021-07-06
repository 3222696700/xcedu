package com.xuecheng.manage_cms_client.service;

import com.xuecheng.manage_cms_client.mapper.CmsPageRepository;
import com.xuecheng.manage_cms_client.mapper.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:ghost
 * @Date:2021/7/6
 * @Description:com.xuecheng.manage_cms_client.service
 * @version:1.0
 */
@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;


}
