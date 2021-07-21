package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.api.course
 * @version:1.0
 */
@Api(value = "课程计划管理接口")
public interface TeachPlanControllerApi {
    @ApiOperation("根据提供的CourseId查询课程计划")
    TeachplanNode queryTeachPlanByCourseId(String courseId);

    @ApiOperation("保存课程计划")
    ResponseResult saveTeachplan(TeachplanNode teachplanNode);


}
