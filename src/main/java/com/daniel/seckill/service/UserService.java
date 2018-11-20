package com.daniel.seckill.service;

import com.daniel.seckill.dao.UserDAO;
import com.daniel.seckill.model.User;
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

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


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

        return userDAO.queryByUsername(username);
    }
}
