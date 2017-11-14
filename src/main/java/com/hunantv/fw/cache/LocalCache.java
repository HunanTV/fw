package com.hunantv.fw.cache;

import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

public class LocalCache {

	public static final String DEFAULT_TABLE = "_DEFAULT_TABLE_";
	CacheManager cacheManager;

	private static LocalCache localCache = null;
	private static LocalCacheConf conf = new LocalCacheConf();

	private LocalCache() {
		Configuration escacheConf = new Configuration();
		escacheConf.setUpdateCheck(conf.updateCheck);
		cacheManager = CacheManager.create(escacheConf);
	}

	public static LocalCache instance() {
		if (localCache == null)
			localCache = new LocalCache();
		return localCache;
	}

	public void set(String key, Object value) {
		this.set(DEFAULT_TABLE, key, value);
	}

	public static void setConf(LocalCacheConf conf) {
		LocalCache.conf = conf;
	}

	public void set(String tableName, String key, Object value) {
		try {
			cacheManager.addCacheIfAbsent(new Cache(tableName, conf.max_len, conf.overflowToDisk, conf.eternal, conf.expired_seconds, conf.expired_seconds));
			Cache lru = cacheManager.getCache(tableName);
			lru.put(new Element(key, value));
		} catch (ObjectExistsException ex) {
		}
	}

	public Cache table() {
		return table(DEFAULT_TABLE);
	}

	public Cache table(String tableName) {
		return this.cacheManager.getCache(tableName);
	}

	public Object get(String key) {
		return this.get(DEFAULT_TABLE, key);
	}

	public Object get(String tableName, String key) {
		Cache lru = cacheManager.getCache(tableName);
		if (null == lru) {
			return null;
		}
		Element ele = lru.get(key);
		return null == ele ? null : ele.getObjectValue();
	}

	public void clearAll() {
		cacheManager.removeAllCaches();
	}

	public void clear() {
		this.clear(DEFAULT_TABLE);
	}

	public void clear(String tableName) {
		cacheManager.removeCache(tableName);
	}
}
