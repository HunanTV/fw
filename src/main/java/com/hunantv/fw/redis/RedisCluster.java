package com.hunantv.fw.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

public class RedisCluster {
    public final Logger logger = LoggerFactory.getLogger(RedisCluster.class);
    public static final String DEFAULT_NAME = "write";
    private String name;
    private JedisCluster jedisCluster;
    private RedisConf conf = RedisConf.getInstance();

    public RedisCluster() {
        this(RedisCluster.DEFAULT_NAME);
    }

    public RedisCluster(String name) {
        this.name = name;
        this.jedisCluster = conf.getJedisCluster(this.name);
    }

    public JedisCluster getJedisCluster() {
        return this.jedisCluster;
    }

}
