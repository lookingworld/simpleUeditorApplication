package com.itheima.filter;

import com.itheima.bean.User;
import com.itheima.service.IUserService;
import com.itheima.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PermissFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PermissFilter.class);
    private static List<String> users = new ArrayList<String>();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //获取请求的页面
        String requestURI = request.getRequestURI();
        requestURI = requestURI.substring(request.getContextPath().length());
        //使用获取的页面进行判断
        if(users.contains(requestURI)){
            //需要被拦截
            //校验登录
            User loginUser = (User)request.getSession().getAttribute("loginUser");
            if(loginUser==null){
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                return;
            }
            //已经登录
            IUserService userService = new UserServiceImpl();
            chain.doFilter(request,response);
        }else{
            //不需要权限，不需要拦截
            chain.doFilter(request,response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        logger.info("==========================加载users到集合开始==========================");
        BufferedReader userReader = new BufferedReader(new InputStreamReader(PermissFilter.class.getClassLoader().getResourceAsStream("user.txt")));
        String line="";
        try {
            while((line=userReader.readLine())!=null){
                users.add(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(userReader!=null){
                try {
                    userReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("用户："+users);
        logger.info("==========================加载users到集合完成==========================");

    }

}
