package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.CoursePicControllerApi;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CoursePicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/course/pic")
public class CoursePicController implements CoursePicControllerApi {

    @Resource
    CoursePicService coursePicService;

    @PostMapping("/save")
    @Override
    public ResponseResult saveCoursePic(CoursePic coursePic) {
        return coursePicService.saveCoursePic(coursePic);
    }

    @GetMapping("/{courseid}")
    @Override
    public CoursePic queryCoursePicByCourseid(@PathVariable("courseid") String courseid) {
        return coursePicService.queryCoursePicByCourseid(courseid);
    }

    @DeleteMapping("/{courseid}")
    @Override
    public ResponseResult deleteCoursePicByCourseid(@PathVariable("courseid") String courseid) {
        return coursePicService.deleteCoursePicByCourseid(courseid);
    }
}
