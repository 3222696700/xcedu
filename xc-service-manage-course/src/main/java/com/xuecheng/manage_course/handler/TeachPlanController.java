package com.xuecheng.manage_course.handler;

import com.xuecheng.api.course.TeachPlanControllerApi;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachplanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_course.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/teachplan")
public class TeachPlanController  implements TeachPlanControllerApi {

    @Resource
    TeachplanService teachplanService;


    //===================================================================TeachPlan Manage=========================================================
    @Override
    @GetMapping("/teachplan/list/{courseid}")
    public TeachplanNode queryTeachPlanByCourseId(@PathVariable("courseid") String courseId)  {
        List<TeachplanNode> list= teachplanService.getTeachPlanWithTree(courseId);
        return list.get(0);
    }


    @PostMapping("/teachplan/add")
    @Override
    public ResponseResult saveTeachplan(@RequestBody TeachplanNode teachplanNode) {
        return teachplanService.saveTeachplan(teachplanNode);
    }

}
