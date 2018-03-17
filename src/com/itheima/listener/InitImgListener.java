package com.itheima.listener;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class InitImgListener implements ServletContextListener{
    private static final Logger logger = LogManager.getLogger(InitImgListener.class);
    // Public constructor is required by servlet spec
    public InitImgListener() {

    }
    private static final ResourceBundle upload = ResourceBundle.getBundle("upload");

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("监听器初始化开始");

        if(upload.containsKey("initCopyFileIn")){
            //包含这个key
            if("true".equalsIgnoreCase(upload.getString("initCopyFileIn"))){
                //key对应的值为true
                //将别处的图片copy回项目
                try {
                    FileUtils.copyDirectory(new File(String.valueOf(new File(upload.getString("copyPath"),"ueditor"))),new File(sce.getServletContext().getRealPath("/"),"/ueditor"));
                    logger.info("服务器启动,迁移图片至服务器成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("服务器启动,迁移图片至服务器失败"+e.getMessage());
                    throw new RuntimeException("服务器启动,迁移图片至服务器失败");
                }
            }
        }
        logger.info("监听器初始化完成");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        String booCopyOut = upload.getString("destoryCopyDirOut");
        if("true".equalsIgnoreCase(booCopyOut)){
            try {
                FileUtils.copyDirectory(new File(sce.getServletContext().getRealPath("/"),"/ueditor"),new File(String.valueOf(new File(upload.getString("copyPath"),"ueditor"))));
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("服务器关闭,迁移图片至本地失败"+e.getMessage());
                throw new RuntimeException("服务器关闭,迁移图片至本地失败");
            }
        }
    }
}
