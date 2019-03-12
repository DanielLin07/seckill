package com.daniel.seckill.service;

import com.alibaba.fastjson.JSON;
import com.daniel.seckill.dao.UserDAO;
import com.daniel.seckill.model.User;
import com.daniel.seckill.redis.UserKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private RedisService redisService;

    /**
     * 根据用户Id获取对应用户
     *
     * @param id 用户Id
     * @return 成功则返回对应用户
     */
    public User queryById(int id) {
        // 先从缓存中取用户数据
        User user = redisService.get(UserKey.getById, id, User.class);
        if (user != null) {
            return user;
        }

        // 缓存中取不到则从数据库中取，并存到缓存当中
        user = userDAO.queryById(id);
        if (user != null) {
            redisService.set(UserKey.getById, id, JSON.toJSONString(user));
        }
        return user;
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
        return redisService.get(UserKey.getByToken, token, User.class);
    }
}
