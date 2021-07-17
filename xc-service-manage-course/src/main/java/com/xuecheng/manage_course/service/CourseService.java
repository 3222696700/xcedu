package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseMapper;
import com.xuecheng.manage_course.dao.CourseMarketMapper;
import com.xuecheng.manage_course.dao.CoursePicMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/14
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CourseService {
    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CoursePicMapper coursePicMapper;


    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    TeachplanService teachplanService;

    public ResponseResult saveCoursePic(CoursePic coursePic){
        if(coursePic==null
                ||StringUtils.isEmpty(coursePic.getCourseid())
                ||StringUtils.isEmpty(coursePic.getPic())
                ||(courseBaseMapper.findCourseBaseById(coursePic.getCourseid()))==null){
            return new ResponseResult(CommonCode.FAIL);
        }

       CoursePic oldCoursePic=coursePicMapper.selectCoursePicByCourseId(coursePic.getCourseid());

       if(oldCoursePic!=null){
            coursePicMapper.updateCoursePic(coursePic);
            return  isSucces(coursePic)? new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);
       }

       coursePicMapper.saveCoursePic(coursePic);
       return isSucces(coursePic) ? new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);
    }

    public boolean isSucces(CoursePic coursePic){
        CoursePic updateCoursePic=coursePicMapper.selectCoursePicByCourseId(coursePic.getCourseid());
        if(coursePic==null
                ||StringUtils.isEmpty(coursePic.getCourseid())
                ||StringUtils.isEmpty(coursePic.getPic())){
            return false;
        }
        return coursePic.getPic().equals(updateCoursePic.getPic())&coursePic.getCourseid().equals(updateCoursePic.getCourseid());
    }


    public CoursePic queryCoursePicByCourseid(String courseid){
        if(StringUtils.isEmpty(courseid)||(courseBaseMapper.findCourseBaseById(courseid))==null){
            return null;
        }
        CoursePic coursePic=coursePicMapper.selectCoursePicByCourseId(courseid);

        if(coursePic==null
                ||StringUtils.isEmpty(coursePic.getCourseid())
                ||StringUtils.isEmpty(coursePic.getPic())){
                return null;
        }
        return coursePic;
    }

    public ResponseResult deleteCoursePicByCourseid(String courseid){
        if(StringUtils.isEmpty(courseid)||(courseBaseMapper.findCourseBaseById(courseid))==null){
            return new ResponseResult(CommonCode.FAIL);
        }
        coursePicMapper.deleteCoursePicByCourseid(courseid);
        return coursePicMapper.selectCoursePicByCourseId(courseid)==null?new ResponseResult(CommonCode.SUCCESS):new ResponseResult(CommonCode.FAIL);

    }


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
