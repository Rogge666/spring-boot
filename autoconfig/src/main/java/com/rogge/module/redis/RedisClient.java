package com.rogge.module.redis;

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
public interface RedisClient {

    /**
     * 设置永不过期的KEY
     *
     * @param key   KEY
     * @param value Value
     * @throws Exception
     */
    void set(String key, String value);

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    boolean hasKey(String key);

    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    String get(String key);

    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    void setWithExpireTime(String key, String value, int expireTime);

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    void delete(String key);

    /**
     * @param key
     * @throws
     * @Title: expire
     * @Description: 更新key的失效时间
     */
    Long expire(String key, int seconds);

    /**
     * 将KEY进行自加1操作
     * <p>
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * </p>
     *
     * @param key KEY
     * @return 加值后的结果 整数
     */
    Long incr(String key);

    /**
     * 通过key给指定的value加值,如果key不存在,则这是value为该值
     *
     * @param key     KEY
     * @param increment 待加目标值
     * @return 加值后的结果 整数
     */
    Long incrBy(String key, Long increment);

    /**
     * 对key的值做减减操作,如果key不存在,则设置key为-1
     *
     * @param key KEY
     * @return 减值后的结果 整数
     */
    Long decr(String key);

    /**
     * 通过KEY给指定的KEY减值，如果KEY不存在，则该KEY的值就是对应的减值
     *
     * @param key     KEY
     * @param decrement 待减的目标值
     * @return 减值后的结果 整数
     */
    Long decrBy(String key, Long decrement);

    /**
     * 通过KEY获取Redis剩余过期时间
     * @param key KEY
     * @return
     */
    Long getExpire(String key);

    /**
     * 只在键key不存在的情况下，将键key的值设置为value
     * 设置成功返回true，设置失败返回false
     * @param key
     * @param value
     * @return
     */
    boolean setIfAbsent(String key, String value);

    /**
     * 消息入队列
     * @param queueName
     * @param value
     * @return
     */
    boolean addQueue(String queueName, String value);

    /**
     * 消息出队列
     * @param queueName
     * @return
     */
    String popQueue(String queueName);
}
