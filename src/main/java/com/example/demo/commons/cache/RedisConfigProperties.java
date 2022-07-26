package com.example.demo.commons.cache;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liwenji
 * @ClassName RedisConfigProperties
 * @Description TODO
 * @date 2022/6/21 11:26
 * @Version 1.0
 */
@Data
@Component
public class RedisConfigProperties {

    @Value("${redis-config.maxIdle}")
    private int maxIdle;
    @Value("${redis-config.minIdle}")
    private int minIdle;
    @Value("${redis-config.maxTotal}")
    private int maxTotal;
    @Value("${redis-config.blockWhenExhausted}")
    private boolean blockWhenExhausted;
    @Value("${redis-config.maxWaitMillis}")
    private int maxWaitMillis;
    @Value("${redis-config.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${redis-config.testOnReturn}")
    private boolean testOnReturn;
    @Value("${redis-config.jmxEnabled}")
    private boolean jmxEnabled;


    @Value("${redis-config.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${redis-config.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${redis-config.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${redis-config.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;


    @Value("${redis-config.commandTimeout}")
    private int commandTimeout;
    @Value("${redis-config.password}")
    private String password;
    @Value("${redis-config.dataBase}")
    private String dataBase;

    /**
     * 集群配置
     */
    @Value("${redis-config.cluster.nodes}")
    private String clusterNodes;
    @Value("${redis-config.cluster.max-redirects}")
    private int maxRedirects;
    @Value("${redis-config.cluster.instanceName}")
    private String instanceName;
    @Value("${redis-config.cluster.soTimeout}")
    private int soTimeout;
    @Value("${redis-config.cluster.maxAttempts}")
    private int maxAttempts;

    /**
     * 单机配置
     */
    @Value("${redis-config.host}")
    private String host;
    @Value("${redis-config.port}")
    private int port;
}
