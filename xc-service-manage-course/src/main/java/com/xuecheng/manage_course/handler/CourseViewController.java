package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.CourseViewControllerApi;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.response.CoursePublishResponseResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageManageFeignClient;
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

    @Resource
    CmsPageManageFeignClient cmsPageManageFeignClient;


    @GetMapping("/{courseid}")
    @Override
    public CourseView getCourseViewByCourseid(@PathVariable("courseid") String courseid) {
        return courseViewService.getCourseViewByCourseid(courseid);
    }

    //todo:新增课程详情
    @PostMapping("/save")
    @Override
    public ResponseResult saveCourseView(CourseView courseView){
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @PostMapping("/preview/{id}")
    @Override
    public CoursePublishResponseResult coursePreview(@PathVariable("id") String id) {
       return null;
    }

}
