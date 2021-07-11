package com.xuecheng.manage_course.service;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Auther:ghost
 * @Date:2021/7/8
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
public abstract class CommonTreeService<V,E> {

    public static final Logger LOGGER= LoggerFactory.getLogger(CommonTreeService.class);

    /**
     * @param list：domain集合
     * @param vClass :dto的class类
     * @return List<V> ：返回封装的dto树
     * @Description: 通用的递归生成树的方法
     * 1、将domain集合转换成dto集合
     * 2、使用用户自定义方法将dto集合转换为树状结构
     * @Author: ghost
     * @Date:2021/6/12
     *
     *
     * */
    public List<V> gennerateTree(List<E> list,Class<V> vClass) throws InstantiationException, IllegalAccessException {

        List<V> vlist=convertDomainToDto(list,vClass);

        if(CollectionUtils.isEmpty(vlist)){
            LOGGER.info("转换的dto集合为空，vlist{}",vlist);
            return new CopyOnWriteArrayList<>();
        }
        return domainListToTree(vlist);
    }


    /**
     * @param elist :domain类型的集合
     *
     * @return List<V> :返回dto类型的集合
     * @Description: 通用的将domain集合转换成dto集合的方法
     * @Author: ghost
     * @Date:2021/6/12
     * @version：1.0
     *
     * */
    public  List<V> convertDomainToDto(List<E> elist,Class<V> vClass) throws IllegalAccessException, InstantiationException {
        if(CollectionUtils.isEmpty(elist)){
            LOGGER.info("传入参数elist为空，elist{}",elist);
            return new CopyOnWriteArrayList<>();
        }
        List<V> vList= new CopyOnWriteArrayList<>();

        for (E e:elist){
                V v=vClass.newInstance();
                    BeanUtils.copyProperties(e, v);
                    vList.add(v);
        }
        return vList;
    }


    /**
     * @param vlist :dto类型的集合
     *
     * @return List<V> :返回dto类型的集合构建的树对象
     * @Description: 通用的将转换成dto集合转换成dto树状结构的方法
     * @Author: ghost
     * @Date:2021/6/12
     * @version：1.0
     *
     * */
    public List<V> domainListToTree(List<V> vlist){
        if(CollectionUtils.isEmpty(vlist))
        {
            LOGGER.info("构建树的dto集合为空,vlist{}",vlist);
            return Lists.emptyList();
        }
        return domainListToTreeCustom(vlist);
    }
    /**
     * @param vlist :dto类型的集合
     *
     * @return List<V> :返回dto树对象的集合
     * @Description: 用户自定义将转换成dto集合转换成dto树状结构的方法
     * @Author: ghost
     * @Date:2021/6/12
     * @version：1.0
     *
     * */
    public abstract List<V> domainListToTreeCustom(List<V> vlist);
}
