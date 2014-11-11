package com.hunantv.fw.tests;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public class KeysetEnum<T> implements Enumeration<T> {

	private Iterator<T> keysIter;

	public KeysetEnum(Map<T, Object> map) {
		keysIter = map.keySet().iterator();
	}

	@Override
	public boolean hasMoreElements() {
		return keysIter.hasNext();
	}

	@Override
	public T nextElement() {
		return keysIter.next();
	}
}
