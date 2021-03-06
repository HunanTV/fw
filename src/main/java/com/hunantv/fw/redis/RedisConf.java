package com.hunantv.fw.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.hunantv.fw.utils.StringUtil;
import com.hunantv.fw.utils.SysConf;

public class RedisConf {
    public static final Logger logger = LoggerFactory.getLogger(RedisConf.class);
    protected Map<String, JedisPool> pools = new HashMap<String, JedisPool>();
    protected Map<String, JedisCluster> clusterMap = new HashMap<>();
    private static RedisConf conf = null;

    public static RedisConf getInstance() {
        if (null == conf) {
            conf = new RedisConf();
        }
        return conf;
    }

    private RedisConf() {
        try {
            SysConf conf = new SysConf();
            Properties pros = conf.read("redis.properties");
            String[] names = StringUtil.split((String) pros.get("redis.names"), ",");
            for (String name : names) {
                initJedisPool(pros, name);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void initJedisPool(Properties pros, String name) {
        JedisPoolConfig poolConfig = initJedisPoolConfig(pros);
        String mode = pros.getProperty("redis." + name + ".mode", "proxy");
        if ("cluster".equals(mode)) {
            Set<HostAndPort> nodes = new HashSet<>();
            String servers = pros.getProperty("redis." + name + ".server");
            for (String server : servers.split(",")) {
                String[] ipAndPort = server.split("\\:");
                nodes.add(new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
            }
            JedisCluster cluster = new JedisCluster(nodes, poolConfig);
            clusterMap.put(name, cluster);
        } else {
            String servers = pros.getProperty("redis." + name + ".server");
            String[] ipAndPort = servers.split(",")[0].split("\\:");
            String ip = ipAndPort[0];
            Integer port = Integer.parseInt(ipAndPort[1]);
            JedisPool pool = new JedisPool(poolConfig, ip, port);
            pools.put(name, pool);
        }
    }

    protected JedisPoolConfig initJedisPoolConfig(Properties pros) {
        JedisPoolConfig config = new JedisPoolConfig();
        // pool可以分配的Jedis实例
        config.setMaxTotal(Integer.valueOf(pros.getProperty("redis.pool.maxActive").trim()));

        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
        config.setMaxIdle(Integer.valueOf(pros.getProperty("redis.pool.maxIdle").trim()));

        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
        config.setMaxWaitMillis(Integer.valueOf(pros.getProperty("redis.pool.maxWait").trim()));

        config.setTestOnBorrow(Boolean.valueOf(pros.getProperty("redis.pool.testOnBorrow").trim()));
        return config;
    }

    public JedisPool getPool(String name) {
        return this.pools.get(name);
    }

    public JedisCluster getJedisCluster(String name) {
        return this.clusterMap.get(name);
    }
}
