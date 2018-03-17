package com.itheima.service.impl;

import com.itheima.bean.User;
import com.itheima.service.IUserService;
import com.itheima.utils.JdbcUtils;
import com.itheima.utils.MD5Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class UserServiceImpl implements IUserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Override
    public User login(User user) {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select id,username from user where username=? and password = ?";
        User query = null;
        try {
            query = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), MD5Utils.getPassword(user.getPassword()));
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("用户登陆失败");
            throw new RuntimeException("用户登陆失败");
        }
        return query;
    }
}
