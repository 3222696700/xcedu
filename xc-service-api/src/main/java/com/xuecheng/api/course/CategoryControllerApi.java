package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.api.course
 * @version:1.0
 */
@Api(value = "课程类别接口")
public interface CategoryControllerApi {

    @ApiOperation("查询课程类别")
    List<CategoryNode> findAllCategory();
}
