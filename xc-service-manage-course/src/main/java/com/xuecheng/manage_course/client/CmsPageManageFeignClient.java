package com.xuecheng.manage_course.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPagePostResult;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:ghost
 * @Date:2021/7/22
 * @Description:com.xuecheng.manage_course.client
 * @version:1.0
 */
@FeignClient(value=XcServiceList.XC_SERVICE_MANAGE_CMS)
@RequestMapping("/cms")
public interface CmsPageManageFeignClient {

    @PostMapping(value = "/page/save")
    CmsPageResult savePage(@RequestBody CmsPage cmsPage);

    @PostMapping("/page/post/{id}")
    CmsPagePostResult postPage(@PathVariable("id") String pageId);

}
