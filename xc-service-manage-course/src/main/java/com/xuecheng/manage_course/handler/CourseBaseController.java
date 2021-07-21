package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.CourseBaseControllerApi;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_course.service.CourseBaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/course")
public class CourseBaseController implements CourseBaseControllerApi {

    @Resource
    CourseBaseService courseBaseService;

    //todo：多条件分页查询课程基本信息
    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findAllCourse(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestBody CourseListRequest courseListRequest) {

        return new QueryResponseResult(CommonCode.SUCCESS,null);
    }






}
