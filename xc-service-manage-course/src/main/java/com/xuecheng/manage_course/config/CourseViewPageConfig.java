package com.xuecheng.manage_course.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:ghost
 * @Date:2021/8/1
 * @Description:com.xuecheng.manage_cms.config
 * @version:1.0
 */
@Configuration
@Data
public class CourseViewPageConfig {
    @Value("${course-publish.siteId}")
    private String publish_siteId;

    @Value("${course-publish.templateId}")
    private String publish_templateId;

    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;

    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;

}
