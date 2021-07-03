package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.mapper.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

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

    @Resource
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

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();

        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageId())) {
            cmsPage.setPageId(queryPageRequest.getPageId());
        }

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        if (page <= 0) {
            page = 1;
        }
        page = page - 1;

        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<CmsPage> pages = cmsPageRepository.findAll(example, pageable);

        QueryResult queryResult = new QueryResult();

        queryResult.setList(pages.getContent());

        queryResult.setTotal(pages.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    //    添加页面
    public CmsPageResult add(CmsPage cmsPage) {

//        参数校验
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //页面唯一性校验
        CmsPage currentPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),
                cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if (currentPage != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        else {
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }

        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public CmsPage findById(String Id) {

        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(Id);

        return optionalCmsPage.orElse(null);
    }

    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage oldCmsPage = findById(id);

        if (oldCmsPage != null) {

            //更新模板Id
            oldCmsPage.setTemplateId(cmsPage.getTemplateId());
            //更新站点Id
            oldCmsPage.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            oldCmsPage.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            oldCmsPage.setPageName(cmsPage.getPageName());
            //更新访问路径
            oldCmsPage.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            oldCmsPage.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            CmsPage newCmsPage = cmsPageRepository.save(oldCmsPage);

            return new CmsPageResult(CommonCode.SUCCESS, newCmsPage);

        }

        return new CmsPageResult(CommonCode.FAIL, null);

    }

    public ResponseResult delete(String id) {

        Optional<CmsPage> optional = cmsPageRepository.findById(id);

        if (optional.isPresent()) {

            cmsPageRepository.deleteById(id);

            return new ResponseResult(CommonCode.SUCCESS);

        }

        return new ResponseResult(CommonCode.FAIL);

    }
}
