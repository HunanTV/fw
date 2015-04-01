package com.hunantv.fw.cache;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LRU extends LinkedHashMap {
	private final int maxCapacity;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Lock lock = new ReentrantLock();

	public LRU(int maxCapacity) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
		this.maxCapacity = maxCapacity;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry eldest) {
		return size() > maxCapacity;
	}

	@Override
	public Object get(Object key) {
		try {
			lock.lock();
			Object obj = super.get(key);
			if (null == obj)
				return null;
			LRUNode node = (LRUNode) obj;
			if (node.isExpired()) {
				this.remove(key);
				return null;
			} else {
				return node.getData();
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Object put(Object key, Object value) {
		return this.put(key, value, 0L);
	}

	public Object put(Object key, Object value, Long expiredSeconds) {
		try {
			lock.lock();
			LRUNode node = new LRUNode(value, expiredSeconds);
			Object obj = super.put(key, node);
			return obj == null ? null : ((LRUNode) obj).getData();
		} finally {
			lock.unlock();
		}
	}
}
