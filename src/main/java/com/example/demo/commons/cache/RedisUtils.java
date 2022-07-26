package com.example.demo.commons.cache;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liwenji
 * @ClassName RedisUtils
 * @Description TODO，通过 JedisPool 或 JedisCluster，对 redis 进行操作
 * @date 2022/6/21 16:55
 * @Version 1.0
 */
@Component
public class RedisUtils {

    @Resource
    RedisCacheFactory redisCacheFactory;

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    private static Jedis jedis = null;
    private static JedisPool jedisPool = null;
    private static JedisCluster jedisCluster = null;

    /**
     * 根据key获取redis数据-字符串
     *
     * @param key 键
     * @return 字符串
     */
    public String get(String key) {
        jedis = null;
        String value = null;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return value;
    }

    /**
     * 根据 key 获取 redis JSON字符串数据-并将其转化为对象（前提知道里面的值为 JSON 串）
     *
     * @param key   键，其存的值必须为 JSON 串
     * @param clazz 对象类
     * @param <T>   对象类型
     * @return
     */
    public <T> T getValue(String key, Class<T> clazz) {
        T realValue = null;
        jedis = null;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            if (jedisPool != null) {
                // 从连接池中获取jedis对象
                jedis = jedisPool.getResource();
                // 如果键key不存在，那么返回特殊值nil；否则返回键key的值。
                String value = jedis.get(key);
                realValue = JSON.parseObject(value, clazz);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return realValue;
    }

    /**
     * 根据 key（哈希表名） 获取 redis 的哈希表中的指定字段的数据-字符串
     *
     * @param key     哈希表名字
     * @param hashKey 哈希表中的指定字段名字
     * @return
     */
    public String getHash(String key, String hashKey) {
        String value = null;
        jedis = null;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            value = jedis.hget(key, hashKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return value;
    }

    /**
     * 根据 key（哈希表名） 获取 redis 哈希表的所有数据 -并将其转换为 map
     *
     * @param key 哈希表名称
     * @return hasMap
     */
    public Map<String, String> getHashAll(String key) {
        jedis = null;
        Map<String, String> valueMap = new HashMap<>();
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            valueMap = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return valueMap;
    }

    /**
     * 根据 key （哈希表名） 新建或覆盖 redis 中该哈希表（key）中指定字段（hashKey）的值（value），并设置过期值（seconds），若 seconds 为 null ，则自动为它设置
     *
     * @param key     哈希表名
     * @param hashKey 哈希表的指定字段
     * @param value   哈希表的指定字段对应的值
     * @param seconds 过期时间（秒），为空的话默认设置 3600 * 24 秒
     * @return
     */
    public long setHash(String key, String hashKey, String value, Integer seconds) {
        jedis = null;
        long result = 0;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            // 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0 。
            result = jedis.hset(key, hashKey, value);
            if (seconds == null) {
                seconds = 3600 * 24;
            }
            //设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0 。
            jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return result;
    }

    /**
     * 根据 key 插入 redis 数据-字符串，并设置过期时间，如果 key 已经存在， SETEX 命令将会替换旧的值。设置成功时返回 OK 。
     *
     * @param key     键
     * @param value   值
     * @param seconds 失效时间（单位：秒）
     * @return
     */
    public String setEx(String key, String value, int seconds) {
        jedis = null;
        String result = null;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            //为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。设置成功时返回 OK 。
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return result;
    }

    /**
     * 分布式锁用到，这种方法没有过期时间，不建议分布式锁用
     * 命令在指定的 key 不存在时，为 key 设置指定的值。并设置过期时间，如果 key 已经存在，返回 0 。设置成功，返回 1 。 设置失败，返回 0 。
     *
     * @param key     键
     * @param value   值
     * @param seconds 失效时间（单位：秒）
     * @return
     */
    public long setNx(String key, String value, int seconds) {
        jedis = null;
        long result = 0;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            //命令在指定的 key 不存在时，为 key 设置指定的值。设置成功，返回 1 。 设置失败，返回 0 。
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return result;
    }


    /**
     * 根据key删除redis数据
     *
     * @param key
     * @return
     */
    public long delete(String key) {
        jedis = null;
        long result = 0;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            // 用于删除已存在的键。不存在的 key 会被忽略。返回值为被删除 key 的数量。
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return result;
    }

    /**
     * 分布式锁用，有过期时间
     * 为 key 设置指定的值，并设置过期时间。等同于以下命令同时执行。返回值String，如果写入成功是“OK”
     * SET key value
     * EXPIRE key seconds
     *
     * @param key        键
     * @param value      值
     * @param expiretime 过期时间，秒
     * @return
     */
    public String setKeyOfExpireTime(String key, String value, int expiretime) {
        jedis = null;
        String result = null;
        try {
            jedisPool = redisCacheFactory.jedisPoolFactory();
            jedis = jedisPool.getResource();
            //nxxx： 只能取 NX 或者 XX，如果取 NX，则只有当 key 不存在是才进行 set，如果取 XX，则只有当 key 已经存在时才进行 set
            //expx： 只能取 EX 或者 PX，代表数据过期时间的单位，EX 代表秒，PX 代表毫秒。
            //time： 过期时间，单位是 expx 所代表的单位。
            result = jedis.set(key, value, "NX", "EX", expiretime);
            logger.info("设置key,返回值为:" + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                // 归还连接
                jedis.close();
            }
            if (jedisPool != null) {
                // 关闭连接池
                jedisPool.close();
            }
        }
        return result;
    }

    public String setKeyOfExpireTimeByCluster(String key, String value, int expiretime) {
        jedisCluster = null;
        String result = null;
        try {
            jedisCluster = redisCacheFactory.jedisClusterFactory();
            //nxxx： 只能取 NX 或者 XX，如果取 NX，则只有当 key 不存在是才进行 set，如果取 XX，则只有当 key 已经存在时才进行 set
            //expx： 只能取 EX 或者 PX，代表数据过期时间的单位，EX 代表秒，PX 代表毫秒。
            //time： 过期时间，单位是 expx 所代表的单位。
            result = jedisCluster.set(key, value, "NX", "EX", expiretime);
            logger.info("集群设置key,返回值为:" + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedisCluster != null) {
                try {
                    // 关闭集群连接池
                    jedisCluster.close();
                } catch (Exception e) {
                    logger.error("关闭 jedisCluster 时出错 ");
                }
            }
        }
        return result;
    }


}
