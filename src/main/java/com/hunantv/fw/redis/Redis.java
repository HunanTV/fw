package com.hunantv.fw.redis;

import com.hunantv.fw.utils.FwLogger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class Redis implements AutoCloseable {
	public Jedis jedis;
	public String name;
	private JedisPool pool;
	private RedisConf conf = RedisConf.getInstance();
	public static final FwLogger logger = new FwLogger(Redis.class);
	public static final String DEFAULT_NAME = "write";

	public Redis() {
		this(Redis.DEFAULT_NAME);
	}

	public Redis(String name) {
		this.name = name;
		this.pool = conf.getPool(this.name);
		this.jedis = pool.getResource();
	}

	public void close() {
		try {
			pool.returnResource(this.jedis);
		} catch (JedisException ex) {
			logger.warn("redis close error.", ex);
		}
	}
}
