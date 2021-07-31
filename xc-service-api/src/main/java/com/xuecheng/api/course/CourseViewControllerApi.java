package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.response.CommonPublishResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.api.course
 * @version:1.0
 */
@Api
public interface CourseViewControllerApi {

    @ApiOperation("根据课程id查询课程详细信息")
    CourseView getCourseViewByCourseid(String courseid);

    @ApiOperation("保存课程详细信息（已存在课程更新，否则新增）")
    ResponseResult saveCourseView(CourseView courseView);

    @ApiOperation("课程预览")
    CommonPublishResponseResult coursePreview(String id);


}
