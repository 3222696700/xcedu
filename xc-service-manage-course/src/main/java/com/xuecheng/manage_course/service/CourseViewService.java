package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPagePostResult;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CommonPublishResponseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_course.client.CmsPageManageFeignClient;
import com.xuecheng.manage_course.config.CourseViewPageConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:ghost
 * @Date:2021/7/21
 * @Description:com.xuecheng.manage_course.service
 * @version:1.0
 */
@Service
public class CourseViewService {
    @Resource
    CourseBaseService courseBaseService;

    @Resource
    CoursePicService coursePicService;

    @Resource
    CourseMarketService courseMarketService;

    @Resource
    TeachplanService teachplanService;

    @Resource
    CourseViewPageConfig courseViewPageConfig;

    @Resource
    CmsPageManageFeignClient cmsPageManageFeignClient;

    public CourseView getCourseViewByCourseid(String courseid){
        if(StringUtils.isEmpty(courseid)){return null;}
        CourseBase courseBase=courseBaseService.findCourseBaseByCourseId(courseid);
        CoursePic coursePic=coursePicService.queryCoursePicByCourseid(courseid);
        CourseMarket courseMarket=courseMarketService.findCourseMarketByCourseId(courseid);
        List<TeachplanNode> list=teachplanService.getTeachPlanWithTree(courseid);

        if(courseBase==null||coursePic==null||courseMarket==null|| CollectionUtils.isEmpty(list)){
            return null;
        }
        CourseView courseView= new CourseView();
        courseView.setCourseBase(courseBase);
        courseView.setCourseMarket(courseMarket);
        courseView.setCoursePic(coursePic);
        courseView.setTeachplanNode(list.get(0));

        return courseView;
    }

    public CommonPublishResponseResult courseViewPreView(String id){
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage=getCmsPageBean(id);
        CmsPageResult cmsPageResult= cmsPageManageFeignClient.savePage(cmsPage);

        if(!cmsPageResult.isSuccess()){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        String previewUrl=courseViewPageConfig.getPreviewUrl()+cmsPage.getPageId();
        // todo 这里需要返回一个自定义的错误页面URL，后续定义
        return StringUtils.isEmpty(previewUrl)?new CommonPublishResponseResult(CommonCode.FAIL,""):
                new CommonPublishResponseResult(CommonCode.SUCCESS,previewUrl);
    }

    public CmsPagePostResult coursePost(String id) {
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage=getCmsPageBean(id);
        CmsPageResult cmsPageResult= cmsPageManageFeignClient.savePage(cmsPage);
        CmsPagePostResult cmsPagePostResult=cmsPageManageFeignClient.postPage(cmsPageResult.getCmsPage().getPageId());
        if(!cmsPagePostResult.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        CourseBase courseBase=new CourseBase();
        courseBase.setId(id);
        courseBase.setSt("1");
        return courseBaseService.saveCourseBase(courseBase)?new CmsPagePostResult(CommonCode.FAIL,""):new CmsPagePostResult(CommonCode.SUCCESS,cmsPagePostResult.getUrl());
    }

    public CmsPage getCmsPageBean(String id){
        CmsPage cmsPage=new CmsPage();
        cmsPage.setTemplateId(courseViewPageConfig.getPublish_templateId());
        cmsPage.setDataUrl(courseViewPageConfig.getPublish_dataUrlPre()+id);
        cmsPage.setPageWebPath(courseViewPageConfig.getPublish_page_webpath());
        cmsPage.setPagePhysicalPath(courseViewPageConfig.getPublish_page_physicalpath());
        cmsPage.setSiteId(courseViewPageConfig.getPublish_siteId());
        return cmsPage;
    }
}
