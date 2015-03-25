package com.hunantv.fw.redis;

import java.util.Map;
import java.util.Properties;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.SysConf;

import freemarker.template.utility.StringUtil;

public class RedisConf {
	public static final FwLogger logger = new FwLogger(RedisConf.class);
	protected Map<String, JedisPool> pools;
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
			String[] names = StringUtil.split((String) pros.get("redis.names"), ',');
			for (String name : names) {
				initJedisPool(pros, name);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void initJedisPool(Properties pros, String name) {
		JedisPoolConfig poolConfig = initJedisPoolConfig(pros);
		String ip = pros.getProperty("redis." + name + ".ip").trim();
		int port = Integer.valueOf(pros.getProperty("redis." + name + ".port").trim());
		JedisPool pool = new JedisPool(poolConfig, ip, port);
		pools.put(name, pool);
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
		if (this.pools.containsKey(name)) {
			return this.pools.get(name);
		}
		return null;
	}
}
