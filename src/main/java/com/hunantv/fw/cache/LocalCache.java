package com.hunantv.fw.cache;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.SysConf;

public class LocalCache {
	public static final String DEFAULT_TABLE = "_DEFAULT_TABLE_";
	public static final FwLogger logger = new FwLogger(LocalCache.class);
	public static int LRU_MAX_LEN = 1024;
	public static long LRU_EXPIRED_SECONDS = 0L;

	private ConcurrentMap<String, LRU> caches = new ConcurrentHashMap<String, LRU>();

	private static LocalCache lc = null;

	private LocalCache() {
		try {
			SysConf conf = new SysConf();
			Properties pros = conf.read("cache.properties");
			Object obj = pros.get("cache.local.length");
			if (null != obj) {
				LocalCache.LRU_MAX_LEN = Integer.valueOf(((String) obj).trim());
			}
			logger.debug("Local Cache Max Length: " + LocalCache.LRU_MAX_LEN);
			obj = pros.get("cache.local.expired");
			if (null != obj) {
				LocalCache.LRU_EXPIRED_SECONDS = Long.valueOf(((String) obj).trim());
			}
			logger.debug("Local Cache Default Expired Seconds: " + LocalCache.LRU_EXPIRED_SECONDS);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static LocalCache instance() {
		if (null == lc)
			lc = new LocalCache();
		return lc;
	}

	public void set(String key, Object value) {
		this.set(DEFAULT_TABLE, key, value);
	}

	public void set(String tableName, String key, Object value) {
		LRU lru = caches.get(tableName);
		if (null == lru) {
			lru = new LRU(LRU_MAX_LEN);
			lru.put(key, value, LocalCache.LRU_EXPIRED_SECONDS);
			caches.put(tableName, lru);
		} else {
			lru.put(key, value, LocalCache.LRU_EXPIRED_SECONDS);
		}
	}

	public Object get(String key) {
		return this.get(DEFAULT_TABLE, key);
	}

	public Object get(String tableName, String key) {
		LRU lru = caches.get(tableName);
		return null == lru ? null : lru.get(key);
	}

	public LRU table(String name) {
		return this.caches.get(name);
	}

	public void clearAll() {
		caches.clear();
	}

	public void clear() {
		this.clear(DEFAULT_TABLE);
	}

	public void clear(String tableName) {
		if (caches.containsKey(tableName)) {
			caches.remove(tableName);
		}
	}
}
