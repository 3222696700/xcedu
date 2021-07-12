package com.xuecheng.manage_course.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.GradeUtil;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachPlanMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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



    @Resource
    CourseBaseRepository courseBaseRepository;

    @Autowired
    CategoryService categoryService;


    /**
     * @param teachplanNode ：新增课程计划信息
     * @return ResponseResult
     * @Description: 新增课程计划
     * 1、参数校验
     * 2、
     * @Author: ghost
     * @Date:2021/6/12
     *
     *
     * */
    @Transactional
    public ResponseResult addTeachPlan(TeachplanNode teachplanNode){
//        参数校验
        if(teachplanNode==null|| StringUtils.isEmpty(teachplanNode.getCourseid())||StringUtils.isEmpty(teachplanNode.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String courseId=teachplanNode.getCourseid();
        String parentId=teachplanNode.getParentid();
        Teachplan parenTeachplan=null;
        // todo :数据库未规定数据唯一性标准，导致可以多次插入完全相同（除了主键外），因此根据course_id和parent_id查询可能会返回多个对象，正在解决...

//        如果父节点ID为空或为0，两种情况
//        1、如果parentId为空或者为0，就根据课程ID和parentId=0查询课程计划
//            （1）如果能够查询到课程计划，则返回该课程计划ID
//            （2）如果不能查询到课程计划，则新增课程计划返回新增主键ID
//        2、
        if(StringUtils.isEmpty(parentId)|GradeUtil.ROOT_PARENT_ID.equals(parentId)){
          parentId=getTeachPlanRootIdByCourseId(courseId);
          parenTeachplan=teachPlanMapper.selectTeachPlanById(parentId);
            if(parenTeachplan==null){
                ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
                return new ResponseResult(CommonCode.FAIL);
            }
        }
        //如果parent_id不为空或不为0，直接查询数据库是否存在该课程计划。
//        存在则计算层级，层级>3报错，否则新增该节点信息
//        不存在或则查出来有多个直接抛异常。
        else{
            //        根据parentId查出父节点的层级，然后通过封装的LevelUtil工具类计算子节点的层级。
            List<Teachplan> list= teachPlanMapper.findTeachPlanByCourseIdAndParentId(courseId,parentId);
            if(CollectionUtils.isEmpty(list)){
                return new ResponseResult(CommonCode.FAIL);
            }
            if(list.size()>1){
                return new ResponseResult(CommonCode.FAIL);
            }
            parenTeachplan=list.get(0);
        }

        String grade= GradeUtil.calculateNextGrade(parenTeachplan.getGrade());

//       对计算的层级进行判断，最多为3层，超出报错。
        if(Integer.parseInt(grade)>3){
            ExceptionCast.cast(CourseCode.COURSE_GRADE_OVER_MAX);
            return new ResponseResult(CommonCode.FAIL);
        }

        Teachplan teachplan=new Teachplan();
        BeanUtils.copyProperties(teachplanNode,teachplan);
        teachplan.setParentid(parenTeachplan.getId());
        teachplan.setGrade(grade);
        teachplan.setStatus("0");
        teachplan.setCourseid(parenTeachplan.getCourseid());
        teachPlanMapper.insertSelective(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * @param couseid：课程Id
     * @return ResponseResult
     * @Description: 新增课程计划
     * 1、如果课程Id能够查询到课程且根据课程Id和parentId=0(根节点)能够查询到该课程计划，即未指定上级目录而是直接将该新增课程计划直接挂在根节点下，直接返回该课程计划ID
     * 2、如果课程Id能够查询到课程且根据课程Id和parentId=0(根节点)不能够查询到该课程计划，即课程可能为新增还未添加课程计划根目录，就直接新增一个根节点，返回新增节点的主键
     * @Author: ghost
     * @Date:2021/6/12

     * */
    private String getTeachPlanRootIdByCourseId(String couseid){

        Optional<CourseBase> courseBaseOption=courseBaseRepository.findById(couseid);

        if(!courseBaseOption.isPresent()){
            return null;
        }

        CourseBase courseBase=courseBaseOption.get();

        List<Teachplan> list=teachPlanMapper.findTeachPlanByCourseIdAndParentId(couseid,GradeUtil.ROOT_PARENT_ID);

        if(list==null&&list.size()==0){
            Teachplan teachplan=new Teachplan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseBase.getId());
            teachplan.setPname(courseBase.getName());
            teachplan.setStatus("0");
            return  Integer.toString(teachPlanMapper.insertSelective(teachplan));
        }
        return list.get(0).getId();
    }

    /**
     * @param courseId
     * @return List<TeachplanNode>
     * @Description: 根据课程ID递归生成课程计划树
     * @Author: ghost
     * @Date:2021/6/12
     *
     *
     * */
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
            if((GradeUtil.ROOT_LEVE+GradeUtil.ROOT_PARENT_ID).equals(t.getGrade()+t.getParentid())){
                rootList.add(t);
            }
        }
        rootList.sort(this);

        transformDepartmentTree(rootList, GradeUtil.ROOT_LEVE,levelDtoMultimap);

        return rootList;
    }
    public void transformDepartmentTree(List<TeachplanNode> list,String level,Multimap<String,TeachplanNode> dtoMap)  {

        for(TeachplanNode dto:list)
        {
            String nextgrade= GradeUtil.calculateNextGrade(dto.getGrade());

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
