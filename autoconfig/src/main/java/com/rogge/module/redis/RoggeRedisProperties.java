package com.rogge.module.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "rogge.redis")
public class RoggeRedisProperties {

    /**
     * 模式：Pool Cluster（集群-分布式，高并发） Sentinel（哨兵-HA 高可用）
     */
    private String model;
    /**
     * 集群节点：用英文逗号隔开
     */
    private String nodes;
    /**
     * 密码
     */
    private String password;
    /**
     * 连接超时时间:毫秒
     */
    private int connectionTimeout;
    /**
     * 读取数据超时时间:毫秒
     */
    private int soTimeout;
    /**
     * 连接池最大连接数:负数无限制
     */
    private int maxActive;
    /**
     * 连接池中的最大空闲连接：负数表示不限制
     */
    private int maxIdle;
    /**
     * 连接池最大阻塞等待时间：负数表示不限制
     */
    private long maxWaitMillis;

    /**
     * 超时重试次数:负数表示集群节点数
     */
    private int maxAttempts;

    /**
     * 默认重试次数:1
     */
    public static final Integer DEFAULT_MAX_ATTEMPTS = 1;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
