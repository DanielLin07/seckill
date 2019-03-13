package com.daniel.seckill.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * 封装Jedis操作的工具类
 *
 * @author DanielLin07
 * @date 2018/11/13 22:00
 */
@Repository
public class JedisAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 通过key获取储存在redis中的value
     *
     * @param key string数据的Key
     * @return 成功返回value；失败返回null
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 向Redis存入string数据,并释放连接资源，如果key已经存在 则覆盖
     *
     * @param key   string数据的Key
     * @param value string数据的value
     * @return 成功返回true；失败返回false
     */
    public Boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     *
     * @param keys 一个key，也可以是key数组
     * @return 返回删除成功的个数
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key向指定的value值追加值
     *
     * @param key 指定的key
     * @param str 追加值
     * @return 成功返回添加后value的长度；失败返回添加的value的长度；异常返回null
     */
    public Long append(String key, String str) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.append(key, str);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 指定的key
     * @return 存在返回true；不存在返回false
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置key的value,如果key已经存在则返回0,nx的语义为not exist
     *
     * @param key   指定key
     * @param value 要设置的value
     * @return 成功返回1；如果存在和发生异常，返回0
     */
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置key value并制定这个键值的有效期
     *
     * @param key     指定key
     * @param value   要设置的value
     * @param seconds 有效期，单位:秒
     * @return 成功返回true；失败和异常返回false
     */
    public Boolean setex(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key和offset，从指定的位置开始将原先value替换
     * 下标从0开始,offset表示从offset下标开始替换
     * 如果替换的字符串长度过小则会这样：
     * value : daniel@abc.cn
     * str : edf
     * 从下标6开始替换，则结果为daniel.edf.cn
     *
     * @param key    指定的key
     * @param str    要替换的内容
     * @param offset 下标位置
     * @return 返回替换后的value的长度
     */
    public Long setrange(String key, String str, int offset) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setrange(key, offset, str);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过批量的key获取批量的value
     *
     * @param keys 一个key，也可以是一个key数组
     * @return 成功返回value的集合, 失败返回空集合 ,异常返回空
     */
    public List<String> mget(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.mget(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 批量的设置string数据,也可以只设置一个
     * example:obj.mset(new String[]{"key2","value1","key2","value2"})
     *
     * @param keysValues keyValue数组，应遵循key和value相接
     * @return 成功返回true；失败或异常返回false
     */
    public Boolean mset(String... keysValues) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.mset(keysValues);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 批量的设置string数据,也可以只设置一个,如果key已经存在则会失败,操作会回滚
     * example:obj.msetnx(new String[]{"key2","value1","key2","value2"})
     *
     * @param keysValues keyValue数组，应遵循key和value相接
     * @return 成功返回true；失败或异常返回false
     */
    public Boolean msetnx(String... keysValues) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.msetnx(keysValues);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置key的值,并返回一个旧值
     *
     * @param key   指定key
     * @param value 要设置的新值
     * @return 成功则返回旧值；如果key不存在则返回null
     */
    public String getset(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.getSet(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过下标和key获取指定下标位置的value
     *
     * @param key         指定Key
     * @param startOffset 开始位置，从0开始，负数表示从右边开始截取
     * @param endOffset   结束位置
     * @return 成功返回对应value；如果没有或者异常返回null
     */
    public String getrange(String key, int startOffset, int endOffset) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key对value进行自增1操作，
     *
     * @param key 指定的key
     * @return 返回加值后的结果；当value不是int类型时会返回0；当key不存在时则value为1
     */
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key给指定的value加值
     *
     * @param key     指定的key
     * @param integer 要加的值
     * @return 返回加值后的结果；当异常会返回0；当key不存在时则value为该值
     */
    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incrBy(key, integer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 对key的值做自减操作
     *
     * @param key 指定的key
     * @return 返回加值后的结果；当异常会返回0；当key不存在，则设置key为-1
     */
    public Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减去指定的值
     *
     * @param key     指定的key
     * @param integer 减去的值
     * @return 返回减值后的结果；当异常会返回0；当key不存在时则value为该值
     */
    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decrBy(key, integer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取value值的长度
     *
     * @param key 指定的key
     * @return 成功则返回value的长度；异常返回null
     */
    public Long serlen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.strlen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key给field设置指定的值，如果key不存在，则先创建
     *
     * @param key   指定的key
     * @param field 字段
     * @param value 要设置的值
     * @return 如果存在返回0；异常返回null
     */
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key给field设置指定的值，如果key不存在则先创建，
     *
     * @param key   指定的key
     * @param field 字段
     * @param value 要设置的值
     * @return 如果field已经存在，返回0；如果异常返回null
     */
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key同时设置hash的多个field
     *
     * @param key  指定的key
     * @param hash hash数据
     * @return 成功返回true；异常返回false
     */
    public Boolean hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key, hash);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key和field获取指定的value
     *
     * @param key   指定的key
     * @param field 字段
     * @return 成功则返回对应的value；没有则返回null
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key和fields获取指定的value 如果没有对应的value则返回null
     *
     * @param key    指定的key
     * @param fields 可以是一个string，也可以是string数组
     * @return 成功则返回对应的value；没有则返回null
     */
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key给指定的field的value加上给定的值
     *
     * @param key   指定的key
     * @param field 字段
     * @param value 要加上的值
     * @return 成功则返回value；异常则返回null
     */
    public Long hincrby(String key, String field, Long value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key和field判断是否有指定的value存在
     *
     * @param key   指定的key
     * @param field 字段
     * @return 存在则返回true；不存在则返回false；异常返回false
     */
    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回field的长度
     *
     * @param key 指定的key
     * @return 成功返回field的长度；异常返回0
     */
    public Long hlen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hlen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * 通过key删除指定的field
     *
     * @param key    指定的key
     * @param fields 可以是一个field，也可以是一个field数组
     * @return 成功返回删除的歌field个数；异常返回0
     */
    public Long hdel(String key, String... fields) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回所有的field
     *
     * @param key 指定的key
     * @return 成功则返回所有的Field的Set集合；失败则返回null
     */
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hkeys(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回所有和key有关的value
     *
     * @param key 指定的key
     * @return 成功则返回所有和key有关的value；异常返回null
     */
    public List<String> hvals(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hvals(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取所有的field和value
     *
     * @param key 指定的key
     * @return 成功获取所有的field和value；异常返回null
     */
    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key向list头部添加字符串
     *
     * @param key     指定的key
     * @param strings 可以是一个string，也可以是string数组
     * @return 成功则返回list的value个数，异常则返回null
     */
    public Long lpush(String key, String... strings) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, strings);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key向list尾部添加字符串
     *
     * @param key     指定的key
     * @param strings 可以是一个string，也可以是string数组
     * @return 成功则返回list的value个数，异常则返回null
     */
    public Long rpush(String key, String... strings) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key, strings);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key在list指定的位置之前或者之后 添加字符串元素
     *
     * @param key   指定的key
     * @param where LIST_POSITION枚举类型
     * @param pivot list里面的value
     * @param value 添加的value
     * @return 成功则返回插入个数；异常则返回null
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where,
                        String pivot, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.linsert(key, where, pivot, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key设置list指定下标位置的value，如果下标超过list里面value的个数则报错
     *
     * @param key   指定的key
     * @param index 索引（从0开始）
     * @param value 要设置的值
     * @return 成功返回则返回true，异常则返回false
     */
    public boolean lset(String key, Long index, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lset(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key从对应的list中删除指定的count个和value相同的元素
     *
     * @param key   指定的key
     * @param count 当count为0时删除全部
     * @param value 指定的值
     * @return 成功返回被删除的个数；异常返回null
     */
    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key保留list中从start下标开始到end下标结束的value值
     *
     * @param key   指定的key
     * @param start 开始下标
     * @param end   结束下标
     * @return 成功返回true；异常返回false
     */
    public Boolean ltrim(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.ltrim(key, start, end);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key从list的头部删除一个value，并返回该value
     *
     * @param key 指定的key
     * @return 成功返回被删除的元素的value；失败返回null
     */
    public String lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key从list尾部删除一个value，并返回该value
     *
     * @param key 指定的key
     * @return 成功返回被删除的元素的value；失败返回null
     */
    public String rpop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key从一个list的尾部删除一个value并添加到另一个list的头部，并返回该value
     *
     * @param srckey 要被删除元素的List
     * @param dstkey 要被添加元素的List
     * @return 成功则返回该value；如果第一个list为空或者不存在则返回null
     */
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpoplpush(srckey, dstkey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取list中指定下标位置的value
     *
     * @param key   指定的key
     * @param index 指定的下标
     * @return 指定下标的value；如果没有返回null
     */
    public String lindex(String key, long index) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lindex(key, index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回list的长度
     *
     * @param key 指定的key
     * @return 成功则返回list的长度；异常返回null
     */
    public Long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取list指定下标位置的value
     *
     * @param key   指定的key
     * @param start 开始下标
     * @param end   结束下标
     * @return 返回指定下标位置的value；如果start为0，end为-1，则返回全部的list中的value
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key向指定的set中添加value
     *
     * @param key     指定的key
     * @param members 可以是一个string，也可以是一个String数组
     * @return 返回添加成功的个数
     */
    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key删除set中对应的value值
     *
     * @param key     指定的key
     * @param members 可以是一个string，也可以是一个string数组
     * @return 返回删除的个数
     */
    public Long srem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key随机删除一个set中的value并返回该值
     *
     * @param key 指定的key
     * @return 成功则返回被删除的value；异常返回null
     */
    public String spop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.spop(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取set中的差集，以第一个set为标准
     *
     * @param keys 可以是一个string，也可以是string数组
     * @return 两个set的差集；如果是一个string作为key，则返回set中所有的value
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sdiff(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取set中的差集并存入到另一个key中，以第一个set为标准
     *
     * @param dstkey 差集存入的key
     * @param keys   可以是一个string，也可以是string数组
     * @return 成功则返回差集个数；如果是一个string则返回set中所有的value
     */
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sdiffstore(dstkey, keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取指定set中的交集
     *
     * @param keys 可以是一个string，也可以是一个string数组
     * @return 若干个set的交集
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sinter(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取指定set中的交集，并将结果存入新的set中
     *
     * @param dstkey 交集存入的key
     * @param keys   可以是一个string，也可以是一个string数组
     * @return 成功则返回交集个数；异常则返回空
     */
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sinterstore(dstkey, keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回所有set的并集
     *
     * @param keys 可以是一个string，也可以是一个string数组
     * @return 若干个set的并集
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sunion(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回所有set的并集，并存入到新的set中
     *
     * @param dstkey 并集存入的key
     * @param keys   可以是一个string，也可以是一个string数组
     * @return 成功则返回并集个数；异常则返回null
     */
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sunionstore(dstkey, keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key将set中的value移除并添加到第二个set中
     *
     * @param srckey 需要移除的
     * @param dstkey 添加的
     * @param member set中的value
     * @return 成功则返回移除个数；异常则返回null
     */
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smove(srckey, dstkey, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取set中value的个数
     *
     * @param key 指定的key
     * @return 指定的key的value的个数
     */
    public Long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key判断value是否是set中的元素
     *
     * @param key    指定的key
     * @param member 要判断是否存在的元素
     * @return 是set中的元素则返回true；不是则返回false
     */
    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取set中随机的value,不删除元素
     *
     * @param key 指定的key
     * @return 随机的value
     */
    public String srandmember(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srandmember(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取set中所有的value
     *
     * @param key 指定的key
     * @return 成功则返回所有的value；异常则返回null
     */
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key向zset中添加value、score，其中score就是用来排序的
     * 如果该value已经存在则根据score更新元素
     *
     * @param key          指定的key
     * @param scoreMembers 各个value的score值
     * @return 成功则返回添加个数；异常则返回null
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key向zset中添加value、score，其中score就是用来排序的
     * 如果该value已经存在则根据score更新元素
     *
     * @param key    指定的key
     * @param score  得分值
     * @param member 指定的member
     * @return 成功则返回添加的个数；异常则返回null
     */
    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, score, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key删除在zset中指定的value
     *
     * @param key     指定的key
     * @param members 可以是一个string，也可以是一个string数组
     * @return 成功则返回删除的个数；异常则返回null
     */
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key, members);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key增加该zset中value的score的值
     *
     * @param key    指定的key
     * @param score  得分值
     * @param member 指定的member
     * @return 成功则返回增加后的score的值；异常返回null
     */
    public Double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回zset中value的排名，标从小到大排序
     *
     * @param key    指定的key
     * @param member 指定的member
     * @return 成功则返回指定的key在zset中的排名；异常返回null
     */
    public Long zrank(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrank(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回zset中value的排名，下标从大到小排序
     *
     * @param key    指定的key
     * @param member 指定的member
     * @return 成功则返回指定的key在zset中的排名；异常返回null
     */
    public Long zrevrank(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrank(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key将获取score从start到end中zset的value，socre从大到小排序
     *
     * @param key   指定的key
     * @param start 开始下标
     * @param end   结束下标
     * @return 成功则返回指定区间的value；当start为0，end为-1时返回全部；异常返回null
     */
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回指定score内zset中的value
     *
     * @param key 指定的key
     * @param max 最大的score
     * @param min 最小的score
     * @return 成功则返回指定区间的value；异常返回null
     */
    public Set<String> zrangebyscore(String key, String max, String min) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 返回指定区间内zset中value的数量
     *
     * @param key 指定的key
     * @param min 最小的score
     * @param max 最大的score
     * @return 成功则返回指定区间的value的数量；异常返回null
     */
    public Long zcount(String key, String min, String max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcount(key, min, max);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key返回zset中的value个数
     *
     * @param key 指定的key
     * @return 成功则返回value的数量；异常返回null
     */
    public Long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key获取zset中value的score值
     *
     * @param key    指定的key
     * @param member 指定的member
     * @return 成功则返回value的score值；异常返回null
     */
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key删除给定区间内的元素
     *
     * @param key   指定的key
     * @param start 开始下标
     * @param end   结束下标
     * @return 成功则返回删除的value的个数；异常返回null
     */
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key删除指定score内的元素
     *
     * @param key   指定的key
     * @param start 开始的score
     * @param end   结束的score
     * @return 成功则返回删除的value的个数；异常返回nul
     */
    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 返回满足pattern表达式的所有key
     *
     * @param pattern 表达式
     * @return 成功则返回满足pattern表达式的所有key；用key(*)则返回所有key；失败则返回null
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(pattern);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过key判断值得类型
     *
     * @param key 指定的key
     * @return 成功则返回指定的key的值类型；异常则返回null
     */
    public String type(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.type(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 返还Jedis连接到连接池
     *
     * @param jedis 需要返回的Jedis连接
     */
    private static void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
