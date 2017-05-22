package com.hunantv.fw.redis;

import com.hunantv.fw.utils.FwLogger;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisException;

public class RedisCluster {
    private String name;
    private JedisCluster jedisCluster;
    private RedisConf conf = RedisConf.getInstance();
    public static final FwLogger logger = new FwLogger(Redis.class);
    public static final String DEFAULT_NAME = "write";

    public RedisCluster() {
        this(RedisCluster.DEFAULT_NAME);
    }

    public RedisCluster(String name) {
        this.name = name;
        this.jedisCluster = conf.getJedisCluster(name);
    }

    public JedisCluster getJedisCluster(){
        return this.jedisCluster;
    }

}
