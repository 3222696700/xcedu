package com.xuecheng.manage_cms.mapper;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther:ghost
 * @Date:2021/6/14
 * @Description:com.xuecheng.manage_cms.mapper
 * @version:1.0
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

        //       根据PageName、SiteId、PageWebPath确定页面
        public CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String PageWebPath);




}
