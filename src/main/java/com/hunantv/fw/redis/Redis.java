package com.hunantv.fw.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis implements AutoCloseable {
    public final Logger logger = LoggerFactory.getLogger(Redis.class);
    public static final String DEFAULT_NAME = "write";
	public Jedis jedis;
	public String name;
	private JedisPool pool;
	private RedisConf conf = RedisConf.getInstance();

	public Redis() {
		this(Redis.DEFAULT_NAME);
	}

	public Redis(String name) {
		this.name = name;
		this.pool = conf.getPool(this.name);
		this.jedis = pool.getResource();
	}
	
	@Override
	public void close() {
		this.jedis.close();
	}
}
