package com.xuecheng.manage_cms.handler;

import com.xuecheng.api.cms.SysDictionaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther:ghost
 * @Date:2021/7/11
 * @Description:com.xuecheng.manage_cms.handler
 * @version:1.0
 */
@RestController
@RequestMapping("/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {

    @Autowired
    SysDictionaryService sysDictionaryService;

    @Override
    public SysDictionary getSysDictionaryByDType(String dType) {
        return sysDictionaryService.findSysDictionaryByDType(dType);
    }
}
