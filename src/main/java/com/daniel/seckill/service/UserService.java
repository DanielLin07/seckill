package com.daniel.seckill.service;

import com.alibaba.fastjson.JSON;
import com.daniel.seckill.common.CodeMsg;
import com.daniel.seckill.dao.UserDAO;
import com.daniel.seckill.exception.GlobalException;
import com.daniel.seckill.model.User;
import com.daniel.seckill.redis.UserKey;
import com.daniel.seckill.util.SecurityUtil;
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
    public User queryById(long id) {
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

    /**
     * 更新用户密码
     *
     * @param token       用户token，用于更新缓存
     * @param id          用户Id
     * @param newPassword 新密码
     * @return 更新结果，更新成功返回true
     */
    public boolean updatePassword(String token, long id, String newPassword) {
        // 根据Id从数据库中获取User信息
        User user = queryById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.USERNAME_NOT_EXIST);
        }

        // 更新数据库的信息
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(SecurityUtil.encryptPassword(newPassword, user.getUsername(), user.getSalt()));
        userDAO.update(toBeUpdate);

        // 最后处理缓存
        redisService.delete(UserKey.getById, id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.getByToken, token, JSON.toJSONString(user));
        return true;
    }
}
