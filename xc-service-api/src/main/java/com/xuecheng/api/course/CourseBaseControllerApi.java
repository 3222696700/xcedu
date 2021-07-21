package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
@Api(value = "课程管理接口")
public interface CourseBaseControllerApi {

    @ApiOperation("分页查询课程")
    QueryResponseResult<CourseInfo> findAllCourse(Integer page, Integer size, CourseListRequest courseListRequest);

}
