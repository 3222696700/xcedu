package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.mapper.CmsSiteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/6/20
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
@Service
public class CmsSiteService {


    @Resource
    CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult findAll(){

        List<CmsSite> list= cmsSiteRepository.findAll();

        QueryResult queryResult=new QueryResult();


        queryResult.setList(list);

        queryResult.setTotal(new Long(list.size()));

        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);


    }
}
