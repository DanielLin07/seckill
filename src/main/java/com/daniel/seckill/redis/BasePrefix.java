package com.daniel.seckill.redis;

/**
 * Redis-Key接口的基本实现类
 *
 * @author DanielLin07
 * @date 2018/11/13 22:00
 */
public abstract class BasePrefix implements KeyPrefix {

    /**
     * 过期时间
     */
    private int expireSeconds;

    /**
     * Key的前缀名
     */
    private String prefix;

    public BasePrefix(String prefix) {//0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String key = getClass().getSimpleName().toLowerCase().replace("key", "");
        return key + ":" + this.prefix;
    }

}
