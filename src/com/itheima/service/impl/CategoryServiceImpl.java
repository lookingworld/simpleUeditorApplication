package com.itheima.service.impl;

import com.alibaba.fastjson.JSON;
import com.itheima.bean.Category;
import com.itheima.service.ICategoryService;
import com.itheima.utils.JdbcUtils;
import com.itheima.utils.JedisUtils;
import com.itheima.web.MessageServlet;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    @Override
    public String getCategory() {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = JedisUtils.getJedis();
            long l1 = System.currentTimeMillis();
            result = jedis.get("category");
            long l2 = System.currentTimeMillis();
            logger.info("从redis中获取Category时间："+(l2-l1));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("从redis中查category失败");
            throw new RuntimeException("从redis中获取category出错");
        }
        try {
            if(StringUtils.isBlank(result)) {
                QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
                String sql = "select * from category";
                List<Category> query = null;
                try {
                    query = qr.query(sql, new BeanListHandler<Category>(Category.class));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                logger.info(query);
                result = JSON.toJSONString(query);
                jedis.set("category",result);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("从数据库中查category失败");
            throw new RuntimeException("从redis中获取category出错");
        } finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return result;
    }
}
