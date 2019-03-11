package com.daniel.seckill.redis;

/**
 * 用户Redis-Key
 *
 * @author DanielLin07
 * @date 2018/11/13 22:04
 */
public class UserKey extends BasePrefix {

    /**
     * Token的过期时间
     */
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey(0, "id");

    public static UserKey getByName = new UserKey(0, "name");

    public static UserKey getByToken = new UserKey(TOKEN_EXPIRE, "token");
}
