package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "课程管理接口", description = "提供课程相关功能")
public interface CourseControllerApi {

    @ApiOperation("根据提供的CourseId查询课程计划")
    public TeachplanNode queryTeachPlanByCourseId(String courseId);

    @ApiOperation("添加课程计划")
    public ResponseResult addTeachPlan(TeachplanNode teachplanNode);

    @ApiOperation("根据提供的CourseId查询课程计划")
    public QueryResponseResult<CourseInfo> findAllCourse(Integer page, Integer size, CourseListRequest courseListRequest);

    @ApiOperation("根据提供的CourseId查询课程计划")
    public CategoryNode findAllCategory();
}
