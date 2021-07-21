package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.mapper.CourseBaseMapper;
import com.xuecheng.manage_course.mapper.CourseMarketMapper;
import com.xuecheng.manage_course.mapper.CoursePicMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
public class CourseViewService {
    @Resource
    CourseBaseMapper courseBaseMapper;

    @Resource
    CoursePicMapper coursePicMapper;

    @Resource
    CourseMarketMapper courseMarketMapper;

    @Resource
    TeachplanService teachplanService;

    public CourseView getCourseViewByCourseid(String courseid){
        if(StringUtils.isEmpty(courseid)){return null;}
        CourseBase courseBase=courseBaseMapper.findCourseBaseById(courseid);
        CoursePic coursePic=coursePicMapper.selectCoursePicByCourseId(courseid);
        CourseMarket courseMarket=courseMarketMapper.selectCourseMarketByCourseId(courseid);
        List<TeachplanNode> list=teachplanService.getTeachPlanWithTree(courseid);

        if(courseBase==null||coursePic==null||courseMarket==null|| CollectionUtils.isEmpty(list)){
            return null;
        }
        CourseView courseView= new CourseView();
        courseView.setCourseBase(courseBase);
        courseView.setCourseMarket(courseMarket);
        courseView.setCoursePic(coursePic);
        courseView.setTeachplanNode(list.get(0));

        return courseView;
    }
}
