package com.xuecheng.manage_course.client;

import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther:ghost
 * @Date:2021/7/22
 * @Description:com.xuecheng.manage_course.client
 * @version:1.0
 */
@FeignClient(value=XcServiceList.XC_SERVICE_MANAGE_CMS)
@RequestMapping("/cms/page")
public interface CmsPageManageFeignClient {

    @GetMapping("/get/{pageId}")
    CmsPage findCmsPageByPageId(@PathVariable("pageId") String pageId);


    @RequestMapping(value={"/preview/{id}"},method = {RequestMethod.GET})
    void preview(@PathVariable("id") String id);
}
