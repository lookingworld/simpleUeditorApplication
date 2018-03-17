package com.itheima.web;

import com.itheima.bean.User;
import com.itheima.service.IUserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger(UserServlet.class);
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        User user = new User();
        if(StringUtils.isBlank(username)){
            response.sendRedirect(request.getContextPath());
            return;
        }
        user.setUsername(username);
        String password = request.getParameter("password");
        if(StringUtils.isBlank(password)){
            response.sendRedirect(request.getContextPath());
            return;
        }
        System.out.println(username+":"+password);
        Jedis jedis = JedisUtils.getJedis();
        String errorTime = jedis.get(username);
        if(StringUtils.isBlank(errorTime)||(StringUtils.isNotBlank(errorTime)&&(3-Integer.parseInt(errorTime))>=1)) {
            user.setPassword(password);
            IUserService userService = new UserServiceImpl();
            User loginUser = userService.login(user);
            if(loginUser!=null){
                request.getSession().setAttribute("loginUser",loginUser);
                response.sendRedirect(request.getContextPath()+"/admin_add.html");
                jedis.del(username);
                logger.info("用户登陆成功");
                return;
            }

            if(StringUtils.isBlank(errorTime)) {
                //第一次错
                jedis.setex(username, 5 * 60, "1");
                request.setAttribute("msg", "用户名密码错误" + 1 + "次，还有" + 2 + "次机会");
            }else {
                //错了多次
                jedis.setex(username,5*60,(Integer.parseInt(errorTime) + 1) + "");
                request.setAttribute("msg", "用户名密码错误" + (Integer.parseInt(errorTime) + 1) + "次，还有" + (2 - Integer.parseInt(errorTime)) + "次机会");
            }
            logger.info("用户名密码错误");
        }else{
            request.setAttribute("msg", "用户名密码锁定,请5分钟后再试");
            logger.info("用户名密码锁定"+username);
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

}
