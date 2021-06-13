package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:
 * @Date:2021/6/12
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */

@RestController("cmsPageController")
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {



    @GetMapping(value = "/list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

        QueryResult<CmsPage> queryResult=new QueryResult();

        queryResult.setTotal(new Integer(5));

        List<CmsPage> list= new ArrayList<>();

        queryResult.setList(list);

        QueryResponseResult queryResponseResult=new QueryResponseResult(CommonCode.SUCCESS,queryResult);

        return queryResponseResult;
    }
}
