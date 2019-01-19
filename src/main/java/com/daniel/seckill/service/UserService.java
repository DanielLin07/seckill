package com.daniel.seckill.service;

import com.alibaba.fastjson.JSON;
import com.daniel.seckill.dao.UserDAO;
import com.daniel.seckill.model.User;
import com.daniel.seckill.redis.JedisAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户相关的Service
 *
 * @author DanielLin07
 * @date 2018/11/18 16:53
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 根据用户Id获取对应用户
     *
     * @param id 用户Id
     * @return 成功则返回对应用户
     */
    public User queryById(int id) {
        return userDAO.queryById(id);
    }

    /**
     * 根据用户名获取对应用户
     *
     * @param username 用户名
     * @return 成功则返回对应用户
     */
    public User queryByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return userDAO.queryByUsername(username);
    }

    /**
     * 根据token到Redis中获取对应用户
     *
     * @param token token
     * @return 成功则返回对应用户
     */
    public User queryByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return JSON.toJavaObject(JSON.parseObject(jedisAdapter.get("user:token:" + token)), User.class);
    }
}
