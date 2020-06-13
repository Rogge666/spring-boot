package com.rogge.module.redis;

import com.rogge.module.redis.impl.RedisAloneClient;
import com.rogge.module.redis.impl.RedisClusterClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

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
@Configuration
@ConditionalOnClass({JedisConnection.class, RedisOperations.class, Jedis.class})
@EnableConfigurationProperties(RoggeRedisProperties.class)
public class RoggeRedisConfiguration {

    private RoggeRedisProperties mProperties;

    public RoggeRedisConfiguration(RoggeRedisProperties properties) {
        this.mProperties = properties;
    }

    @ConditionalOnExpression("'Pool'.equals('${rogge.redis.model}')")
    @Bean
    public JedisPool jedisPool() {
        String[] nodes = mProperties.getNodes().split(",");
        String[] nodeAttrs = nodes[0].trim().split(":");
        if (StringUtils.isEmpty(mProperties.getPassword())) {
            return new JedisPool(config(), nodeAttrs[0], Integer.valueOf(nodeAttrs[1]), mProperties.getConnectionTimeout());
        } else {
            return new JedisPool(config(), nodeAttrs[0], Integer.valueOf(nodeAttrs[1]), mProperties.getConnectionTimeout(), mProperties.getPassword());
        }
    }

    @ConditionalOnExpression("'Cluster'.equals('${rogge.redis.model}')")
    @Bean
    public JedisCluster jedisCluster() {
        String[] nodes = mProperties.getNodes().split(",");
        // 集群
        Set<HostAndPort> nodeSet = new HashSet<>(nodes.length);
        for (String node : nodes) {
            String[] nodeAttrs = node.trim().split(":");
            HostAndPort hap = new HostAndPort(nodeAttrs[0], Integer.valueOf(nodeAttrs[1]));
            nodeSet.add(hap);
        }

        // redis节点数大于1时，说明为集群
        if (nodes.length > 1) {
            if (mProperties.getMaxAttempts() == 0) {
                mProperties.setMaxAttempts(RoggeRedisProperties.DEFAULT_MAX_ATTEMPTS);
            }
        }

        // 重试次数设置为负数时，默认为集群节点数
        if (mProperties.getMaxAttempts() < 0) {
            mProperties.setMaxAttempts(nodes.length);
        }

        if (StringUtils.isEmpty(mProperties.getPassword())) {
            return new JedisCluster(nodeSet, mProperties.getConnectionTimeout(), mProperties.getMaxAttempts(), config());
        } else {
            return new JedisCluster(nodeSet, mProperties.getConnectionTimeout(), mProperties.getSoTimeout(), mProperties.getMaxAttempts(), mProperties.getPassword(), config());
        }
    }

    @Bean
    public RedisClient initRedisClient() throws Exception {
        String lModel = mProperties.getModel();
        if(StringUtils.isEmpty(lModel)){
            throw new Exception("请设置RedisModel");
        }
        switch (lModel) {
            case "Pool":
                return new RedisAloneClient();
            case "Cluster":
                return new RedisClusterClient();
            case "Sentinel":
                return new RedisClusterClient();
            default:
                throw new Exception("RedisModel超出类型");
        }
    }

    private JedisPoolConfig config() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(mProperties.getMaxActive());
        poolConfig.setMaxIdle(mProperties.getMaxIdle());
        poolConfig.setMaxWaitMillis(mProperties.getMaxWaitMillis());
        return poolConfig;
    }


    public RoggeRedisProperties getProperties() {
        return mProperties;
    }

    public void setProperties(RoggeRedisProperties properties) {
        mProperties = properties;
    }
}
