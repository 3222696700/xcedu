package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @Auther:http://www.gzcb.com
 * @Date:2021/6/12
 * @Description:com.xuecheng.api.cms
 * @version:1.0
 */
public interface CmsPageControllerApi {

    //页面查询
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);




}
