package com.hunantv.fw.cache;

import java.util.Date;

public class LRUNode {
	private Object data;
	private long expiredSeconds;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getExpiredSeconds() {
		return expiredSeconds;
	}

	public LRUNode() {
	}

	public LRUNode(Object data) {
		this(data, 0L);
	}

	public LRUNode(Object data, long expiredSeconds) {
		this.data = data;
		if (0 == expiredSeconds)
			this.expiredSeconds = 0;
		else
			this.expiredSeconds = expiredSeconds + new Date().getTime();
	}

	public boolean isExpired() {
		if (0 == this.expiredSeconds)
			return false;
		return new Date().getTime() > this.expiredSeconds;
	}
}
