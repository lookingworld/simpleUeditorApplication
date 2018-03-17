package com.itheima.web;

import com.itheima.bean.Message;
import com.itheima.service.IMessageService;
import com.itheima.service.impl.MessageServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MessageServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger(MessageServlet.class);
    private IMessageService messageService = new MessageServiceImpl();
    protected void getAllMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = messageService.getAllMessage();
        response.getWriter().write(result);
    }

    protected void getMessageById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String result = messageService.getMessageById(id);
        logger.info("前端请求数据id="+id);
        response.getWriter().write(result);
    }

    protected void receviceMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map parameterMap = request.getParameterMap();
        Message message = new Message();
        try {
            BeanUtils.populate(message, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加message数据异常");
            response.sendRedirect(request.getContextPath());
        }
        messageService.addMessage(message,getServletContext().getRealPath("/"));
        response.getWriter().write("ok");
    }
}
