package com.hunantv.fw.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import com.hunantv.fw.utils.FwLogger;

public class LocalCache {

	public static final FwLogger logger = new FwLogger(LocalCache.class);
	public static final String DEFAULT_TABLE = "_DEFAULT_TABLE_";
	CacheManager cacheManager = CacheManager.create();
	private static LocalCache lc = null;
	private LocalCacheConf conf;

	private LocalCache() {
	}

	public static LocalCache instance() {
		if (null == lc) {
			lc = new LocalCache();
			lc.setConf(new LocalCacheConf());
		}
		return lc;
	}

	public void setConf(LocalCacheConf conf) {
		this.conf = conf;
	}

	public LocalCacheConf getConf() {
		return this.conf;
	}

	public void set(String key, Object value) {
		this.set(DEFAULT_TABLE, key, value);
	}

	public void set(String tableName, String key, Object value) {
		try {
			Cache lru = cacheManager.getCache(tableName);
			if (null == lru) {
				// public Cache(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds)
				lru = new Cache(tableName, conf.max_len, conf.overflowToDisk, conf.eternal, conf.expired_seconds, conf.expired_seconds);
				cacheManager.addCache(lru);
			}
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
		if (null == lru)
			return null;
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
