package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.CourseViewControllerApi;
import com.xuecheng.framework.domain.cms.response.CmsPagePostResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.response.CommonPublishResponseResult;
import com.xuecheng.manage_course.service.CourseViewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/course/view")
public class CourseViewController implements CourseViewControllerApi {

    @Resource
    CourseViewService courseViewService;

    @GetMapping("/{courseid}")
    @Override
    public CourseView getCourseViewByCourseid(@PathVariable("courseid") String courseid) {
        return courseViewService.getCourseViewByCourseid(courseid);
    }

    @PostMapping("/preview/{id}")
    @Override
    public CommonPublishResponseResult coursePreview(@PathVariable("id") String id) {
        return courseViewService.courseViewPreView(id);
    }

    @PostMapping("/post/{id}")
    @Override
    public CmsPagePostResult coursePost(@PathVariable("id") String id) {
        return courseViewService.coursePost(id);
    }




}
