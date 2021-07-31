package com.xuecheng.manage_cms.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Auther:ghost
 * @Date:2021/7/26
 * @Description:com.xuecheng.manage_cms.util
 * @version:1.0
 */
public class FreeMarkerUtil {

    public static String gennerateHtml(String template, Map data){
        Configuration configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", template);
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            String page = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("template"), data);
            if (!StringUtils.isEmpty(page)) {
                return page;
            } else {
                return null;
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean uploadePageToServer(String page,String pagePath){

        if(StringUtils.isEmpty(page)){
            return false;
        }

        InputStream inputStream= IOUtils.toInputStream(page, StandardCharsets.UTF_8);
        if (inputStream == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
