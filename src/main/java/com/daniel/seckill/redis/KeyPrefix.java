package com.daniel.seckill.redis;

/**
 * Redis-Key定义接口
 *
 * @author DanielLin07
 * @date 2018/11/13 22:00
 */
public interface KeyPrefix {

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    int expireSeconds();

    /**
     * 获取Redis前缀
     *
     * @return Redis前缀
     */
    String getPrefix();

}
