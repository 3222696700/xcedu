package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.api.course
 * @version:1.0
 */
@Api("课程图片管理接口")
public interface CoursePicControllerApi {

    @ApiOperation("根据课程id查询课程图片")
    CoursePic queryCoursePicByCourseid(String courseid);

    @ApiOperation("根据课程id删除课程图片")
    ResponseResult deleteCoursePicByCourseid(String courseid);


    @ApiOperation("保存课程图片")
    public ResponseResult saveCoursePic(CoursePic coursePic);
}
