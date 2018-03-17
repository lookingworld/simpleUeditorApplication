package com.itheima.service;

import com.itheima.bean.User;

public interface IUserService {
    /**
     * 用户登陆
     * @return
     */
    User login(User user);
}
