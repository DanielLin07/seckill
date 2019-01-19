package com.daniel.seckill.dao;

import com.daniel.seckill.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户相关DAO
 *
 * @author DanielLin07
 * @date 2018/11/18 16:45
 */
@Mapper
@Repository
public interface UserDAO {

    /**
     * 根据用户Id获取对应用户
     *
     * @param id 用户Id
     * @return 成功则返回对应用户
     */
    User queryById(int id);

    /**
     * 根据用户名获取对应用户
     *
     * @param username 用户名
     * @return 成功则返回对应用户
     */
    User queryByUsername(String username);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    List<User> queryList();

    /**
     * 添加新用户
     *
     * @param user 新用户
     */
    void add(User user);

}
