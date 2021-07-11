package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/9
 * @Description:com.xuecheng.manage_course.dao
 * @version:1.0
 */
@Repository
public interface TeachPlanMapper {
    List<Teachplan> getTeachPlanListByCourseId(String courseId);
    List<Teachplan> findTeachPlanByCourseIdAndParentId(String courseId,String parentId);
    int insertSelective(Teachplan record);

    Teachplan selectTeachPlanById(String Id);
}