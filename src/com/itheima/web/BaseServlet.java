package com.itheima.web;

import com.itheima.listener.InitListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(BaseServlet.class);
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("methodName");
        if(StringUtils.isNotBlank(methodName)) {
            logger.info("执行"+methodName+"方法");
            try {
                Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(this,request, response);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("请求方法异常");
            }
        }else{
            logger.warn("请求方法的参数为空");
        }
    }

//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String methodName = req.getParameter("methodName");
//        if(StringUtils.isNotBlank(methodName)) {
//            logger.info("执行"+methodName+"方法");
//            try {
//                Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//                method.invoke(this,req, resp);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("请求方法异常" + System.lineSeparator() + e.getMessage());
//            }
//        }else{
//            logger.warn("请求方法的参数为空");
//        }
//    }
}
