package com.daniel.seckill.service;

import com.alibaba.fastjson.JSON;
import com.daniel.seckill.redis.BasePrefix;
import com.daniel.seckill.redis.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Redis业务封装
 *
 * @author DanielLin07
 * @date 2019/3/11 16:59
 */
@Service
public class RedisService {

    public static final String SPLIT = ":";

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean set(String key, String value) {
        return jedisAdapter.set(key, value);
    }

    public boolean set(BasePrefix prefix, String value) {
        return jedisAdapter.set(prefix.getPrefix(), value);
    }

    public boolean set(BasePrefix prefix, Object keyName, String value) {
        return jedisAdapter.set(prefix.getPrefix() + SPLIT + keyName, value);
    }

    public boolean setex(String key, String value, int seconds) {
        return jedisAdapter.setex(key, value, seconds);
    }

    public boolean setex(BasePrefix prefix, String value) {
        return jedisAdapter.setex(prefix.getPrefix(), value, prefix.expireSeconds());
    }

    public boolean setex(BasePrefix prefix, Object keyName, String value) {
        return jedisAdapter.setex(
                prefix.getPrefix() + SPLIT + keyName, value, prefix.expireSeconds());
    }

    public String get(String key) {
        return jedisAdapter.get(key);
    }

    public String get(BasePrefix prefix) {
        return jedisAdapter.get(prefix.getPrefix());
    }

    public String get(BasePrefix prefix, Object keyName) {
        return jedisAdapter.get(prefix.getPrefix() + SPLIT + keyName);
    }

    public <T> T get(String key, Class<T> clazz) {
        return JSON.toJavaObject(JSON.parseObject(jedisAdapter.get(key)), clazz);
    }

    public <T> T get(BasePrefix prefix, Object keyName, Class<T> clazz) {
        return JSON.toJavaObject(JSON.parseObject(
                jedisAdapter.get(prefix.getPrefix() + SPLIT + keyName)), clazz);
    }

}
