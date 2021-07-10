package com.xuecheng.manage_course.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.utils.LevelUtil;
import com.xuecheng.manage_course.dao.TeachPlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CourseService extends CommonTreeService<TeachplanNode, Teachplan> implements Comparator<TeachplanNode> {


    @Resource
    TeachPlanMapper teachPlanMapper;


    public List<TeachplanNode> getTeachPlanWithTree(String courseId) {
        List<TeachplanNode> list=null;
        try{
             list=super.gennerateTree(teachPlanMapper.getTeachPlanListByCourseId(courseId),TeachplanNode.class);
        }catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }

        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public List<TeachplanNode> domainListToTreeCustom(List<TeachplanNode> vlist) {

        List<TeachplanNode> rootList=new CopyOnWriteArrayList<>();

        Multimap<String,TeachplanNode> levelDtoMultimap= ArrayListMultimap.create();

        for(TeachplanNode t:vlist){
            levelDtoMultimap.put(t.getGrade()+t.getParentid(),t);
            if((LevelUtil.ROOT_LEVE+t.getParentid()).equals(t.getGrade()+t.getParentid())){
                rootList.add(t);
            }
        }
        rootList.sort(this);

        transformDepartmentTree(rootList,LevelUtil.ROOT_LEVE,levelDtoMultimap);

        return rootList;
    }

    public void transformDepartmentTree(List<TeachplanNode> list,String level,Multimap<String,TeachplanNode> dtoMap)  {

        for(TeachplanNode dto:list)
        {
            String nextgrade=LevelUtil.caculateLevel(level);

            String nextLevel=nextgrade+dto.getId();

            List<TeachplanNode> nextNodeList=(List<TeachplanNode>) dtoMap.get(nextLevel);

            if(!(CollectionUtils.isEmpty(nextNodeList))){
                nextNodeList.sort(this);
                dto.setChildren(nextNodeList);
                transformDepartmentTree(nextNodeList,nextgrade,dtoMap);
            }
        }
    }

    @Override
    public int compare(TeachplanNode o1, TeachplanNode o2) {
        return o1.getOrderby()-o2.getOrderby();
    }
}
