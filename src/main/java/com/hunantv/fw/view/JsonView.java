package com.hunantv.fw.view;

import com.alibaba.fastjson.JSON;

public class JsonView implements View {

	private Object v;

	public JsonView(Object v) {
		this.v = v;
	}

	public String render() {
		return JSON.toJSONString(v);
	}
}
