package com.rogge.module.redis.impl;

import com.rogge.module.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

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
public class RedisClusterClient implements RedisClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAloneClient.class);

    @Autowired(required = false)
    private JedisCluster jedisCluster;

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
        LOGGER.info("RedisUtil:set cache key={},value={}", key, value);
    }

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    @Override
    public boolean hasKey(String key) {
        return jedisCluster.exists(key);
    }


    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    @Override
    public void setWithExpireTime(String key, String value, int expireTime) {
        jedisCluster.setex(key, expireTime, value);
        LOGGER.info("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", key, value, expireTime);
    }


    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    @Override
    public String get(String key) {
        String value = jedisCluster.get(key);
        LOGGER.info("RedisUtil:get cache key={},value={}", key, value);
        return value;
    }

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    @Override
    public void delete(String key) {
        jedisCluster.del(key);
        LOGGER.info("RedisUtil:delete cache key={}", key);
    }

    /**
     * @param key
     * @throws
     * @Title: expire
     * @Description: 更新key的失效时间
     */
    @Override
    public Long expire(String key, int seconds) {
        LOGGER.info("RedisUtil:expire cache key={}", key);
        return jedisCluster.expire(key, seconds);
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
        return jedisCluster.incr(key);
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
        return jedisCluster.incrBy(key, increment);
    }

    /**
     * 对key的值做减减操作,如果key不存在,则设置key为-1
     *
     * @param key KEY
     * @return 减值后的结果 整数
     */
    @Override
    public Long decr(String key) {
        return jedisCluster.decr(key);
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
        return jedisCluster.decrBy(key, decrement);
    }

    @Override
    public Long getExpire(String key){
        return jedisCluster.ttl(key);
    }

    @Override
    public boolean setIfAbsent(String key, String value) {
        Long result = jedisCluster.setnx(key,value);
        return result == 1;
    }

    @Override
    public boolean addQueue(String queueName, String value) {
        jedisCluster.lpush(queueName,value);
        return true;
    }

    @Override
    public String popQueue(String queueName) {
        return jedisCluster.lpop(queueName);
    }
}
