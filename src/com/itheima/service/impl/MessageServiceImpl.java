package com.itheima.service.impl;

import com.alibaba.fastjson.JSON;
import com.itheima.bean.Message;
import com.itheima.bean.MessageOut;
import com.itheima.service.IMessageService;
import com.itheima.utils.JdbcUtils;
import com.itheima.utils.JedisUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageServiceImpl implements IMessageService {
    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    @Override
    public void addMessage(Message message,String realPath) {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        //防止description过长，截取字符串
        if(message.getDescription().length()>155) {
            message.setDescription(message.getDescription().substring(0, 100) + "...");
        }
        String sql = "insert into message (tittle,message,description,createtime,categoryid) values(?,?,?,?,?)";
        try {
            qr.update(sql,message.getTittle(),message.getMessage(),message.getDescription(),new Timestamp(System.currentTimeMillis()),message.getCategoryid());
            logger.info("添加一条message成功");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("添加message失败");
        }
        //将更新后的数据重新加载到redis中
        setRedis();
        logger.info("开始复制上传数据到指定目录。。。。。。。。。。。。。。。。。。。。");
        //获取匹配src的正则表达式
        String reg = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
        Pattern p = Pattern.compile(reg);
        //获取匹配器对象
        Matcher matcher = p.matcher(message.getMessage());
        List<String> list = new ArrayList<>();
        while(matcher.find()){
            String url = matcher.group();
            url = url.substring("src=\"".length(),url.length()-1);
            if(url.contains("http://localhost:8080/")){
                url = url.substring("http://localhost:8080/".length());
            }
            list.add(url);
        }
        logger.info("获取到上传图片的相对路径"+list);
        try {
            String rootPath = ResourceBundle.getBundle("upload").getString("copyPath");
            if(list!=null&&list.size()!=0) {
                for (String url : list) {
                    FileUtils.copyFile(new File(realPath, url), new File(rootPath,url));
                    logger.debug(rootPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("copy上传图片异常"+e.getMessage());
            throw new RuntimeException("copy上传图片异常");
        }
        logger.info("复制上传数据到指定目录结束。。。。。。。。。。。。。。。。。。。。");
    }

    public void setRedis(){
        logger.info("系统初始化开始，加载信息到redis");
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "select description,m.id id,m.tittle tittle,createtime,updatetime,message,c.id cid,c.name cname from message m,category c where m.categoryid=c.id order by c.id,createtime";
            List<MessageOut> query = qr.query(sql, new BeanListHandler<MessageOut>(MessageOut.class));
            //先删除原来redis中的内容
            jedis.del("message");
            jedis.set("message", JSON.toJSONString(query));

            for(int i =0;i<query.size();i++){
                query.get(i).setMessage("");
            }
            jedis.del("messageSimple");
            jedis.set("messageSimple", JSON.toJSONString(query));
            logger.info("系统初始化成功，加载信息到redis");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("加载数据到redis失败");
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
    }

    @Override
    public String getMessageById(String id) {
        Integer _id = null;
        String result= "";
        try {
            _id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("获取message的请求参数id错误1");
            throw new RuntimeException("参数错误1");
        }
        if(_id==null||_id<=0){
            logger.error("获取message的请求参数id错误2");
            throw new RuntimeException("参数错误2");
        }
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            String message = jedis.get("message");
            if(StringUtils.isNotBlank(message)) {
                long l1 = System.currentTimeMillis();
                List<MessageOut> parse = JSON.parseArray(message, MessageOut.class);
                for (MessageOut messageOut : parse) {
                    if (messageOut.getId() == _id) {
                        result = JSON.toJSONString(messageOut);
                    }
                }
                long l2 = System.currentTimeMillis();
                logger.info("从redis中获取指定id的message，时间："+(l2-l1));
                if(StringUtils.isBlank(result)){
                    logger.warn("redis中没有指定的数据");
                    String sql = "select description,m.id id,m.tittle tittle,createtime,updatetime,message,c.id cid,c.name cname from message m,category c where m.categoryid=c.id and m.id = ?";
                    QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
                    MessageOut query = qr.query(sql, new BeanHandler<MessageOut>(MessageOut.class), _id);
                    result = JSON.toJSONString(query);
                }
            }else{
                logger.warn("redis中没有获取到message数据");
                String sql = "select description,m.id id,m.tittle tittle,createtime,updatetime,message,c.id cid,c.name cname from message m,category c where m.categoryid=c.id and m.id = ?";
                QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
                MessageOut query = qr.query(sql, new BeanHandler<MessageOut>(MessageOut.class), _id);
                result = JSON.toJSONString(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("通过id获取message数据失败");
            throw new RuntimeException("通过id获取message数据失败");
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public String getAllMessage() {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            result = "";
            long l1 = System.currentTimeMillis();
            result = jedis.get("messageSimple");
            long l2 = System.currentTimeMillis();
            logger.info("从redis中获取所有信息,时间："+(l2-l1));
            if(StringUtils.isBlank(result)){
                logger.info("从redis中获取所有信息为空，从数据库中查找");
                QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
                String sql = "select description,m.id id,m.tittle tittle,createtime,updatetime,c.id cid,c.name cname from message m,category c where m.categoryid=c.id order by c.id,createtime";
                List<MessageOut> query = qr.query(sql, new BeanListHandler<MessageOut>(MessageOut.class));
                result = JSON.toJSONString(query);
                jedis.set("messageSimple",result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("获取所有信息出错");
            throw new RuntimeException("获取所有信息出错");
        } finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return result;
    }
}
