package com.xuecheng.api.course;

import com.xuecheng.framework.domain.cms.response.CmsPagePostResult;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.response.CommonPublishResponseResult;
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

    @ApiOperation("课程预览")
    CommonPublishResponseResult coursePreview(String id);


    @ApiOperation("课程发布")
    CmsPagePostResult coursePost(String id);


}
