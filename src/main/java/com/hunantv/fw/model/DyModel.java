package com.hunantv.fw.model;

import java.util.HashMap;
import java.util.Map;

public class DyModel {
	private Map<String, Object> data = new HashMap<String, Object>();

	public DyModel() {
	}

	public DyModel(Map<String, Object> data) {
		this.data = data;
	}

	public Object get(String attrName) {
		return this.data.get(attrName);
	}

	public void set(String attrName, Object attrValue) {
		this.data.put(attrName, attrValue);
	}
}
