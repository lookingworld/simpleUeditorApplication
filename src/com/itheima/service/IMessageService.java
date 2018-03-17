package com.itheima.service;

import com.itheima.bean.Message;

/**
 *
 */
public interface IMessageService {
    /**
     * 添加message信息
     * @param message
     */
    void addMessage(Message message,String realPath);

    /**
     * 向redis中添加数据
     */
    void setRedis();

    /**
     * 根据id查找相应的数据
     * @param id
     * @return
     */
    String getMessageById(String id);

    /**
     * 获得所有的信息，简单的
     * @return
     */
    String getAllMessage();
}
