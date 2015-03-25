package com.hunantv.fw.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {
	public Jedis jedis;
	public String name;
	private JedisPool pool;
	private RedisConf conf = RedisConf.getInstance();

	public Redis(String name) {
		this.name = name;
		this.pool = conf.getPool(this.name);
		this.jedis = pool.getResource();
	}

	public void close() {
		pool.returnResource(this.jedis);
	}
}
