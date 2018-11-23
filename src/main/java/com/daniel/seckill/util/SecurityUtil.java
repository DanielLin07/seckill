package com.daniel.seckill.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 与安全相关的工具类
 *
 * @author DanielLin07
 * @date 2018/11/18 20:06
 */
public class SecurityUtil {

    /**
     * 加密算法
     */
    private static final String ALGORITHM_NAME = "MD5";

    /**
     * 加密次数
     */
    private static final int HASH_ITERATIONS = 2;


    /**
     * 根据用户名以及密码生成加密对，加密算法采用两次MD5加密
     *
     * @param username 用户名，用于与生成的盐一同加密
     * @param password 密码
     * @return 加密对
     */
    public static Map<String, String> genEncryptPasswordAndSalt(String username, String password) {

        Map<String, String> result = new HashMap<>(2);
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String encodedPassword = encryptPassword(password, username, salt);
        result.put("password", encodedPassword);
        result.put("salt", salt);
        return result;
    }


    /**
     * 生成加密后的密码
     *
     * @param password 加密前的密码
     * @param username 用户名，用于与盐一起使用
     * @param salt     盐
     * @return 加密后的密码
     */
    public static String encryptPassword(String password, String username, String salt) {
        return new SimpleHash(ALGORITHM_NAME, password, username + salt, HASH_ITERATIONS).toHex();
    }


    /**
     * 生成随机UUID字符串
     *
     * @return UUID字符串
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
