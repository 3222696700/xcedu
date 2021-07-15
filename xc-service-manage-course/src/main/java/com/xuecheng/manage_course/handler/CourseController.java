package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CategoryService;
import com.xuecheng.manage_course.service.CourseService;
import com.xuecheng.manage_course.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {


    @Autowired
    TeachplanService teachplanService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CourseService courseService;



//  ==============================================================CourseBase Manage===========================================================
    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findAllCourse(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestBody CourseListRequest courseListRequest) {


        return null;
    }
    @PostMapping("/coursepic/save")
    @Override
    public ResponseResult saveCoursePic(CoursePic coursePic) {
        return courseService.saveCoursePic(coursePic);
    }





//===================================================================TeachPlan Manage=========================================================
    @Override
    @GetMapping("/teachplan/list/{courseid}")
    public TeachplanNode queryTeachPlanByCourseId(@PathVariable("courseid") String courseId)  {
        List<TeachplanNode> list= teachplanService.getTeachPlanWithTree(courseId);
        return list.get(0);
    }


    @PostMapping("/teachplan/add")
    @Override
    public ResponseResult addTeachPlan(@RequestBody TeachplanNode teachplanNode) {
        return teachplanService.addTeachPlan(teachplanNode);
    }





//    ====================================================================Category Manage===================================================

    @GetMapping("/category/list")
    @Override
    public List<CategoryNode> findAllCategory() {
        return categoryService.getCategoryWithTree();
    }



}
