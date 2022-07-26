package com.example.demo.commons.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liwenji
 * @ClassName JedisPoolFactory
 * @Description TODO，配置cache工厂
 * @date 2022/6/21 11:32
 * @Version 1.0
 */
@Service
public class RedisCacheFactory {

    private static JedisPool jedisPool = null;

    @Resource
    private RedisConfigProperties redisConfigProperties;

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheFactory.class);

    /**
     * Jedis 单机版，获取JedisPool池子
     *
     * @return JedisPool
     */
    public JedisPool jedisPoolFactory() {
        if (jedisPool == null) {
            // 创建连接池
            //执行命令如下
            try {
                // Host为实例的IP， Port 为实例端口，Password 为实例的密码，timeout 既是连接超时又是读写超时
                jedisPool = new JedisPool(getJedisPoolConfig(),
                        redisConfigProperties.getHost(),
                        redisConfigProperties.getPort(),
                        redisConfigProperties.getCommandTimeout(),
                        redisConfigProperties.getPassword()
                );
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return jedisPool;
    }

    /**
     * Jedis - 集群、连接池模式
     *
     * @return JedisCluster
     */
    public JedisCluster jedisClusterFactory() {

        /* 切割节点信息 */
        String[] nodes = redisConfigProperties.getClusterNodes().split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        for (String node : nodes) {
            int index = node.indexOf(":");
            hostAndPorts.add(new HostAndPort(node.substring(0, index), Integer.parseInt(node.substring(index + 1))));
        }
        /* Jedis连接池配置 */
        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
        return new JedisCluster(hostAndPorts,
                redisConfigProperties.getCommandTimeout(),
                redisConfigProperties.getSoTimeout(),
                redisConfigProperties.getMaxAttempts(),
                redisConfigProperties.getPassword(),
                jedisPoolConfig);

    }

    /**
     * 连接池配置
     *
     * @return JedisPoolConfig
     **/
    @Bean
    private JedisPoolConfig getJedisPoolConfig() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfigProperties.getMaxIdle());       // 最大空闲连接数, 默认8个,控制一个pool最多有多少个状态为 idle(空闲的)的jedis实例。
        jedisPoolConfig.setMaxTotal(redisConfigProperties.getMaxTotal());    // 最大连接数, 默认8个
        jedisPoolConfig.setMinIdle(redisConfigProperties.getMinIdle());       // 最小空闲连接数, 默认0
        jedisPoolConfig.setMaxWaitMillis(redisConfigProperties.getMaxWaitMillis()); // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常 JedisConnectionException , 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setTestOnBorrow(redisConfigProperties.isTestOnBorrow());  // 对拿到的 connection 进行 validateObject 校验，是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnReturn(redisConfigProperties.isTestOnReturn()); // 对拿到的 connection 进行 validateObject 校验，向资源池归还连接时是否做连接有效性检测（ping）。检测到无效连接将会被移除。

        return jedisPoolConfig;
    }

}
