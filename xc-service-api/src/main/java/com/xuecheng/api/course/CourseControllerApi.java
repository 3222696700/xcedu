package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "课程管理接口", description = "提供课程相关功能")
public interface CourseControllerApi {

    @ApiOperation("根据提供的CourseId查询课程计划")
    TeachplanNode queryTeachPlanByCourseId(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachPlan(TeachplanNode teachplanNode);



    @ApiOperation("查询所有课程类别")
    List<CategoryNode> findAllCategory();



    @ApiOperation("保存课程图片")
    ResponseResult saveCoursePic(CoursePic coursePic);

    @ApiOperation("根据课程id查询课程图片")
    CoursePic queryCoursePicByCourseid(String courseid);

    @ApiOperation("根据课程id删除课程图片")
    ResponseResult deleteCoursePicByCourseid(String courseid);

    @ApiOperation("分页查询课程")
    QueryResponseResult<CourseInfo> findAllCourse(Integer page, Integer size, CourseListRequest courseListRequest);
}
