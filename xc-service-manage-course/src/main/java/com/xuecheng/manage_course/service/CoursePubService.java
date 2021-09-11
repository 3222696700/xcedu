package com.xuecheng.manage_course.service;

import com.alibaba.fastjson.JSONObject;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.manage_course.mapper.CoursePubMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Auther:ghost
 * @Date:2021/8/5
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CoursePubService {

    @Resource
    CourseViewService courseViewService;

    @Resource
    CoursePubMapper coursePubMapper;

    public boolean saveCoursePub(CoursePub coursePub){
        if(StringUtils.isEmpty(coursePub.getId())){
            return true;
        }
        return true;
    }

    public CoursePub getCoursePubBean(String id){
        CourseView courseView=courseViewService.getCourseViewByCourseid(id);
        CoursePub coursePub=new CoursePub();
        coursePub.setTeachplan(JSONObject.toJSONString(courseView.getTeachplanNode()));
        BeanUtils.copyProperties(courseView.getCourseBase(),coursePub);
        BeanUtils.copyProperties(courseView.getCoursePic(),coursePub);
        BeanUtils.copyProperties(courseView.getCourseMarket(),coursePub);
        return coursePub;
    }
}
