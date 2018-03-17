package com.itheima.web;

import com.itheima.service.ICategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.service.impl.MessageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CategoryServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger(CategoryServlet.class);
    protected void getCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ICategoryService categoryService = new CategoryServiceImpl();
        String result = categoryService.getCategory();
        response.getWriter().write(result);
    }

}
