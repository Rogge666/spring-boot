package com.rogge.module.redis.impl;

import com.rogge.module.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2020-06-13.
 * @since 1.0.0
 */
public class RedisAloneClient implements RedisClient {

    @Autowired(required = false)
    private JedisPool jedisPool;

    /**
     * 设置永不过期的KEY
     *
     * @param key   KEY
     * @param value Value
     * @throws Exception
     */
    @Override
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public boolean hasKey(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 判断KEY是否存在
     *
     * @param key KEY
     * @return true:存在-false:不存在
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 根据KEY获取对应Value
     *
     * @param key KEY
     * @return String Value
     * @throws Exception
     */
    @Override
    public String get(String key){

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void setWithExpireTime(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, Integer.valueOf(expireTime + ""));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void delete(String key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 将KEY进行自加1操作
     * <p>
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * </p>
     *
     * @param key KEY
     * @return 加值后的结果 整数
     */
    @Override
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 通过key给指定的value加值,如果key不存在,则这是value为该值
     *
     * @param key     KEY
     * @param increment 待加目标值
     * @return 加值后的结果 整数
     */
    @Override
    public Long incrBy(String key, Long increment) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incrBy(key, increment);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 对key的值做减减操作,如果key不存在,则设置key为-1
     *
     * @param key KEY
     * @return 减值后的结果 整数
     */
    @Override
    public Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 通过KEY给指定的KEY减值，如果KEY不存在，则该KEY的值就是对应的减值
     *
     * @param key     KEY
     * @param decrement 待减的目标值
     * @return 减值后的结果 整数
     */
    @Override
    public Long decrBy(String key, Long decrement) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decrBy(key, decrement);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long getExpire(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long result = jedis.setnx(key,value);
            return result == 1;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public boolean addQueue(String queueName, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(queueName,value);
        } finally {
            closeJedis(jedis);
        }
        return true;
    }

    @Override
    public String popQueue(String queueName) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(queueName);
        } finally {
            closeJedis(jedis);
        }
    }

    private void closeJedis(Jedis jedis){
        if(null != jedis){
            jedis.close();
        }
    }
}
