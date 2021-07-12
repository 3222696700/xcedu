package com.xuecheng.manage_course.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.xuecheng.framework.domain.course.Category;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.dao.CategoryMapper;
import com.xuecheng.manage_course.util.CategoryNodeLevelUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther:ghost
 * @Date:2021/7/12
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CategoryService extends CommonTreeService<CategoryNode,Category> implements Comparator<CategoryNode> {
    @Resource
    CategoryMapper categoryMapper;


    public List<CategoryNode> getCategoryWithTree(){
        List<CategoryNode> list=null;
        try{
            list=super.gennerateTree(categoryMapper.findAllCategory(),CategoryNode.class);
        }catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        return list;
    }



    @Override
    public List<CategoryNode> domainListToTreeCustom(List<CategoryNode> vlist) {
        List<CategoryNode> rootList = new CopyOnWriteArrayList<>();

        Multimap<String, CategoryNode> levelDtoMultimap = ArrayListMultimap.create();

//        String rootLevel=null;
//
//        for(CategoryNode c:vlist){
//            levelDtoMultimap.put(c.getId(),c);
//
//            if(GradeUtil.ROOT_PARENT_ID.equals(c.getParentid())){
//                rootList.add(c);
//                rootLevel= CategoryNodeLevelUtil.calculateNextCategoryGrade(c);
//                transformDepartmentTree(rootList,rootLevel,levelDtoMultimap);
//            }
//
//
//        }
//        rootList.sort(this);
//
//        return rootList;
        for (CategoryNode c : vlist) {
            levelDtoMultimap.put(c.getParentid(), c);
            if(CategoryNodeLevelUtil.ROOT_PARENT_ID.equals(c.getParentid())){
                rootList.add(c);
            }
        }
        transformDepartmentTree(rootList,levelDtoMultimap);

        return rootList;
    }
    public void transformDepartmentTree(List<CategoryNode> list, Multimap<String,CategoryNode> dtoMap)  {

        for(CategoryNode dto:list)
        {
            List<CategoryNode> childrenList=(List<CategoryNode>) dtoMap.get(dto.getId());

            if(!(CollectionUtils.isEmpty(childrenList))){
                childrenList.sort(this);
                dto.setChildren(childrenList);
                transformDepartmentTree(childrenList,dtoMap);
            }
        }
    }


    @Override
    public int compare(CategoryNode o1, CategoryNode o2) {
        return o1.getOrderby()-o2.getOrderby();
    }
}
