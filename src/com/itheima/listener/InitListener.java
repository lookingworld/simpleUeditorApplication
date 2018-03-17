package com.itheima.listener;

import com.alibaba.fastjson.JSON;
import com.itheima.bean.MessageOut;
import com.itheima.service.IMessageService;
import com.itheima.service.impl.MessageServiceImpl;
import com.itheima.utils.JdbcUtils;
import com.itheima.utils.JedisUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.sql.SQLException;
import java.util.List;

public class InitListener implements ServletContextListener{
    private static final Logger logger = LogManager.getLogger(InitListener.class);
    // Public constructor is required by servlet spec
    public InitListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("监听器初始化开始");
        IMessageService messageService = new MessageServiceImpl();
        messageService.setRedis();
        logger.info("监听器初始化完成");
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
