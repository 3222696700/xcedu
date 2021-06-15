package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.mapper.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @Auther:ghost
 * @Date:2021/6/14
 * @Description:com.xuecheng.manage_cms.service
 * @version:1.0
 */
/*
 * cmsPage页码从第一页开始计数，pageable的页码从0开始
 *
 *
 *
 *
 * */
@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * @param size             每页记录数
     * @param page             分页页码：日常习惯从1开始，pageable从0开始。
     * @param queryPageRequest :页面查询条件
     * @return queryResponseResult : 统一数据返回对象。
     * @Description: 分页查询MongoDB数据库的页面数据
     * @Author: ghost
     * @Date:2021/6/14
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        if (page <= 0) {
            page = 1;
        }

        page = page - 1;

        if (size <= 0) {
            size = 10;
        }

        Page<CmsPage> pages = cmsPageRepository.findAll(PageRequest.of(page, size));

        QueryResult<CmsPage> queryResult = new QueryResult();

        queryResult.setList(pages.getContent());

        queryResult.setTotal(pages.getTotalElements());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);

        return queryResponseResult;
    }
}
